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
package jcs.controller;

import java.io.Serializable;

/**
 *
 * @author Frans Jacobs
 */
public class ControllerEvent implements Serializable {

    private boolean powerOn;
    private boolean connected;

    public ControllerEvent() {
        this(false, false);
    }

    public ControllerEvent(boolean powerOn, boolean connected) {
        this.powerOn = powerOn;
        this.connected = connected;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.powerOn ? 1 : 0);
        hash = 73 * hash + (this.connected ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ControllerEvent other = (ControllerEvent) obj;
        if (this.powerOn != other.powerOn) {
            return false;
        }
        return this.connected == other.connected;
    }

    @Override
    public String toString() {
        return "{ Power: " + (powerOn ? "On" : "Off") + "; Connected: " + (connected ? "Yes" : "No") + "}";
    }

}
