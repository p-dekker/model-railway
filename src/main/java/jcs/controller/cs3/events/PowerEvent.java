/*
 * Copyright (C) 2022 frans.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package jcs.controller.cs3.events;

import jcs.controller.cs3.can.CanMessage;
import static jcs.controller.cs3.can.MarklinCan.SYSTEM_COMMAND_RESPONSE;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class PowerEvent {

    private boolean power;

    public PowerEvent(CanMessage message) {
        parseMessage(message);
    }

    private void parseMessage(CanMessage message) {
        CanMessage resp;
        if (!message.isResponseMessage()) {
            resp = message.getResponse();
        } else {
            resp = message;
        }

        int cmd = message.getCommand();
        int subCmd = message.getSubCommand();

        if (resp.isResponseMessage() && SYSTEM_COMMAND_RESPONSE == cmd && (subCmd == 0 | subCmd == 1)) {

            this.power = subCmd == 1;
        } else {
            Logger.warn("Can't parse message, not a System Go or Stop Response! " + resp);
        }
    }

    public boolean isPower() {
        return power;
    }

}
