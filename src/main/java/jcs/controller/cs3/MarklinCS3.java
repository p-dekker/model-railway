/*
 * Copyright (C) 2020 Frans Jacobs.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package jcs.controller.cs3;

import jcs.controller.cs3.can.parser.DirectionInfo;
import jcs.controller.cs3.can.parser.PingResponseParser;
import jcs.controller.cs3.can.parser.SystemStatusParser;
import jcs.controller.cs3.can.parser.StatusDataConfigParser;
import java.awt.Image;
import jcs.controller.cs3.events.SensorMessageEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jcs.controller.ControllerEvent;
import jcs.controller.ControllerEventListener;
import jcs.controller.cs3.can.CanMessage;
import jcs.controller.cs3.can.CanMessageFactory;
import static jcs.controller.cs3.can.MarklinCan.FUNCTION_OFF;
import static jcs.controller.cs3.can.MarklinCan.FUNCTION_ON;
import jcs.controller.cs3.events.CanMessageListener;
import jcs.controller.cs3.http.AccessoryBeanParser;
import jcs.controller.cs3.http.LocomotiveBeanParser;
import jcs.controller.cs3.net.CS3ConnectionFactory;
import jcs.controller.cs3.net.HTTPConnection;
import jcs.entities.enums.AccessoryValue;
import jcs.entities.enums.Direction;
import jcs.entities.enums.DecoderType;
import jcs.controller.HeartbeatListener;
import jcs.controller.cs3.events.SensorMessageListener;
import jcs.controller.cs3.http.SvgIconToPngIconConverter;
import jcs.entities.AccessoryBean;
import jcs.entities.LocomotiveBean;
import org.tinylog.Logger;
import jcs.controller.MarklinController;
import jcs.controller.cs3.can.parser.ChannelDataParser;
import jcs.controller.cs3.devices.GFP;
import jcs.controller.cs3.devices.LinkSxx;
import jcs.controller.cs3.http.DeviceJSONParser;
import jcs.controller.cs3.net.CS3Connection;

/**
 *
 * @author Frans Jacobs
 */
public class MarklinCS3 implements MarklinController {

    private CS3Connection connection;
    private boolean connected = false;
    private final Queue<CanMessage> messageQueue;

    //CS3 properties
    private GFP gfp;
    private int gfpUid;

    private LinkSxx linkSxx;
    private int linkSxxUid;

    private int cs3Uid;
    private String cs3Name;
    private String cs3Version;
    private int cs3DeviceId;

    private String serialNumber;
    private String hardwareVersion;
    private String articleNumber;

    private final List<ControllerEventListener> controllerEventListeners;

    private final List<SensorMessageListener> sensorMessageEventListeners;

    private final ExecutorService executor;

    private static final long DELAY = 0L;

    //Device via CAN
    private StatusDataConfigParser cs3Device;

    public MarklinCS3() {
        this(true);
    }

    //For testing
    MarklinCS3(boolean connect) {
        messageQueue = new LinkedList<>();
        //devices = new HashMap<>();

        controllerEventListeners = new LinkedList<>();
        sensorMessageEventListeners = new LinkedList<>();
        executor = Executors.newCachedThreadPool();

        if (connect) {
            connect();
        }
    }

    int getGfpUid() {
        return gfpUid;
    }

    int getLinkSxxUid() {
        return linkSxxUid;
    }

    int getCs3Uid() {
        return cs3Uid;
    }

    @Override
    public String getName() {
        return this.cs3Name;
    }

    public String getIp() {
        return CS3ConnectionFactory.getControllerIp();
    }

    @Override
    public final boolean connect() {
        if (!connected) {
            Logger.trace("Connecting to CS3...");
            //Obtain some info from the CS 3 connected to
            getAppDevices();
            //Basically the same, less info via CAN
            getMembers();

            CS3Connection cs3Connection = CS3ConnectionFactory.getConnection();
            this.connection = cs3Connection;
            this.connected = cs3Connection != null;
        }

        return connected;
    }

    /**
     * The CS3 has a Web App API which is used for the Web GUI. The Internal
     * devices can be obtained calling this API which returns a JSON string,
     * From this JSON all devices are found. Most important is the GFP which is
     * the heart of the CS 3 most CAN Command need the GFP UID. This dat can
     * also be obtained using the CAN Member PING command, but The JSON gives a
     * little more detail
     *
     * @return
     */
    void getAppDevices() {
        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
        String deviceJSON = httpCon.getDevicesJSON();
        DeviceJSONParser dp = new DeviceJSONParser();
        dp.parseDevices(deviceJSON);

        this.cs3Uid = Integer.parseInt(dp.getCs3().getUid().substring(2), 16);
        this.cs3Name = dp.getCs3().getName();
        this.gfp = dp.getGfp();
        this.gfpUid = Integer.parseInt(this.gfp.getUid().substring(2), 16);
        this.linkSxx = dp.getLinkSxx();
        this.linkSxxUid = Integer.parseInt(this.linkSxx.getUid().substring(2), 16);

        Logger.trace("CS3 uid: " + dp.getCs3().getUid());
        Logger.trace("CS3: " + this.cs3Name);
        Logger.trace("GFP uid: " + this.gfp.getUid());
        Logger.trace("GFP Article: " + this.gfp.getArticleNumber());
        Logger.trace("GFP version: " + this.gfp.getVersion());
        Logger.trace("GFP Serial: " + this.gfp.getSerial());
        Logger.trace("GFP id: " + this.gfp.getIdentifier());

        Logger.trace("LinkSxx uid: " + this.linkSxx.getUid());
        Logger.trace("LinkSxx id: " + this.linkSxx.getIdentifier());
        Logger.trace("LinkSxx serial: " + this.linkSxx.getSerialNumber());
        Logger.trace("LinkSxx version: " + this.linkSxx.getVersion());
        Logger.trace("LinkSxx sensors: " + this.linkSxx.getTotalSensors());
    }

    /**
     * Send a "Ping message to all members on the CAN bus Each device responds
     * with the appropriate data. In this way, the configuration query of all
     * participants that can be reached on the CAN bus is achieved. DLC = 0:
     * Query of all participants on the bus. DLC = 8: When responding, the UID
     * is replaced by that of the responding device. Thus, the graphical user
     * interface processor can determine which devices are connected. Version
     * number is an identifier of the software version.
     *
     * @return List with Ping responses from the members
     */
    private List<PingResponseParser> membersPing() {
        CanMessage msg = sendMessage(CanMessageFactory.getMemberPing());
        List<CanMessage> rl = msg.getResponses();
        List<PingResponseParser> prl = new ArrayList<>(rl.size());
        for (CanMessage r : rl) {
            prl.add(new PingResponseParser(r));
        }
        return prl;
    }

    /**
     * Get the CAN Member using the membersPing For now only log the found
     * members not sure what to do with this information
     */
    void getMembers() {
        Map<Integer, PingResponseParser> devices = new HashMap<>();

        List<PingResponseParser> dl = membersPing();
        for (PingResponseParser d : dl) {
            devices.put(d.getSenderDeviceUid(), d);
        }

        //Need to do it twice to be sure all devices are captured
        dl = membersPing();
        for (PingResponseParser d : dl) {
            devices.put(d.getSenderDeviceUid(), d);
        }

        Logger.trace("Found " + devices.size() + " members");

        for (PingResponseParser d : devices.values()) {
            if (d.getSenderDeviceUid() == this.cs3Uid) {
                Logger.trace("Found CS3 " + d);
            } else if (d.getSenderDeviceUid() == this.gfpUid) {
                Logger.trace("Found GFP");
            } else if (d.getSenderDeviceUid() == this.linkSxxUid) {
                Logger.trace("Found LinkSxx");
            } else {
                Logger.trace("Found: " + d);
            }
        }
    }

    /**
     * Query the System Status
     *
     * @return true the track power is on else off.
     */
    @Override
    public boolean isPower() {
        if (this.connected) {
            CanMessage m = sendMessage(CanMessageFactory.querySystem(this.gfpUid));

            Logger.trace("Received " + m.getResponses().size() + " responses. RX:" + m.getResponse());
            SystemStatusParser ss = new SystemStatusParser(m);
            return ss.isPower();
        } else {
            return false;
        }
    }

    /**
     * System Stop and GO When on = true then the GO command is issued: The
     * track format processor activates the operation and supplies electrical
     * energy. Any speed levels/functions that may still exist or have been
     * saved will be sent again. when false the Stop command is issued: Track
     * format processor stops operation on main and programming track.
     * Electrical energy is no longer supplied. All speed levels/function values
     * and settings are retained.
     *
     * @param on true Track power On else Off
     * @return true the Track power is On else Off
     */
    @Override
    public boolean power(boolean on) {
        if (this.connected) {
            SystemStatusParser ss = new SystemStatusParser(sendMessage(CanMessageFactory.systemStopGo(on, gfpUid)));
            return ss.isPower();
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
            this.connection.close();
            connected = false;
            executor.shutdown();
        } catch (Exception ex) {
            Logger.error(ex);
        }
    }

    void getStatusConfig(int index) {
        if (this.connected) {
            CanMessage message = sendMessage(CanMessageFactory.statusDataConfig(index, gfpUid));

            StatusDataConfigParser sdcp = new StatusDataConfigParser(message);

            Logger.debug(sdcp);

            message = sendMessage(CanMessageFactory.statusDataConfig(1, gfpUid));
            ChannelDataParser cdp = new ChannelDataParser(message);
            Logger.debug(cdp);

            message = sendMessage(CanMessageFactory.statusDataConfig(2, gfpUid));
            cdp = new ChannelDataParser(message);
            Logger.debug(cdp);

            message = sendMessage(CanMessageFactory.statusDataConfig(3, gfpUid));
            cdp = new ChannelDataParser(message);
            Logger.debug(cdp);

            message = sendMessage(CanMessageFactory.statusDataConfig(4, gfpUid));
            cdp = new ChannelDataParser(message);
            Logger.debug(cdp);

        }
    }

    /**
     * Blocking call to the message sender thread which send the message and
     * await the response. When there is no response within 1s the waiting is
     * cancelled
     *
     * @param canMessage to send
     * @return the CanMessage with responses
     */
    private CanMessage sendMessage(CanMessage canMessage) {
        if (this.connection != null) {
            this.connection.sendCanMessage(canMessage);
        } else {
            Logger.warn("NOT connected!");
        }
        return canMessage;
    }

    private int getLocoAddres(int address, DecoderType decoderType) {
        int locoAddress;
        switch (decoderType) {
            case MFX:
                locoAddress = 0x4000 + address;
                break;
            case DCC:
                locoAddress = 0xC000 + address;
                break;
            case SX1:
                locoAddress = 0x0800 + address;
                break;
            case MM:
                locoAddress = address;
                break;
            default:
                locoAddress = address;
                break;
        }

        return locoAddress;
    }

    //@Override
    public void toggleDirection(int address, DecoderType decoderType) {
        int la = getLocoAddres(address, decoderType);
        CanMessage msg = sendMessage(CanMessageFactory.queryDirection(la, this.gfpUid));
        DirectionInfo di = new DirectionInfo(msg);
        Logger.trace(di);
        Direction direction = di.getDirection();
        direction = direction.toggle();

        changeDirection(address, decoderType, direction);
    }

    @Override
    public void changeDirection(int address, DecoderType decoderType, Direction direction) {
        int la = getLocoAddres(address, decoderType);
        Logger.trace("Setting direction to: " + direction + " for loc address: " + la + " Decoder: " + decoderType);
        sendMessage(CanMessageFactory.setDirection(la, direction.getMarklinValue(), this.gfpUid));
    }

    public DirectionInfo getDirection(int address, DecoderType decoderType) {
        int la = getLocoAddres(address, decoderType);
        DirectionInfo di = new DirectionInfo(sendMessage(CanMessageFactory.queryDirection(la, this.gfpUid)));
        Logger.trace(di);
        return di;
    }

    @Override
    public void setSpeed(int address, DecoderType decoderType, int speed) {
        int la = getLocoAddres(address, decoderType);
        Logger.trace("Setting speed to: " + speed + " for loc address: " + la + " Decoder: " + decoderType);

        //Calculate the speed??
        sendMessage(CanMessageFactory.setLocSpeed(la, speed, this.gfpUid));
    }

    @Override
    public void setFunction(int address, DecoderType decoderType, int functionNumber, boolean flag) {
        int value = flag ? FUNCTION_ON : FUNCTION_OFF;
        int la = getLocoAddres(address, decoderType);
        sendMessage(CanMessageFactory.setFunction(la, functionNumber, value, this.gfpUid));
    }

    @Override
    public void switchAccessory(int address, AccessoryValue value) {
        executor.execute(() -> switchAccessoryOnOff(address, value));
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

    private void switchAccessoryOnOff(int address, AccessoryValue value) {
        sendMessage(CanMessageFactory.switchAccessory(address, value, true, this.gfpUid));
        //TODO: dynamic setting of time or messageQueue it
        wait200ms();
        sendMessage(CanMessageFactory.switchAccessory(address, value, false, this.gfpUid));
    }

    @Override
    public List<LocomotiveBean> getLocomotives() {
        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
        String lokomotiveCs2 = httpCon.getLocomotivesFile();
        LocomotiveBeanParser lp = new LocomotiveBeanParser();
        return lp.parseLocomotivesFile(lokomotiveCs2);
    }

    @Override
    public void cacheAllFunctionIcons() {
        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
        String json = httpCon.getAllFunctionsSvgJSON();
        SvgIconToPngIconConverter svgp = new SvgIconToPngIconConverter();
        svgp.convertAndCacheAllFunctionsSvgIcons(json);
    }

    @Override
    public List<AccessoryBean> getAccessories() {
        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
        String magnetartikelCs2 = httpCon.getAccessoriesFile();
        AccessoryBeanParser ap = new AccessoryBeanParser();
        return ap.parseAccessoryFile(magnetartikelCs2);
    }

    @Override
    public Image getLocomotiveImage(String icon) {
        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
        Image locIcon = httpCon.getLocomotiveImage(icon);
        return locIcon;
    }

//    @Override
//    public StatusDataConfigParser getControllerInfo() {
//        if (cs3Device == null) {
//            HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
//            String deviceFile = httpCon.getDeviceFile();
//            DeviceFileParser dp = new DeviceFileParser();
//            cs3Device = dp.parseAccessoryFile(deviceFile);
//        }
//        return cs3Device;
//    }
    //@Override
    public void addControllerEventListener(ControllerEventListener listener) {
        this.controllerEventListeners.add(listener);
    }

    //@Override
    public void removeControllerEventListener(ControllerEventListener listener) {
        this.controllerEventListeners.remove(listener);
    }

    //@Override
    public void notifyAllControllerEventListeners() {
        Logger.info("Current Controller Power Status: " + (isPower() ? "On" : "Off") + "...");
        executor.execute(() -> notifyControllerEventListeners(new ControllerEvent(isPower(), isConnected())));
    }

    //@Override
    public void addHeartbeatListener(HeartbeatListener listener) {
        //synchronized (heartbeatListeners) {
        //    this.heartbeatListeners.add(listener);
        //}
    }

    //@Override
    public void removeHeartbeatListener(HeartbeatListener listener) {
        //synchronized (heartbeatListeners) {
        //    this.heartbeatListeners.remove(listener);
        //}
    }

    //@Override
    public void removeAllHeartbeatListeners() {
        //synchronized (heartbeatListeners) {
        //    this.heartbeatListeners.clear();
        //}
    }

    //@Override
    public void addCanMessageListener(CanMessageListener listener) {
        //if (this.connection != null) {
        //    this.connection.addCanMessageListener(listener);
        //}
    }

    //@Override
    public void removeCanMessageListener(CanMessageListener listener) {
        //if (this.connection != null) {
        //    this.connection.addCanMessageListener(listener);
        //}
    }

    @Override
    public void addSensorMessageListener(SensorMessageListener listener) {
        this.sensorMessageEventListeners.add(listener);
    }

    @Override
    public void removeSensorMessageListener(SensorMessageListener listener) {
        this.sensorMessageEventListeners.remove(listener);
    }

    public SensorMessageEvent querySensor(int contactId) {
        CanMessage msg = null;//sendMessage(CanMessageFactory.querySensor(contactId));
        CanMessage resp = msg.getResponse();
        if (resp.isResponseFor(msg)) {
            return new SensorMessageEvent(msg);
        } else {
            return null;
        }
    }

    //@Override
    public List<SensorMessageEvent> querySensors(int sensorCount) {
        Logger.trace("Query Contacts from 1 until: " + sensorCount);

        List<SensorMessageEvent> sel = new ArrayList<>(sensorCount);

        int fromContactId = 1;
        int toContactId = sensorCount;
        CanMessage msg = null; //sendMessage(CanMessageFactory.querySensors(fromContactId, toContactId));

        List<CanMessage> responses = msg.getResponses();
        Logger.trace("Got " + responses.size() + " responses...");

        for (CanMessage rm : responses) {
            SensorMessageEvent se = new SensorMessageEvent(rm);
            sel.add(se);
        }

        return sel;
    }

    private void notifyControllerEventListeners(ControllerEvent event) {
        Set<ControllerEventListener> snapshot;
        synchronized (controllerEventListeners) {
            if (controllerEventListeners.isEmpty()) {
                snapshot = new HashSet<>();
            } else {
                snapshot = new HashSet<>(controllerEventListeners);
            }
        }

        for (ControllerEventListener listener : snapshot) {
            listener.notify(event);
        }
    }

    private void notifySensorMessageEventListeners(SensorMessageEvent event) {
        for (SensorMessageListener listener : sensorMessageEventListeners) {
            listener.onSensorMessage(event);
        }
    }

//    private class ExtraMessageListener implements CanMessageListener {
//
//        private final MarklinCS3 controller;
//
//        ExtraMessageListener(MarklinCS3 cs2Controller) {
//            controller = cs2Controller;
//        }
//
//        @Override
//        public void onCanMessage(CanMessageEvent canEvent) {
//            CanMessage msg = canEvent.getCanMessage();
//            int cmd = msg.getCommand();
//
//            switch (cmd) {
//                case MarklinCan.S88_EVENT_RESPONSE:
//                    SensorMessageEvent sme = new SensorMessageEvent(msg);
//                    Logger.trace(sme);
//                    controller.notifySensorMessageEventListeners(sme);
//                    break;
//                default:
//                    //Logger.trace("Message: " + msg);
//                    break;
//            }
//        }
//    }
    //Test
    public static void main(String[] a) {

        MarklinCS3 cs3 = new MarklinCS3(false);
        Logger.debug((cs3.connect() ? "Connected" : "NOT Connected"));

        if (cs3.isConnected()) {
            Logger.debug("Power is " + (cs3.isPower() ? "ON" : "Off"));
            
            cs3.power(false);
            cs3.pause(500);
            Logger.debug("Power is " + (cs3.isPower() ? "ON" : "Off"));
            cs3.power(true);
            cs3.pause(500);
            Logger.debug("Power is " + (cs3.isPower() ? "ON" : "Off"));
            
            //SystemConfiguration data
            cs3.getStatusConfig(0);

//Now get the systemstatus for all devices

//First the status data config must be called to get the channels
            //cs3.getSystemStatus()
            //            SystemStatusParser ss = cs3.getSystemStatus();
            //            Logger.debug("1: "+ss);
            //
            //
            //            ss = cs3.power(true);
            //            Logger.debug("3: "+ss);
            //
            //            cs3.pause(1000);
            //            ss = cs3.power(false);
            //            Logger.debug("4: "+ss);
            //            List<SensorMessageEvent> sml = cs3.querySensors(48);
            //            for (SensorMessageEvent sme : sml) {
            //                Sensor s = new Sensor(sme.getContactId(), sme.isNewValue() ? 1 : 0, sme.isOldValue() ? 1 : 0, sme.getDeviceIdBytes(), sme.getMillis(), new Date());
            //                Logger.debug(s.toLogString());
            //            }
            //List<AccessoryBean> asl = cs3.getAccessoryStatuses();
            //for (AccessoryStatus as : asl) {
            //    Logger.debug(as.toString());
            //}
            //            for (int i = 0; i < 30; i++) {
            //                cs3.sendIdle();
            //                pause(500);
            //            }
            //            Logger.debug("Sending  member ping\n");
            //            List<PingResponse> prl = cs3.membersPing();
            //            //Logger.info("Query direction of loc 12");
            //            //DirectionInfo info = cs3.getDirection(12, DecoderType.MM);
            //            Logger.debug("got " + prl.size() + " responses");
            //            for (PingResponseParser device : prl) {
            //                Logger.debug(device);
            //            }
            //            List<SensorMessageEvent> sel = cs3.querySensors(48);
            //
            //            for (SensorMessageEvent se : sel) {
            //                Logger.debug(se.toString());
            //            }
            //            FeedbackModule fm2 = new FeedbackModule(2);
            //            cs3.queryAllPorts(fm2);
            //            Logger.debug(fm2.toLogString());
            //cs2.querySensor(1);
        }

        //PingResponse pr2 = cs3.memberPing();
        //Logger.info("Query direction of loc 12");
        //DirectionInfo info = cs3.getDirection(12, DecoderType.MM);
//        cs3.pause(5L);
        cs3.disconnect();
        cs3.pause(100L);
        Logger.debug("DONE");
        //System.exit(0);
    }
    //for (int i = 0; i < 16; i++) {
    //    cs3.requestFeedbackEvents(i + 1);
    //}

}
//    @Override
//    public List<AccessoryStatus> getAccessoryStatuses() {
//This piece does not seem to work with a CS3
//TO sort out how to get the statuses
//Update the file by sending a configRequest first
//        CanMessage msg = connection.sendCanMessage(CanMessageFactory.requestConfig("magstat"));
//        //give it some time to process
//        pause(100L);
//        HTTPConnection httpCon = CS3ConnectionFactory.getHTTPConnection();
//        String accessoryStatuses = httpCon.getAccessoryStatusesFile();
//        AccessoryBeanParser ap = new AccessoryBeanParser();
//        return ap.parseAccessoryStatusFile(accessoryStatuses);
//STUB
//        return Collections.EMPTY_LIST;
//    }
