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

import jcs.entities.LocomotiveBean;
import jcs.entities.RouteBean;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class RunState implements DispatcherState {

  private final LocomotiveBean locomotive;
  private final RouteBean route;
  
  RunState(LocomotiveBean locomotive, RouteBean route) {
    this.locomotive = locomotive;
    this.route = route;
  }

  
  @Override
  public void next(TrainDispatcher locRunner) {
    locRunner.setState(new EnterState(locomotive, route));
  }

  @Override
  public void prev(TrainDispatcher locRunner) {
    locRunner.setState(new ReserveRouteState(locomotive, route));
  }

  @Override
  public void logState() {
    Logger.debug("The Locomotive is running");
  }

  @Override
  public String toString() {
    return "RunState{}";
  }

}
