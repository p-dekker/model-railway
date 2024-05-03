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
import jcs.commandStation.events.SensorEvent;
import jcs.commandStation.events.SensorEventListener;
import jcs.entities.BlockBean;
import jcs.entities.LocomotiveBean;
import jcs.entities.RouteBean;
import jcs.entities.SensorBean;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class RunState extends DispatcherState {

  //private SensorListener enterListener;
  //private SensorListener arrivalListener;
  //private BlockBean departureBlock;
  //private BlockBean destinationBlock;
  boolean entered = false;
  boolean arrived = false;

  public RunState(TrainDispatcher dispatcher) {
    super(dispatcher);
  }

  @Override
  public void next(TrainDispatcher locRunner) {
    Logger.trace("canAdvanceState: " + canAdvanceToNextState);
    if (canAdvanceToNextState) {
      DispatcherState newState = new ArrivalState(this.dispatcher);
      newState.setRunning(running);
      locRunner.setDispatcherState(newState);
    } else {
      locRunner.setDispatcherState(this);
    }
  }

  @Override
  public void execute() {
    //Which sensors do we need to watch
    RouteBean route = this.dispatcher.getRouteBean();
    String departureTileId = route.getFromTileId();
    String destinationTileId = route.getToTileId();
    //From which side on the block is the train expected to arrive?
    String arrivalSuffix = route.getToSuffix();

    Logger.trace("Destination tile: " + destinationTileId + " Arrival on the " + arrivalSuffix + " side of the block");
    BlockBean departureBlock = PersistenceFactory.getService().getBlockByTileId(departureTileId);
    BlockBean destinationBlock = PersistenceFactory.getService().getBlockByTileId(destinationTileId);

    SensorBean enterSensor;
    if ("+".equals(arrivalSuffix)) {
      enterSensor = destinationBlock.getPlusSensorBean();
    } else {
      enterSensor = destinationBlock.getMinSensorBean();
    }

    SensorListener enterListener = new SensorListener(enterSensor, true, this.dispatcher);

    SensorBean inSensor;
    if ("-".equals(arrivalSuffix)) {
      inSensor = destinationBlock.getPlusSensorBean();
    } else {
      inSensor = destinationBlock.getMinSensorBean();
    }

    SensorListener arrivalListener = new SensorListener(inSensor, false, this.dispatcher);

    Logger.trace("Register the sensor listeners at the commandstation ");
    JCS.getJcsCommandStation().addSensorEventListener(enterListener);
    JCS.getJcsCommandStation().addSensorEventListener(arrivalListener);

    dispatcher.setEnterEventListener(enterListener);
    dispatcher.setInEventListener(arrivalListener);

    Logger.debug("Enter Sensor: " + enterSensor.getName() + " [" + enterSensor.getId() + "] In Sensor: " + inSensor.getName() + " [" + inSensor.getId() + "]");

    LocomotiveBean locomotive = dispatcher.getLocomotiveBean();
    JCS.getJcsCommandStation().changeLocomotiveSpeed(750, locomotive);

    //Loc has started advance to the next state
    canAdvanceToNextState = true;
    Logger.trace("Can advance to next state: " + canAdvanceToNextState);
  }

  private class SensorListener implements SensorEventListener {

    private final Integer deviceId;
    private final Integer contactId;
    private final boolean enter;
    private final TrainDispatcher dispatcher;

    SensorListener(SensorBean sensor, boolean enter, TrainDispatcher dispatcher) {
      this.deviceId = sensor.getDeviceId();
      this.contactId = sensor.getContactId();
      this.enter = enter;
      this.dispatcher = dispatcher;
      Logger.trace("deviceId: " + deviceId + " contactId: " + contactId + " enter: " + enter);
    }

    @Override
    public void onSensorChange(SensorEvent event) {
      SensorBean sensor = event.getSensorBean();

      if (deviceId.equals(sensor.getDeviceId()) && contactId.equals(sensor.getContactId())) {
        Logger.trace("-> deviceId: " + deviceId + " contactId: " + contactId + " enter: " + enter);
        if (sensor.isActive()) {
          if (enter) {
            dispatcher.onEnter();
          } else {
            dispatcher.onArrival();
          }
        }
      }
    }
  }

}
