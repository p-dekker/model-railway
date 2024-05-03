/*
 * Copyright 2024 frans.
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
package jcs.commandStation.autopilot.state;

import jcs.JCS;
import jcs.commandStation.autopilot.TrainDispatcher;
import jcs.entities.LocomotiveBean;
import jcs.entities.RouteBean;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class ArrivalState extends DispatcherState {

  public ArrivalState(TrainDispatcher dispatcher) {
    super(dispatcher);
  }

  @Override
  public void next(TrainDispatcher locRunner) {
    Logger.trace("canAdvanceState: " + canAdvanceToNextState);
    if (canAdvanceToNextState) {
      DispatcherState newState = new ArrivedState(this.dispatcher);
      newState.setRunning(running);
      locRunner.setDispatcherState(newState);
    } else {
      locRunner.setDispatcherState(this);
    }
  }

  @Override
  public void execute() {
    //When the arrival event goes of this is executed
    if (this.dispatcher.isEnterDestinationBlock()) {
      Logger.debug("Train has entered the destination block. Slow down");
      LocomotiveBean locomotive = this.dispatcher.getLocomotiveBean();
      RouteBean route = this.dispatcher.getRouteBean();
      //Slowdown....
      JCS.getJcsCommandStation().changeLocomotiveSpeed(100, locomotive);
      Logger.debug(locomotive.getName() + " has entered destination " + route.getToTileId() + "...");
      this.canAdvanceToNextState = true;
    }
    Logger.trace("Can advance to next state: " + canAdvanceToNextState);
  }

}
