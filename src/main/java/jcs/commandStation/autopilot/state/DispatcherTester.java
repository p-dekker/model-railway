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

/**
 *
 * @author frans
 */
public class DispatcherTester {
 
  public static void main(String[] a) {
    LocomotiveBean loc = new LocomotiveBean(8L, "NS DHG 6505", 8L, 8, "/Users/fransjacobs/jcs/cache/dcc-ex/ns dhg 6505.png", "dcc", 100, 1, 0, 0, false, true, true);
    loc.setCommandStationId("virtual");
    
    TrainDispatcher dispatcher = new TrainDispatcher(loc);
    
    //idle
    dispatcher.showStatus();
    
    dispatcher.nextState();
    //prpare
    dispatcher.showStatus();

    dispatcher.nextState();
    //departure
    dispatcher.showStatus();

    dispatcher.nextState();
    //Running
    dispatcher.showStatus();

    dispatcher.nextState();
    //Arriving
    dispatcher.showStatus();

    dispatcher.nextState();
    //Waiting
    dispatcher.showStatus();

    dispatcher.nextState();
    //prpare
    dispatcher.showStatus();
    
  }
  
}
