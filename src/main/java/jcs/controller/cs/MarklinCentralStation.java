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

import jcs.entities.MeasurementChannel;
import jcs.entities.Device;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import jcs.entities.AccessoryBean;
import jcs.entities.LocomotiveBean;
import jcs.entities.enums.AccessoryValue;
import jcs.entities.enums.Direction;
import jcs.controller.events.AccessoryEventListener;
import jcs.controller.events.LocomotiveDirectionEventListener;
import jcs.controller.events.LocomotiveFunctionEventListener;
import jcs.controller.events.LocomotiveSpeedEventListener;
import jcs.controller.events.PowerEventListener;
import jcs.controller.events.SensorEventListener;

public interface MarklinCentralStation {

  boolean connect();

  boolean isConnected();

  void disconnect();

  boolean isPower();

  boolean power(boolean on);

  void changeDirection(int locUid, Direction direction);

  void changeVelocity(int locUid, int speed);

  void changeFunctionValue(int locUid, int functionNumber, boolean flag);

  void switchAccessory(int address, AccessoryValue value);

  void switchAccessory(int address, AccessoryValue value, int switchTime);

  void addPowerEventListener(PowerEventListener listener);

  void removePowerEventListener(PowerEventListener listener);

  void addSensorEventListener(SensorEventListener listener);

  void removeSensorEventListener(SensorEventListener listener);

  void addAccessoryEventListener(AccessoryEventListener listener);

  void removeAccessoryEventListener(AccessoryEventListener listener);

  void addLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener);

  void removeLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener);

  void addLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener);

  void removeLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener);

  void addLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener);

  void removeLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener);

  List<LocomotiveBean> getLocomotives();

  Image getLocomotiveImage(String icon);

  Image getLocomotiveFunctionImage(String icon);

  List<AccessoryBean> getSwitches();

  List<AccessoryBean> getSignals();

  Device getDevice();

  List<Device> getDevices();

  void clearCaches();

  Map<Integer, MeasurementChannel> getTrackMeasurements();

}
