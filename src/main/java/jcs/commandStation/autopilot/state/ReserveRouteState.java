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

import java.util.ArrayList;
import java.util.List;
import jcs.JCS;
import jcs.entities.AccessoryBean;
import jcs.entities.AccessoryBean.AccessoryValue;
import jcs.entities.LocomotiveBean;
import jcs.entities.RouteBean;
import jcs.entities.RouteElementBean;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class ReserveRouteState extends DispatcherState {

  ReserveRouteState(LocomotiveBean locomotive, RouteBean route) {
    super(locomotive, route);
  }

  @Override
  public void next(TrainDispatcher dispatcher) {
    if (this.canAdvanceState) {
      dispatcher.setState(new RunState(locomotive, route));
    } else {
      Logger.debug("Can't reserve a route for " + locomotive.getName() + " ...");
      dispatcher.setState(this);
    }
  }

  @Override
  public void prev(TrainDispatcher dispatcher) {
    dispatcher.setState(new SearchRouteState(locomotive));
  }

  @Override
  void onHalt(TrainDispatcher dispatcher) {
    Logger.debug("HALT!");
  }

  @Override
  public boolean performAction() {
    if (JCS.getJcsCommandStation() == null) {
      Logger.error("Can't obtain a Command Station");
      canAdvanceState = false;
      return false;
    }

    Logger.debug("Reserving route " + route);
    this.route.setLocked(true);
    PersistenceFactory.getService().persist(route);

    // need the turnouts
    List<RouteElementBean> turnouts = getTurnouts(route);
    Logger.trace("There are " + turnouts.size() + " turnouts in this route");

    //Set the turnout in the right direction
    for (RouteElementBean reb : turnouts) {
      AccessoryValue av = reb.getAccessoryValue();
      AccessoryBean turnout = reb.getTileBean().getAccessoryBean();

      Logger.debug("Setting turnout " + turnout.getName() + " [" + turnout.getAddress() + "] to : " + av.getValue());
      JCS.getJcsCommandStation().switchAccessory(turnout, av);
    }
    Logger.debug("Locked route " + route);
    canAdvanceState = true;
    
    
    
    
    return canAdvanceState;
  }

  List<RouteElementBean> getTurnouts(RouteBean routeBean) {
    List<RouteElementBean> rel = routeBean.getRouteElements();
    List<RouteElementBean> turnouts = new ArrayList<>();
    for (RouteElementBean reb : rel) {
      if (reb.isTurnout()) {
        turnouts.add(reb);
      }
    }
    return turnouts;
  }

}
