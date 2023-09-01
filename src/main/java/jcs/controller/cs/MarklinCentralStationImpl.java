/*
 * Copyright 2023 Frans Jacobs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jcs.controller.cs;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import jcs.controller.events.SensorEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import jcs.JCS;
import jcs.controller.cs.can.parser.AccessoryBeanParser;
import jcs.controller.cs.can.parser.LocomotiveBeanParser;
import jcs.entities.enums.AccessoryValue;
import jcs.entities.enums.Direction;
import jcs.entities.enums.DecoderType;
import jcs.controller.cs3.http.SvgIconToPngIconConverter;
import jcs.entities.AccessoryBean;
import jcs.entities.LocomotiveBean;
import org.tinylog.Logger;
import jcs.controller.cs.can.CanMessage;
import jcs.controller.cs.can.CanMessageFactory;
import static jcs.controller.cs.can.CanMessageFactory.getStatusDataConfigResponse;
import jcs.controller.cs.can.parser.ChannelDataParser;
import jcs.controller.cs.can.parser.MessageInflator;
import jcs.controller.cs.can.parser.StatusDataConfigParser;
import jcs.controller.cs.can.parser.SystemStatusParser;
import jcs.controller.cs.net.CSConnection;
import jcs.controller.cs.net.HTTPConnection;
import jcs.controller.cs.net.CSConnectionFactory;
import jcs.controller.events.PowerEvent;
import jcs.controller.cs3.http.DeviceJSONParser;

import jcs.controller.events.LocomotiveDirectionEvent;
import jcs.controller.events.LocomotiveFunctionEvent;
import jcs.controller.cs3.http.AccessoryJSONParser;
import jcs.controller.events.LocomotiveSpeedEvent;
import jcs.controller.cs.events.CanPingListener;
import jcs.controller.cs.events.AccessoryListener;
import jcs.controller.events.AccessoryEvent;
import jcs.util.ByteUtil;
import jcs.controller.cs.events.FeedbackListener;
import jcs.controller.cs.events.LocomotiveListener;
import jcs.controller.cs.events.SystemListener;
import jcs.controller.events.AccessoryEventListener;
import jcs.controller.events.LocomotiveDirectionEventListener;
import jcs.controller.events.LocomotiveFunctionEventListener;
import jcs.controller.events.PowerEventListener;
import jcs.controller.events.SensorEventListener;
import jcs.controller.events.LocomotiveSpeedEventListener;
import jcs.util.RunUtil;

/**
 *
 * @author Frans Jacobs
 */
public class MarklinCentralStationImpl implements MarklinCentralStation {

  private CSConnection connection;
  private boolean connected = false;

  private final Map<Integer, Device> devices;
  private Device mainDevice;
  private int csUid;

  private ChannelDataParser channelData1;
  private ChannelDataParser channelData2;
  private ChannelDataParser channelData3;
  private ChannelDataParser channelData4;

  private final List<PowerEventListener> powerEventListeners;
  private final List<AccessoryEventListener> accessoryEventListeners;
  private final List<SensorEventListener> sensorEventListeners;

  private final List<LocomotiveFunctionEventListener> locomotiveFunctionEventListeners;
  private final List<LocomotiveDirectionEventListener> locomotiveDirectionEventListeners;
  private final List<LocomotiveSpeedEventListener> locomotiveSpeedEventListeners;

  private ExecutorService executor;
  private ScheduledExecutorService scheduledExecutor;
  private boolean power;

  public MarklinCentralStationImpl() {
    this(true);
  }

  private MarklinCentralStationImpl(boolean autoConnect) {
    devices = new HashMap<>();

    powerEventListeners = new LinkedList<>();
    sensorEventListeners = new LinkedList<>();
    accessoryEventListeners = new LinkedList<>();

    locomotiveFunctionEventListeners = new LinkedList<>();
    locomotiveDirectionEventListeners = new LinkedList<>();
    locomotiveSpeedEventListeners = new LinkedList<>();

    executor = Executors.newCachedThreadPool();
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    if (autoConnect) {
      connect();
    }
  }

  int getCsUid() {
    return csUid;
  }

  public boolean isCS3() {
    if (this.mainDevice != null) {
      return this.mainDevice.isCS3();
    } else {
      return false;
    }
  }

  public String getIp() {
    return CSConnectionFactory.getControllerIp();
  }

  @Override
  public final boolean connect() {
    if (!connected) {
      Logger.trace("Connecting to a Central Station...");
      if (executor == null || executor.isShutdown()) {
        executor = Executors.newCachedThreadPool();
      }

      CSConnection csConnection = CSConnectionFactory.getConnection();
      this.connection = csConnection;

      if (connection != null) {
        //Wait, if needed until the receiver thread has started
        long now = System.currentTimeMillis();
        long timeout = now + 1000L;

        while (!connected && now < timeout) {
          connected = csConnection.isConnected();
          now = System.currentTimeMillis();
        }

        if (connected) {
          //Prepare the observers (listeners) which need to react on message events from the Central Station
          CanPingMessageListener pingListener = new CanPingMessageListener(this);
          CanFeedbackMessageListener feedbackListener = new CanFeedbackMessageListener(this);
          CanSystemMessageListener systemEventListener = new CanSystemMessageListener(this);
          CanAccessoryMessageListener accessoryListener = new CanAccessoryMessageListener(this);
          CanLocomotiveMessageListener locomotiveListener = new CanLocomotiveMessageListener(this);

          this.connection.setCanPingListener(pingListener);
          this.connection.setFeedbackListener(feedbackListener);
          this.connection.setSystemListener(systemEventListener);
          this.connection.setAccessoryListener(accessoryListener);
          this.connection.setLocomotiveListener(locomotiveListener);

          JCS.logProgress("Obtaining Device information...");
          //request all member to give a response
          getMembers();

          now = System.currentTimeMillis();
          timeout = now + 30000L;
          while (this.mainDevice == null && now < timeout) {
            pause(100);
            now = System.currentTimeMillis();
          }

          Logger.trace("Found " + this.devices.size() + " devices.");
          for (Device d : this.devices.values()) {
            Logger.trace(d);
          }

          if (this.mainDevice != null) {
            Logger.trace("Connected with " + this.mainDevice.getDeviceName() + " " + this.mainDevice.getArticleNumber() + " SerialNumber: " + mainDevice.getSerialNumber() + " UID: " + this.csUid);
            JCS.logProgress("Connected with " + this.mainDevice.getDeviceName());
            
            this.power = this.isPower();
            JCS.logProgress("Power is " + (this.power ? "On" : "Off"));

          } else {
            Logger.warn("No Main Device found yet...");
            //this.disconnect();
          }
        }
      } else {
        Logger.warn("Can't connect with Central Station!");
        JCS.logProgress("Can't connect with Central Station!");
      }
    }
    Logger.trace("Connected: " + connected);

    return connected;
  }

  /**
   * The CS3 has a Web App API which is used for the Web GUI. The Internal devices can be obtained calling this API which returns a JSON string, From this JSON all devices are found. Most important is
   * the GFP which is the heart of the CS 3 most CAN Command need the GFP UID. This dat can also be obtained using the CAN Member PING command, but The JSON gives a little more detail
   *
   *
   */
  protected void getAppDevicesCs3() {
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    if (httpCon.isConnected()) {
      String deviceJSON = httpCon.getDevicesJSON();
      DeviceJSONParser dp = new DeviceJSONParser();
      dp.parseDevices(deviceJSON);

      //TODO update the devices ?...
      //this.csUid = Integer.parseInt(dp.getCs3().getUid().substring(2), 16);
      //this.csName = dp.getCs3().getName();
      //this.gfp = dp.getGfp();
      String gfpUid = ByteUtil.toHexString(dp.getGfp().getUid());  //Integer.parseInt(this.gfp.getUid().substring(2), 16);
      //this.linkSxx = dp.getLinkSxx();
      String linkSxxUid = ByteUtil.toHexString(dp.getLinkSxx().getUid()); //   Integer.parseInt(this.linkSxx.getUid().substring(2), 16);

      Logger.trace("CS3 uid: " + dp.getCs3().getUid());
      Logger.trace("GFP uid: " + dp.getGfp().getUid());
      Logger.trace("GFP Article: " + dp.getGfp().getArticleNumber());
      Logger.trace("GFP version: " + dp.getGfp().getVersion());
      Logger.trace("GFP Serial: " + dp.getGfp().getSerialNumber());
      Logger.trace("GFP id: " + dp.getGfp().getIdentifier());

      Logger.trace("LinkSxx uid: " + dp.getLinkSxx().getUid());
      Logger.trace("LinkSxx id: " + dp.getLinkSxx().getIdentifier() + " deviceId: " + dp.getLinkSxx().getDeviceId());
      Logger.trace("LinkSxx serial: " + dp.getLinkSxx().getSerialNumber());
      Logger.trace("LinkSxx version: " + dp.getLinkSxx().getVersion());

      for (SxxBus b : dp.getLinkSxx().getSxxBusses().values()) {
        Logger.trace(b);
      }
    } else {
      Logger.warn("Not Connected with CS 3!");
    }
  }

  @Override
  public Device getDevice() {
    return this.mainDevice;
  }

  @Override
  public List<Device> getDevices() {
    return this.devices.values().stream().collect(Collectors.toList());
  }

  /**
   * Query the System Status
   *
   * @return true the track power is on else off.
   */
  @Override
  public boolean isPower() {
    if (this.connected) {
      CanMessage m = sendMessage(CanMessageFactory.querySystem(this.csUid));

      Logger.trace("Received " + m.getResponses().size() + " responses. RX: " + m.getResponse());
      SystemStatusParser ss = new SystemStatusParser(m);
      this.power = ss.isPower();
    } else {
      this.power = false;
    }
    return this.power;
  }

  /**
   * System Stop and GO When on = true then the GO command is issued: The track format processor activates the operation and supplies electrical energy. Any speed levels/functions that may still exist
   * or have been saved will be sent again. when false the Stop command is issued: Track format processor stops operation on main and programming track. Electrical energy is no longer supplied. All
   * speed levels/function values and settings are retained.
   *
   * @param on true Track power On else Off
   * @return true the Track power is On else Off
   */
  @Override
  public boolean power(boolean on) {
    if (this.connected) {
      SystemStatusParser ss = new SystemStatusParser(sendMessage(CanMessageFactory.systemStopGo(on, csUid)));
      this.power = ss.isPower();
      return power;
    } else {
      return false;
    }
  }

  @Override
  public boolean isConnected() {
    return connected;
  }

  @Override
  public void disconnect() {
    try {
      if (connection != null) {
        connection.close();
        connected = false;
      }

      if (executor != null) {
        executor.shutdown();
      }
      executor = null;
      connection = null;

      CSConnectionFactory.disconnectAll();
    } catch (Exception ex) {
      Logger.error(ex);
    }
    Logger.trace("Disconnected");
  }

  void getMembers() {
    CanMessage msg = CanMessageFactory.getMembersPing();
    this.connection.sendCanMessage(msg);

    for (CanMessage r : msg.getResponses()) {
      Device d = new Device(r);
      if (!this.devices.containsKey(d.getUid())) {
        this.devices.put(d.getUid(), d);
      }
    }
    Logger.trace("Aquired "+this.devices.size()+" devices");
  }

  private void updateMember(CanMessage message) {
    executor.execute(() -> updateDevice(message));
  }

  private void updateDevice(final CanMessage message) {
    if (CanMessage.PING_RESP == message.getCommand()) {
      int uid = message.getDeviceUidNumberFromMessage();

      Device device;
      if (this.devices.containsKey(uid)) {
        device = this.devices.get(uid);
      } else {
        device = new Device(message);
        this.devices.put(device.getUid(), device);
      }

      if (!device.isDataComplete()) {
        CanMessage msg = sendMessage(CanMessageFactory.statusDataConfig(device.getUid(), 0));
        device.updateFromMessage(msg);
        Logger.trace("Updated: " + device);

        //Can the main device be set from the avaliable data
        for (Device d : this.devices.values()) {
          if (d.isDataComplete() && ("60214".equals(d.getArticleNumber()) || "60226".equals(d.getArticleNumber()) || "60126".equals(d.getArticleNumber()))) {
            this.csUid = d.getUid();
            this.mainDevice = d;
            Logger.trace("Main Device: " + d);
            //TODO Callback to UI whet the Main Device is set at a later state 
            
          }
        }
      }

      if (this.mainDevice == null) {
        //Lets send a ping again
        getMembers();
      } else {
        if (this.mainDevice != null && this.mainDevice.isDataComplete()) {
          if (System.getProperty("cs.article") == null) {
            System.setProperty("cs.article", this.mainDevice.getArticleNumber());
            System.setProperty("cs.serial", this.mainDevice.getSerialNumber());
            System.setProperty("cs.name", this.mainDevice.getDeviceName());
            System.setProperty("cs.cs3", (this.mainDevice.isCS3() ? "true" : "false"));

            Logger.trace("CS " + (mainDevice.isCS3() ? "3" : "2") + " Device: " + device);
          }
        }
      }
    }
  }

  void getStatusDataConfig() {
    if (this.connected) {
      CanMessage message = sendMessage(CanMessageFactory.statusDataConfig(csUid, 0));
      StatusDataConfigParser sdcp = new StatusDataConfigParser(message);

      Logger.debug(sdcp);

      message = sendMessage(CanMessageFactory.statusDataConfig(csUid, 1));
      channelData1 = new ChannelDataParser(message);

      message = sendMessage(CanMessageFactory.statusDataConfig(csUid, 2));
      channelData2 = new ChannelDataParser(message);

      message = sendMessage(CanMessageFactory.statusDataConfig(csUid, 3));
      channelData3 = new ChannelDataParser(message);

      message = sendMessage(CanMessageFactory.statusDataConfig(csUid, 4));
      channelData4 = new ChannelDataParser(message);

      updateChannelStatuses();
    }
  }

  void updateChannelStatuses() {
    if (this.connected) {
      CanMessage message = sendMessage(CanMessageFactory.systemStatus(1, csUid));
      channelData1.parseMessage(message);
      Logger.trace(channelData1);

      message = sendMessage(CanMessageFactory.systemStatus(2, csUid));
      channelData2.parseMessage(message);
      Logger.trace(channelData2);

      message = sendMessage(CanMessageFactory.systemStatus(3, csUid));
      channelData3.parseMessage(message);
      Logger.trace(channelData3);

      message = sendMessage(CanMessageFactory.systemStatus(4, csUid));
      channelData4.parseMessage(message);
      Logger.trace(channelData1);
    }
  }

  /**
   * Blocking call to the message sender thread which send the message and await the response. When there is no response within 1s the waiting is cancelled
   *
   * @param canMessage to send
   * @return the CanMessage with responses
   */
  private CanMessage sendMessage(CanMessage canMessage) {
    if (this.connection != null) {
      this.connection.sendCanMessage(canMessage);
    } else {
      Logger.warn("NOT connected!");
      Logger.trace("Message: " + canMessage + " NOT Send!");
    }
    return canMessage;
  }

  private int getLocoAddres(int address, DecoderType decoderType) {
    int locoAddress;
    locoAddress = switch (decoderType) {
      case MFX ->
        0x4000 + address;
      case DCC ->
        0xC000 + address;
      case SX1 ->
        0x0800 + address;
      case MM ->
        address;
      default ->
        address;
    };

    return locoAddress;
  }

  @Override
  public void changeDirection(int address, DecoderType decoderType, Direction direction) {
    if (this.power) {
      int la = getLocoAddres(address, decoderType);
      Logger.trace("Setting direction to: " + direction + " for loc address: " + la + " Decoder: " + decoderType + " Dir Mar: " + direction.getMarklinValue());
      CanMessage message = sendMessage(CanMessageFactory.setDirection(la, direction.getMarklinValue(), this.csUid));
      LocomotiveDirectionEvent dme = new LocomotiveDirectionEvent(message);
      this.notifyLocomotiveDirectionEventListeners(dme);
    }
  }

  @Override
  public void changeVelocity(int address, DecoderType decoderType, int speed) {
    if (this.power) {
      int la = getLocoAddres(address, decoderType);
      CanMessage message = sendMessage(CanMessageFactory.setLocSpeed(la, speed, this.csUid));
      LocomotiveSpeedEvent vme = new LocomotiveSpeedEvent(message);
      this.notifyLocomotiveSpeedEventListeners(vme);
    }
  }

  @Override
  public void changeFunctionValue(int address, DecoderType decoderType, int functionNumber, boolean flag) {
    if (this.power) {
      int value = flag ? CanMessage.FUNCTION_ON : CanMessage.FUNCTION_OFF;
      int la = getLocoAddres(address, decoderType);
      CanMessage message = sendMessage(CanMessageFactory.setFunction(la, functionNumber, value, this.csUid));
      this.notifyLocomotiveFunctionEventListeners(new LocomotiveFunctionEvent(message));
    }
  }

  // Use for Accessories
  private void wait200ms() {
    pause(200L);
  }

  private void pause(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException ex) {
      Logger.error(ex);
    }
  }

  @Override
  public void switchAccessory(int address, AccessoryValue value) {
    if (this.power) {
      executor.execute(() -> switchAccessoryOnOff(address, value));
    } else {
      Logger.trace("Trackpower is OFF! Can't switch Accessory: " + address + " to: " + value + "!");
    }
  }

  private void switchAccessoryOnOff(int address, AccessoryValue value) {
    CanMessage message = sendMessage(CanMessageFactory.switchAccessory(address, value, true, this.csUid));
    //TODO: dynamic setting of time or messageQueue it
    wait200ms();
    sendMessage(CanMessageFactory.switchAccessory(address, value, false, this.csUid));
    //Notify listeners
    AccessoryEvent ae = new AccessoryEvent(message);
    notifyAccessoryEventListeners(ae);
  }

  private void sendJCSUID() {
    executor.execute(() -> sendJCSUIDMessage());
  }

  private void sendJCSUIDMessage() {
    sendMessage(CanMessageFactory.getMemberPingResponse(CanMessage.JCS_UID, 1, CanMessage.JCS_DEVICE_ID));
  }

  private void sendJCSInformation() {
    executor.execute(() -> sentJCSInformationMessage());
  }

  private void sentJCSInformationMessage() {
    List<CanMessage> messages = getStatusDataConfigResponse(CanMessage.JCS_SERIAL, 0, 0, "JCS", "Java Central Station", CanMessage.JCS_UID);
    for (CanMessage msg : messages) {
      sendMessage(msg);
    }
  }

  List<LocomotiveBean> getLocomotivesViaCAN() {
    CanMessage message = CanMessageFactory.requestConfigData(csUid, "loks");
    this.connection.sendCanMessage(message);
    String lokomotive = MessageInflator.inflateConfigDataStream(message);

    LocomotiveBeanParser lp = new LocomotiveBeanParser();
    return lp.parseLocomotivesFile(lokomotive);
  }

  List<LocomotiveBean> getLocomotivesViaHttp() {
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    String csLocos = httpCon.getLocomotivesFile();
    LocomotiveBeanParser lp = new LocomotiveBeanParser();
    return lp.parseLocomotivesFile(csLocos);
  }

  @Override
  public List<LocomotiveBean> getLocomotives() {
    if (System.getProperty("locomotive.list.via", "can").equalsIgnoreCase("http")) {
      return getLocomotivesViaHttp();
    } else {
      return getLocomotivesViaCAN();
    }
  }

  @Override
  public void cacheAllFunctionIcons(PropertyChangeListener progressListener) {
    if(1==2) {
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    String json = httpCon.getAllFunctionsSvgJSON();

    if (progressListener != null) {
      PropertyChangeEvent pce = new PropertyChangeEvent(this, "synchProcess", null, "Getting all function Icons...");
      progressListener.propertyChange(pce);
    }

    SvgIconToPngIconConverter svgp = new SvgIconToPngIconConverter(progressListener);
    svgp.convertAndCacheAllFunctionsSvgIcons(json);
    }
  }

  public List<AccessoryBean> getAccessoriesViaHttp() {
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    String magnetartikelCs2 = httpCon.getAccessoriesFile();
    AccessoryBeanParser ap = new AccessoryBeanParser();
    return ap.parseAccessoryFile(magnetartikelCs2);
  }

  public List<AccessoryBean> getAccessoriesViaCan() {
    CanMessage message = CanMessageFactory.requestConfigData(csUid, "mags");
    this.connection.sendCanMessage(message);
    String magnetartikel = MessageInflator.inflateConfigDataStream(message);

    AccessoryBeanParser ap = new AccessoryBeanParser();
    return ap.parseAccessoryFile(magnetartikel);
  }

  @Override
  public List<AccessoryBean> getSwitches() {
    AccessoryJSONParser accessoryParser = new AccessoryJSONParser();
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    String json = httpCon.getAccessoriesJSON();
    accessoryParser.parseAccessories(json);
    return accessoryParser.getTurnouts();
  }

  @Override
  public List<AccessoryBean> getSignals() {
    AccessoryJSONParser accessoryParser = new AccessoryJSONParser();
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    String json = httpCon.getAccessoriesJSON();
    accessoryParser.parseAccessories(json);
    return accessoryParser.getSignals();
  }

  @Override
  public Image getLocomotiveImage(String icon) {
    HTTPConnection httpCon = CSConnectionFactory.getHTTPConnection(this.isCS3());
    Image locIcon = httpCon.getLocomotiveImage(icon);
    return locIcon;
  }

  @Override
  public void addPowerEventListener(PowerEventListener listener) {
    this.powerEventListeners.add(listener);
  }

  @Override
  public void removePowerEventListener(PowerEventListener listener) {
    this.powerEventListeners.remove(listener);
  }

  @Override
  public void addSensorEventListener(SensorEventListener listener) {
    this.sensorEventListeners.add(listener);
  }

  @Override
  public void removeSensorEventListener(SensorEventListener listener) {
    this.sensorEventListeners.remove(listener);
  }

  @Override
  public void addAccessoryEventListener(AccessoryEventListener listener) {
    this.accessoryEventListeners.add(listener);
  }

  @Override
  public void removeAccessoryEventListener(AccessoryEventListener listener) {
    this.accessoryEventListeners.remove(listener);
  }

  @Override
  public void addLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener) {
    this.locomotiveFunctionEventListeners.add(listener);
  }

  @Override
  public void removeLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener) {
    this.locomotiveFunctionEventListeners.remove(listener);
  }

  @Override
  public void addLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener) {
    this.locomotiveDirectionEventListeners.add(listener);
  }

  @Override
  public void removeLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener) {
    this.locomotiveDirectionEventListeners.remove(listener);
  }

  @Override
  public void addLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener) {
    this.locomotiveSpeedEventListeners.add(listener);
  }

  @Override
  public void removeLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener) {
    this.locomotiveSpeedEventListeners.remove(listener);
  }

  private void notifyPowerEventListeners(final PowerEvent powerEvent) {
    this.power = powerEvent.isPower();
    executor.execute(() -> fireAllPowerEventListeners(powerEvent));
  }

  private void fireAllPowerEventListeners(final PowerEvent powerEvent) {
    this.power = powerEvent.isPower();
    for (PowerEventListener listener : powerEventListeners) {
      listener.onPowerChange(powerEvent);
    }
  }

  private void fireAllSensorEventListeners(final SensorEvent sensorEvent) {
    for (SensorEventListener listener : sensorEventListeners) {
      listener.onSensorChange(sensorEvent);
    }
  }

  private void notifySensorEventListeners(final SensorEvent sensorEvent) {
    executor.execute(() -> fireAllSensorEventListeners(sensorEvent));
  }

  private void fireAllAccessoryEventListeners(final AccessoryEvent accessoryEvent) {
    for (AccessoryEventListener listener : this.accessoryEventListeners) {
      listener.onAccessoryChange(accessoryEvent);
    }
  }

  private void notifyAccessoryEventListeners(final AccessoryEvent accessoryEvent) {
    executor.execute(() -> fireAllAccessoryEventListeners(accessoryEvent));
  }

  private void fireAllFunctionEventListeners(final LocomotiveFunctionEvent functionEvent) {
    if (functionEvent.isValid()) {
      for (LocomotiveFunctionEventListener listener : this.locomotiveFunctionEventListeners) {
        listener.onFunctionChange(functionEvent);
      }
    }
  }

  private void notifyLocomotiveFunctionEventListeners(final LocomotiveFunctionEvent functionEvent) {
    executor.execute(() -> fireAllFunctionEventListeners(functionEvent));
  }

  private void fireAllDirectionEventListeners(final LocomotiveDirectionEvent directionEvent) {
    if (directionEvent.isValid()) {
      for (LocomotiveDirectionEventListener listener : this.locomotiveDirectionEventListeners) {
        listener.onDirectionChange(directionEvent);
      }
    }
  }

  private void notifyLocomotiveDirectionEventListeners(final LocomotiveDirectionEvent directionEvent) {
    executor.execute(() -> fireAllDirectionEventListeners(directionEvent));
  }

  private void fireAllLocomotiveSpeedEventListeners(final LocomotiveSpeedEvent speedEvent) {
    if (speedEvent.isValid()) {
      for (LocomotiveSpeedEventListener listener : this.locomotiveSpeedEventListeners) {
        listener.onSpeedChange(speedEvent);
      }
    }
  }

  private void notifyLocomotiveSpeedEventListeners(final LocomotiveSpeedEvent locomotiveEvent) {
    executor.execute(() -> fireAllLocomotiveSpeedEventListeners(locomotiveEvent));
  }

  private class CanFeedbackMessageListener implements FeedbackListener {

    private final MarklinCentralStationImpl controller;

    CanFeedbackMessageListener(MarklinCentralStationImpl controller) {
      this.controller = controller;
    }

    @Override
    public void onFeedbackMessage(final CanMessage message) {
      int cmd = message.getCommand();
      switch (cmd) {
        case CanMessage.S88_EVENT_RESPONSE -> {
          if (CanMessage.DLC_8 == message.getDlc()) {
            SensorEvent sme = new SensorEvent(message, new Date());
            if (sme.getSensorBean() != null) {
              controller.notifySensorEventListeners(sme);
            }
          }
        }
      }
    }
  }

  private class CanPingMessageListener implements CanPingListener {

    private final MarklinCentralStationImpl controller;

    CanPingMessageListener(MarklinCentralStationImpl controller) {
      this.controller = controller;
    }

    @Override
    public void onCanPingRequestMessage(final CanMessage message) {
      int cmd = message.getCommand();
      int dlc = message.getDlc();
      //int uid = message.getDeviceUidNumberFromMessage();
      switch (cmd) {
        case CanMessage.PING_REQ -> {
          //Lets do the when we know all of the CS
          if (controller.mainDevice != null) {
            if (CanMessage.DLC_0 == dlc) {
              //broadcast  
              controller.sendJCSUID();
            }
          }
        }
      }
    }

    @Override
    public void onCanPingResponseMessage(final CanMessage message
    ) {
      int cmd = message.getCommand();
      int dlc = message.getDlc();
      //int uid = message.getDeviceUidNumberFromMessage();
      switch (cmd) {
        case CanMessage.PING_RESP -> {
          if (CanMessage.DLC_8 == dlc) {
            controller.updateMember(message);
          }
        }
      }
    }

    @Override
    public void onCanStatusConfigRequestMessage(final CanMessage message
    ) {
      int cmd = message.getCommand();
      int dlc = message.getDlc();
      int uid = message.getDeviceUidNumberFromMessage();
      switch (cmd) {
        case CanMessage.STATUS_CONFIG -> {
          if (CanMessage.JCS_UID == uid && CanMessage.DLC_5 == dlc) {
            controller.sendJCSInformation();
          }
        }
      }
    }
  }

  private class CanSystemMessageListener implements SystemListener {

    private final MarklinCentralStationImpl controller;

    CanSystemMessageListener(MarklinCentralStationImpl controller) {
      this.controller = controller;
    }

    @Override
    public void onSystemMessage(CanMessage message) {
      int cmd = message.getCommand();
      int subcmd = message.getSubCommand();

      switch (cmd) {
        case CanMessage.SYSTEM_COMMAND_RESP -> {
          switch (subcmd) {
            case CanMessage.STOP_SUB_CMD -> {
              PowerEvent spe = new PowerEvent(message);
              controller.notifyPowerEventListeners(spe);
            }
            case CanMessage.GO_SUB_CMD -> {
              PowerEvent gpe = new PowerEvent(message);
              controller.notifyPowerEventListeners(gpe);
            }
            case CanMessage.HALT_SUB_CMD -> {
              PowerEvent gpe = new PowerEvent(message);
              controller.notifyPowerEventListeners(gpe);
            }
            case CanMessage.LOC_STOP_SUB_CMD -> {
              PowerEvent gpe = new PowerEvent(message);
              controller.notifyPowerEventListeners(gpe);
            }
            case CanMessage.OVERLOAD_SUB_CMD -> {
              PowerEvent gpe = new PowerEvent(message);
              controller.notifyPowerEventListeners(gpe);
            }
            default -> {
            }
          }
        }
      }
    }
  }

  private class CanAccessoryMessageListener implements AccessoryListener {

    private final MarklinCentralStationImpl controller;

    CanAccessoryMessageListener(MarklinCentralStationImpl controller) {
      this.controller = controller;
    }

    @Override
    public void onAccessoryMessage(CanMessage message) {
      int cmd = message.getCommand();
      switch (cmd) {
        case CanMessage.ACCESSORY_SWITCHING_RESP -> {
          AccessoryEvent ae = new AccessoryEvent(message);
          if (ae.isKnownAccessory()) {
            controller.notifyAccessoryEventListeners(ae);
          }
        }
      }
    }
  }

  private class CanLocomotiveMessageListener implements LocomotiveListener {

    private final MarklinCentralStationImpl controller;

    CanLocomotiveMessageListener(MarklinCentralStationImpl controller) {
      this.controller = controller;
    }

    @Override
    public void onLocomotiveMessage(CanMessage message) {
      int cmd = message.getCommand();
      switch (cmd) {
        case CanMessage.LOC_FUNCTION_RESP ->
          controller.notifyLocomotiveFunctionEventListeners(new LocomotiveFunctionEvent(message));
        case CanMessage.LOC_DIRECTION_RESP ->
          controller.notifyLocomotiveDirectionEventListeners(new LocomotiveDirectionEvent(message));
        case CanMessage.LOC_VELOCITY_RESP ->
          controller.notifyLocomotiveSpeedEventListeners(new LocomotiveSpeedEvent(message));
      }
    }
  }

//  
  public static void main(String[] a) {
    RunUtil.loadExternalProperties();
    MarklinCentralStationImpl cs = new MarklinCentralStationImpl(false);
    Logger.debug((cs.connect() ? "Connected" : "NOT Connected"));

    if (cs.isConnected()) {
      //Logger.debug("Power is " + (cs.isPower() ? "ON" : "Off"));

      //cs.getLocomotivesViaCAN();
      //cs.getAccessoriesViaCan();
      //cs3.pause(2000);
      //Logger.trace("getStatusDataConfig CS3");
      //cs3.getStatusDataConfigCS3();
      //cs3.pause(2000);
      //Logger.trace("getStatusDataConfig");
      //cs3.getStatusDataConfig();
      //cs3.pause(1000);
      //cs3.pause(4000);
      //cs3.power(false);
      //Logger.debug("Power is " + (cs.isPower() ? "ON" : "Off"));
      //cs3.power(true);
      //cs3.pause(500);
      //Logger.debug("Power is " + (cs.isPower() ? "ON" : "Off"));
      //cs3.sendJCSInfo();
      //cs3.pause(500);
      //Logger.debug("Power is " + (cs.isPower() ? "ON" : "Off"));
      //cs3.sendJCSInfo();
      //cs3.pause(500);
      //Logger.debug("Power is " + (cs.isPower() ? "ON" : "Off"));
      //cs3.sendJCSInfo();
      //SystemConfiguration data
//      cs.getStatusDataConfig();
//      Logger.debug("Channel 1: " + cs.channelData1.getChannel().getHumanValue() + " " + cs.channelData1.getChannel().getUnit());
//      Logger.debug("Channel 2: " + cs.channelData2.getChannel().getHumanValue() + " " + cs.channelData2.getChannel().getUnit());
//      Logger.debug("Channel 3: " + cs.channelData3.getChannel().getHumanValue() + " " + cs.channelData3.getChannel().getUnit());
//      Logger.debug("Channel 4: " + cs.channelData4.getChannel().getHumanValue() + " " + cs.channelData4.getChannel().getUnit());
      //cs.getSystemStatus(1);
//
//            Logger.debug("Channel 4....");
//            cs.getSystemStatus(4);
//Now get the systemstatus for all devices
//First the status data config must be called to get the channels
      //cs3.getSystemStatus()
      //            SystemStatusParser ss = cs.getSystemStatus();
      //            Logger.debug("1: "+ss);
      //
      //
      //            ss = cs.power(true);
      //            Logger.debug("3: "+ss);
      //
      //            cs.pause(1000);
      //            ss = cs.power(false);
      //            Logger.debug("4: "+ss);
      //            List<SensorMessageEvent> sml = cs.querySensors(48);
      //            for (SensorEvent sme : sml) {
      //                Sensor s = new Sensor(sme.getContactId(), sme.isNewValue() ? 1 : 0, sme.isOldValue() ? 1 : 0, sme.getDeviceIdBytes(), sme.getMillis(), new Date());
      //                Logger.debug(s.toLogString());
      //            }
      //List<AccessoryBean> asl = cs.getAccessoryStatuses();
      //for (AccessoryStatus as : asl) {
      //    Logger.debug(as.toString());
      //}
      //            for (int i = 0; i < 30; i++) {
      //                cs.sendIdle();
      //                pause(500);
      //            }
      //            Logger.debug("Sending  member ping\n");
      //            List<PingResponse> prl = cs.membersPing();
      //            //Logger.info("Query direction of loc 12");
      //            //DirectionInfo info = cs.getDirection(12, DecoderType.MM);
      //            Logger.debug("got " + prl.size() + " responses");
      //            for (PingResponseParser device : prl) {
      //                Logger.debug(device);
      //            }
      //            List<SensorMessageEvent> sel = cs.querySensors(48);
      //
      //            for (SensorEvent se : sel) {
      //                Logger.debug(se.toString());
      //            }
      //            FeedbackModule fm2 = new FeedbackModule(2);
      //            cs.queryAllPorts(fm2);
      //            Logger.debug(fm2.toLogString());
      //cs2.querySensor(1);
    }

    //PingResponse pr2 = cs.memberPing();
    //Logger.info("Query direction of loc 12");
    //DirectionInfo info = cs.getDirection(12, DecoderType.MM);
    //cs3.pause(500L);
    //Logger.debug("Wait for 1m");
    //cs.pause(1000 * 60 * 1);
    List<LocomotiveBean> locs = cs.getLocomotives();
    for (LocomotiveBean loc : locs) {
      Logger.trace(loc);
   }

    cs.pause(40000);
    cs.disconnect();
    cs.pause(100L);
    Logger.debug("DONE");
    //System.exit(0);
  }
  //for (int i = 0; i < 16; i++) {
  //    cs.requestFeedbackEvents(i + 1);
  //}

}
