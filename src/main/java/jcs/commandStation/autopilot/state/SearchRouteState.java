/*
 * Copyright 2024 Frans Jacobs.
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

import java.util.List;
import java.util.Random;
import jcs.entities.BlockBean;
import jcs.entities.LocomotiveBean;
import jcs.entities.LocomotiveBean.Direction;
import jcs.entities.RouteBean;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 * Search a (free) Route and choose one
 */
public class SearchRouteState extends DispatcherState {

  public SearchRouteState(TrainDispatcher dispatcher) {
    super(dispatcher);
  }

  @Override
  public void next(TrainDispatcher dispatcher) {
    if (canAdvanceToNextState) {
      DispatcherState newState = new LockRouteState(dispatcher);
      dispatcher.setDispatcherState(newState);
    } else {
      dispatcher.setDispatcherState(this);
    }
  }

  @Override
  public void execute() {
    LocomotiveBean locomotive = dispatcher.getLocomotiveBean();
    Logger.trace("Search a free route for " + locomotive.getName() + "...");

    BlockBean blockBean = PersistenceFactory.getService().getBlockByLocomotiveId(locomotive.getId());

    String departureSuffix = blockBean.getDepartureSuffix();
    LocomotiveBean.Direction locDir = locomotive.getDirection();

    Logger.trace("Loco " + locomotive.getName() + " is in block " + blockBean.getId() + ". Direction " + locDir.getDirection() + ". DepartureSuffix " + departureSuffix + "...");

    //Search for the possible routes
    List<RouteBean> routes = PersistenceFactory.getService().getRoutes(blockBean.getId(), departureSuffix);
    Logger.trace("There " + (routes.size() == 1 ? "is" : "are") + " " + routes.size() + " possible route(s)...");

    if (routes.isEmpty() && locomotive.isCommuter()) {
      //No routes possible. When the Locomotive is a commuter train it can reverse direction, so
      Logger.debug("Reversing Arrival side...");
      blockBean.setReverseArrival(!blockBean.isReverseArrival());

      Direction newDirection = locomotive.toggleDirection();
      //Do NOT set the final direction yet just test....
      locomotive.setDirection(newDirection);
      blockBean.setLocomotive(locomotive);

      locDir = locomotive.getDirection();
      departureSuffix = blockBean.getDepartureSuffix();

      Logger.trace("2nd; Loco " + locomotive.getName() + " is in block " + blockBean.getId() + ". Direction " + locDir.getDirection() + ". DepartureSuffix " + departureSuffix + "...");
      routes = PersistenceFactory.getService().getRoutes(blockBean.getId(), departureSuffix);

      Logger.trace("2nd attempt, there " + (routes.size() == 1 ? "is" : "are") + " " + routes.size() + " possible route(s). " + (!routes.isEmpty() ? "Direction of " + locomotive.getName() + " must be swapped!" : ""));
      if (!routes.isEmpty()) {
        Logger.trace("Locomotive Direction Swap is needed!");
        dispatcher.setSwapLocomotiveDirection(true);
      }
    }

    int rIdx = 0;
    if (routes.size() > 1) {
      //Choose randomly the route
      for (int i = 0; i < 10; i++) {
        //Seed a bit....
        getRandomNumber(0, routes.size());
      }
      rIdx = getRandomNumber(0, routes.size());
    }

    RouteBean route = null;
    if (!routes.isEmpty()) {
      route = routes.get(rIdx);
      Logger.trace("Choosen route " + route.toLogString());
    } else {
      Logger.debug("No route available for " + locomotive.getName() + " ...");
    }
    this.dispatcher.setRouteBean(route);

    canAdvanceToNextState = route != null;
    Logger.trace("Can advance to next state: " + canAdvanceToNextState);
  }

  public int getRandomNumber(int min, int max) {
    Random random = new Random();
    return random.ints(min, max).findFirst().getAsInt();
  }

}
