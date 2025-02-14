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

import java.util.List;
import jcs.commandStation.events.DisconnectionEventListener;
import jcs.entities.CommandStationBean;
import jcs.commandStation.entities.DeviceBean;
import jcs.commandStation.entities.InfoBean;

public interface GenericController {

  CommandStationBean getCommandStationBean();

  boolean connect();

  boolean isConnected();

  void disconnect();

  void setVirtual(boolean flag);

  boolean isVirtual();

  void addDisconnectionEventListener(DisconnectionEventListener listener);

  void removeDisconnectionEventListener(DisconnectionEventListener listener);

  InfoBean getCommandStationInfo();

  DeviceBean getDevice();

  List<DeviceBean> getDevices();

  String getIp();

}
