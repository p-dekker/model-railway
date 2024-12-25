/*
 * Copyright 2024 fransjacobs.
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
package jcs.commandStation.esu.ecos;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jcs.commandStation.events.SensorEvent;
import jcs.entities.FeedbackModuleBean;
import org.tinylog.Logger;

/**
 *
 * Feedback-Manager (id=26)
 */
class FeedbackManager {

  public static final int ID = Ecos.FEEDBACK_MANAGER_ID;
  public static final int S88_OFFSET = 100;

//  private int size;
  private final EsuEcosCommandStationImpl ecosCommandStation;
  private final Map<Integer, FeedbackModuleBean> modules;

  FeedbackManager(EsuEcosCommandStationImpl ecosCommandStation, EcosMessage message) {
    this.ecosCommandStation = ecosCommandStation;
    modules = new HashMap<>();
    parse(message);
  }

  private List<SensorEvent> parse(EcosMessage message) {
    Logger.trace(message.getMessage());
    Logger.trace(message.getResponse());

    List<SensorEvent> changedSensors;
    boolean event = message.isEvent();
    Map<String, Object> values = message.getValueMap();
    int objectId = message.getObjectId();

    if (ID != objectId) {
      FeedbackModuleBean s88;
      if (this.modules.containsKey(objectId)) {
        s88 = this.modules.get(objectId);
      } else {
        s88 = new FeedbackModuleBean();
        s88.setId(objectId);
        s88.setAddressOffset(S88_OFFSET);
        s88.setModuleNumber(objectId);
      }

      if (values.containsKey(Ecos.PORTS)) {
        String vports = values.get(Ecos.PORTS).toString();
        if (vports != null) {
          int ports = Integer.parseInt(vports);
          s88.setPortCount(ports);
        }
      }

      if (values.containsKey(Ecos.STATE)) {
        String state = values.get(Ecos.STATE).toString();
        updatePorts(state, s88);
      }
      this.modules.put(objectId, s88);
      changedSensors = s88.getChangedSensors();

      if (event) {
        if (this.ecosCommandStation != null) {
          for (SensorEvent sensorEvent : changedSensors) {
            this.ecosCommandStation.fireSensorEventListeners(sensorEvent);
          }
        }
      }
    } else {
      if (Ecos.FEEDBACK_MANAGER_ID == objectId) {
        if (values.containsKey(Ecos.SIZE)) {
          int size = Integer.parseInt(values.get(Ecos.SIZE).toString());
          for (int i = 0; i < size; i++) {
            FeedbackModuleBean fbmb = new FeedbackModuleBean();
            fbmb.setAddressOffset(S88_OFFSET);
            fbmb.setModuleNumber(S88_OFFSET + i);
            fbmb.setId(S88_OFFSET + i);
            this.modules.put(fbmb.getId(), fbmb);
          }
        }

      }
      changedSensors = Collections.EMPTY_LIST;
    }
    return changedSensors;
  }

  List<SensorEvent> update(EcosMessage message) {
    List<SensorEvent> changedSensors = parse(message);
    return changedSensors;
  }

  public int getSize() {
    return this.modules.size();
  }

  void updatePorts(String state, FeedbackModuleBean s88) {
    String val = state.replace("0x", "");
    int l = 4 - val.length();
    for (int i = 0; i < l; i++) {
      val = "0" + val;
    }

    int[] ports = s88.getPorts();
    int[] prevPorts = s88.getPrevPorts();

    if (ports == null) {
      ports = new int[FeedbackModuleBean.DEFAULT_PORT_COUNT];
      prevPorts = new int[FeedbackModuleBean.DEFAULT_PORT_COUNT];
    }
    //Set the previous ports State
    System.arraycopy(ports, 0, prevPorts, 0, ports.length);
    s88.setPrevPorts(prevPorts);

    int stateVal = Integer.parseInt(val, 16);
    //Logger.trace(state + " -> " + stateVal);
    for (int i = 0; i < ports.length; i++) {
      int m = ((int) Math.pow(2, i));
      int pv = (stateVal & m) > 0 ? 1 : 0;
      ports[i] = pv;
    }
    s88.setPorts(ports);
  }

  public Map<Integer, FeedbackModuleBean> getModules() {
    return modules;
  }

  public FeedbackModuleBean getFeedbackModule(int id) {
    return this.modules.get(id);
  }
}
