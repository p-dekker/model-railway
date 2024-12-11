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
package jcs.commandStation.events;

import java.io.Serializable;
import jcs.commandStation.marklin.cs.can.CanMessage;
import jcs.entities.FunctionBean;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs
 */
public class LocomotiveFunctionEvent implements Serializable {

  private FunctionBean function;

  public LocomotiveFunctionEvent(FunctionBean changedFunction) {
    this.function = changedFunction;
  }

  public LocomotiveFunctionEvent(long locomotiveBeanId, int functionNumber, boolean flag, String commandStationId) {
    createLocomotiveFunctionBean(locomotiveBeanId, functionNumber, flag, commandStationId);
  }

  //TODO: move Marklin code the the CS implementation
  public LocomotiveFunctionEvent(CanMessage message) {
    parseMessage(message);
  }

  private void createLocomotiveFunctionBean(long locomotiveBeanId, int functionNumber, boolean flag, String commandStationId) {
    function = new FunctionBean(locomotiveBeanId, functionNumber, flag ? 1 : 0);
  }

  private void parseMessage(CanMessage message) {
    CanMessage resp;
    if (!message.isResponseMessage()) {
      resp = message.getResponse();
    } else {
      resp = message;
    }

    if (resp.isResponseMessage() && CanMessage.LOC_FUNCTION_RESP == resp.getCommand()) {
      byte[] data = resp.getData();
      long locomotiveId = CanMessage.toInt(new byte[]{data[0], data[1], data[2], data[3]});

      int functionNumber = data[4];
      int functionValue = data[5];

      FunctionBean fb = new FunctionBean(functionNumber, locomotiveId);
      fb.setValue(functionValue);

      this.function = fb;
    } else {
      Logger.warn("Can't parse message, not an Locomotive Function Message! " + resp);
    }
  }

  public FunctionBean getFunctionBean() {
    return this.function;
  }

  public void setFunctionBean(FunctionBean function) {
    this.function = function;
  }

  public boolean isValid() {
    return this.function != null && this.function.getLocomotiveId() != null && this.function.getNumber() != null;
  }

  public boolean isEventFor(FunctionBean function) {
    if (function != null) {
      return this.function.getNumber().equals(function.getNumber()) && this.function.getLocomotiveId().equals(function.getLocomotiveId());
    } else {
      return false;
    }
  }

}
