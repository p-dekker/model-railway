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
package jcs.commandStation;

import java.awt.Image;
import jcs.commandStation.events.AccessoryEventListener;
import jcs.commandStation.events.LocomotiveDirectionEventListener;
import jcs.commandStation.events.LocomotiveFunctionEventListener;
import jcs.commandStation.events.LocomotiveSpeedEventListener;
import jcs.commandStation.events.MeasurementEventListener;
import jcs.commandStation.events.PowerEventListener;
import jcs.commandStation.events.SensorEventListener;
import jcs.entities.AccessoryBean;
import jcs.entities.AccessoryBean.AccessoryValue;
import jcs.entities.InfoBean;
import jcs.entities.LocomotiveBean;

/**
 * The Track repository contain all track item which are used on the Track This can be Locomotives, Turnouts, Signals, etc There For future use the implementation of the Repository could be changed to
 * an other storage provider
 *
 * @author frans
 */
public interface JCSCommandStation {

  void switchPower(boolean on);

  boolean isPowerOn();

  boolean connect();

  boolean isConnected();

  void disconnect();

  void addPowerEventListener(PowerEventListener listener);

  void removePowerEventListener(PowerEventListener listener);

  void changeLocomotiveDirection(LocomotiveBean.Direction direction, LocomotiveBean locomotive);

  void changeLocomotiveSpeed(Integer speed, LocomotiveBean locomotive);

  void changeLocomotiveFunction(Boolean value, Integer functionNumber, LocomotiveBean locomotive);

  void switchAccessory(AccessoryValue value, AccessoryBean accessory);

  void addAccessoryEventListener(AccessoryEventListener listener);

  void removeAccessoryEventListener(AccessoryEventListener listener);

  void addSensorEventListener(SensorEventListener listener);

  void removeSensorEventListener(SensorEventListener listener);

  void addLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener);

  void removeLocomotiveFunctionEventListener(LocomotiveFunctionEventListener listener);

  void addLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener);

  void removeLocomotiveDirectionEventListener(LocomotiveDirectionEventListener listener);

  void addLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener);

  void removeLocomotiveSpeedEventListener(LocomotiveSpeedEventListener listener);

  void addMeasurementEventListener(MeasurementEventListener listener);

  void removeMeasurementListener(MeasurementEventListener listener);

  InfoBean getCommandStationInfo();
  
  //String getCommandStationName();

  //String getCommandStationSerialNumber();

  //String getCommandStationArticleNumber();

  //void synchronizeLocomotivesWithCommandStation(PropertyChangeListener progressListener);

  //void synchronizeTurnoutsWithCommandStation();

  //void synchronizeSignalsWithCommandStation();

  Image getLocomotiveImage(String imageName);

  Image getLocomotiveFunctionImage(String imageName);
}
