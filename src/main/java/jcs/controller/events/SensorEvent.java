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
package jcs.controller.events;

import java.util.Date;
import jcs.controller.cs.can.CanMessage;
import jcs.entities.SensorBean;
import jcs.util.ByteUtil;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs
 */
public class SensorEvent {

  private SensorBean sensor;
  private final CanMessage message;

  public SensorEvent(CanMessage message, Date eventDate) {
    this.message = message;
    parseMessage(message, eventDate);
  }

  private void parseMessage(CanMessage message, Date eventDate) {
    CanMessage resp;
    if (!message.isResponseMessage()) {
      resp = message.getResponse();
    } else {
      resp = message;
    }

    if (resp.isResponseMessage() && CanMessage.S88_EVENT_RESPONSE == resp.getCommand()) {
      byte[] data = resp.getData();

      Integer deviceId = ByteUtil.toInt(new byte[]{data[0], data[1]});
      Integer contactId = ByteUtil.toInt(new byte[]{data[2], data[3]});

      int previousStatus = data[4];
      int status = data[5];

      Integer millis = ByteUtil.toInt(new byte[]{data[6], data[7]}) * 10;

      this.sensor = new SensorBean(deviceId, contactId, status, previousStatus, millis, eventDate);
    } else {
      Logger.warn("Can't parse message, not a Sensor Response! " + resp);
    }
  }

  public SensorBean getSensorBean() {
    return sensor;
  }

  public CanMessage getMessage() {
    return this.message;
  }

}
