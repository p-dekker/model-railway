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
package jcs.commandStation.dccex;

interface DccEx {

  public static final String DCC_EX_COMMANDSTATION_ID = "dcc-ex";
  
  public static final String REQ_TRACK_CURRENT = "<c>";

  public static final String REQ_VERSION_HARDWARE_TURNOUTS = "<s>";

  public static final String SYSTEM_OPCODES = "cs#=01JipP";

}
