/*
 * Copyright (C) 2020 Frans Jacobs.
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
package lan.wervel.jcs.controller.cs2.can;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CS 2 CAN message.
 */
public class CanMessage {

  public static final int MESSAGE_SIZE = 13;

  public static final int HASH_SIZE = 2;
  public static final int DATA_SIZE = 8;

  private static final int PRIO_IDX = 0;
  private static final int CMD_IDX = 1;
  private static final int HASH_IDX = 2;
  private static final int DLC_IDX = 4;
  private static final int DATA_IDX = 5;

  private static final int JCS_UID = 0x12345678;

  private final int cs2UID;

  private final int[] message;

  private List<int[]> responses;

  public CanMessage() {
    this.message = getEmptyMessage();
    cs2UID = -1;
  }

  public CanMessage(byte[] message) {
    this.message = getEmptyMessage();
    cs2UID = -1;
    for (int i = 0; i < this.message.length; i++) {
      this.message[i] = message[i] & 0xFF;
    }
  }

  public CanMessage(int[] message) {
    this.message = getEmptyMessage();
    cs2UID = -1;
    System.arraycopy(message, 0, this.message, 0, message.length);
  }

  public CanMessage(int priority, int command, int dlc, int[] data, int cs2UID) {
    this(priority, command, null, dlc, data, cs2UID);
  }

  public CanMessage(int priority, int command, int[] hash, int dlc, int[] data, int cs2UID) {
    this.message = getEmptyMessage();
    this.cs2UID = cs2UID;
    this.setPriority(priority);
    this.setCommand(command);
    this.setDlc(dlc);
    this.setData(data);

    if (hash == null) {
      this.setHash(this.generateHash());
    } else {
      this.setHash(hash);
    }
  }

  public int getLength() {
    return MESSAGE_SIZE;
  }

  public int[] getMessage() {
    return this.message;
  }

  public void addResponse(CanMessage reply) {
    if (this.responses == null) {
      this.responses = new ArrayList<>();
    }
    if (reply != null) {
      this.responses.add(reply.getMessage());
    }
  }

  public void addResponse(byte[] response) {
    if (this.responses == null) {
      this.responses = new ArrayList<>();
    }

    int[] r = new int[response.length];
    for (int i = 0; i < response.length; i++) {
      r[i] = response[i] & 0xff;
    }
    this.responses.add(r);
  }

  public void addResponse(int[] response) {
    if (this.responses == null) {
      this.responses = new ArrayList<>();
    }
    this.responses.add(response);
  }

  public List<int[]> getResponses() {
    return this.responses;
  }

  public CanMessage getResponse(int idx) {
    return new CanMessage(this.responses.get(idx));
  }

  public CanMessage getResponse() {
    return new CanMessage(this.responses.get(0));
  }

  public byte[] getBytes() {
    byte[] bytes = new byte[MESSAGE_SIZE];
    for (int i = 0; i < bytes.length; i++) {
      byte b = (byte) (this.message[i] & 0xFF);
      bytes[i] = b;
    }
    return bytes;
  }

  public void setMessage(int[] message) {
    System.arraycopy(message, 0, this.message, 0, message.length);
  }

  public final void setPriority(int priority) {
    this.message[PRIO_IDX] = priority;
  }

  public int getPriority() {
    return this.message[PRIO_IDX];
  }

  public final void setCommand(int command) {
    this.message[CMD_IDX] = command;
  }

  public int getCommand() {
    return this.message[CMD_IDX];
  }

  public int[] getHash() {
    int[] hash = new int[HASH_SIZE];
    System.arraycopy(message, HASH_IDX, hash, 0, HASH_SIZE);
    return hash;
  }

  public final void setHash(int[] hash) {
    System.arraycopy(hash, 0, this.message, HASH_IDX, hash.length);
  }

  public int getDlc() {
    return this.message[DLC_IDX];
  }

  public final void setDlc(int dlc) {
    this.message[DLC_IDX] = dlc;
  }

  public int[] getData() {
    int[] data = new int[DATA_SIZE];
    System.arraycopy(message, DATA_IDX, data, 0, DATA_SIZE);
    return data;
  }

  public byte[] getDataBytes() {
    int[] data = this.getData();
    byte[] db = new byte[data.length];
    for (int i = 0; i < data.length; i++) {
      byte b = (byte) (data[i] & 0xFF);
      db[i] = b;
    }
    return db;
  }

  public final void setData(int[] data) {
    System.arraycopy(data, 0, this.message, DATA_IDX, data.length);
  }

  public boolean isResponseMessage() {
    int command = getCommand();
    command = command & 0x01;
    return command == 1;
  }

  public boolean hasValidResponse() {
    if (this.responses == null || this.responses.isEmpty()) {
      return false;
    }
    //get the last response
    int last = this.responses.size() - 1;
    int[] response = this.responses.get(last);
    if (response.length != MESSAGE_SIZE) {
      return false;
    }
    //compare the messsage
    for (int i = 0; i < message.length; i++) {
      if (i == CMD_IDX) {
        //check the response bit
        int cmd = response[i] & 0x01;
        if (cmd != 1) {
          return false;
        } else {
          cmd = response[i] & 0xFE;
          if (cmd != message[i]) {
            return false;
          }
        }
      } else {
        if (response[i] != message[i]) {
          return false;
        }
      }
    }
    return true;
  }

  public int getUidInt() {
    int[] data = getData();

    return ((data[0] & 0xFF) << 24)
            | ((data[1] & 0xFF) << 16)
            | ((data[2] & 0xFF) << 8)
            | (data[3] & 0xFF);
  }

  public int[] getUid() {
    int[] uid = new int[4]; //CS2 UID is 4 bytes long
    System.arraycopy(this.message, DATA_IDX, uid, 0, uid.length);
    return uid;
  }

  public String responseString() {
    return toString(this.responses.get(0));
  }

  @Override
  public String toString() {
    return toString(this.message);
  }

  String toString(int[] bytes) {
    if (bytes == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < bytes.length; i++) {
      sb.append(toHexString(bytes[i]));
      if (i + 1 < bytes.length) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }

  public int getNumberOfMeasurementValues() {
    if (this.isResponseMessage()) {
      int command = this.getCommand();
      if ((command & 0xFe) == MarklinCan.STATUS_CONFIG) {
        //get the 1st data byte
        return this.getData()[0];
      }
    }
    return -1;
  }

  public final int generateHashInt() {
    int uid;
    if (this.cs2UID > 0) {
      uid = this.cs2UID;
    } else {
      uid = this.getUidInt();
    }

    int msb = uid >> 16;
    int lsb = uid & 0xffff;
    int hash = msb ^ lsb;
    hash = (((hash << 3) & 0xFF00) | 0x0300) | (hash & 0x7F);

    //int ch = (uid >> 16) ^ (uid & 0xFFFF);
    //int hash = ((ch << 3) | 0x0300) & 0xFF00;
    return hash;
  }

  public final int[] generateHash() {
    int gh = generateHashInt();
    int[] hash = to2ByteArray(gh);
    return hash;
  }

  public final int getHashInt() {
    int[] h = this.getHash();

    return ((h[0] & 0xFF) << 8)
            | ((h[1] & 0xFF));
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Arrays.hashCode(this.message);
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
    final CanMessage other = (CanMessage) obj;
    return Arrays.equals(this.message, other.message);
  }

  public static int[] to2ByteArray(int value) {
    int[] bts = new int[]{
      (value >> 8) & 0xFF,
      value & 0XFF};

    return bts;
  }

  public static int toInt(int[] value) {
    int val;
    switch (value.length) {
      case 2:
        val = ((value[0] & 0xFF) << 8) | (value[1] & 0xFF);
        break;
      case 4:
        val = ((value[0] & 0xFF) << 24)
                | ((value[1] & 0xFF) << 16)
                | ((value[2] & 0xFF) << 8)
                | (value[3] & 0xFF);
        break;
      default:
        val = 0;
        break;
    }
    return val;
  }

  public static int[] to4ByteArray(int value) {
    int[] bts = new int[]{
      (value >> 24) & 0xFF,
      (value >> 16) & 0xFF,
      (value >> 8) & 0xFF,
      value & 0XFF};

    return bts;
  }

  private static String toHexString(int b) {
    String h = Integer.toHexString((b & 0xff));
    if (h.length() == 1) {
      h = "0" + h;
    }
    return h;
  }

  private static int[] getEmptyMessage() {
    int[] msg = new int[MESSAGE_SIZE];
    //Enshure it is filled with 0x00
    for (int i = 0; i < msg.length; i++) {
      msg[i] = 0;
    }
    return msg;
  }

}
