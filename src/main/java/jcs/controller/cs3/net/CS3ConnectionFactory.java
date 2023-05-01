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
package jcs.controller.cs3.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import jcs.JCS;
import jcs.controller.cs3.can.CanMessage;
import jcs.controller.cs3.can.CanMessageFactory;
import jcs.controller.cs3.can.MarklinCan;
import jcs.util.NetworkUtil;
import jcs.util.RunUtil;
import org.tinylog.Logger;

/**
 * Try to connect with a CS 3. A "ping" is send to the broadcast address like the mobile app does. The CS3 response reveals the IP
 * address.
 *
 * @author Frans Jacobs
 */
public class CS3ConnectionFactory {
  
  private static CS3ConnectionFactory instance;
  
  private CS3Connection controllerConnection;
  private HTTPConnection httpConnection;
  private InetAddress controllerHost;
  
  private static final String BROADCAST_ADDRESS = "255.255.255.255";
  
  private static final String LAST_USED_IP_PROP_FILE = RunUtil.DEFAULT_PATH + "last-used-marklin-cs-ip.properties";
  
  private CS3ConnectionFactory() {
  }
  
  public static CS3ConnectionFactory getInstance() {
    if (instance == null) {
      instance = new CS3ConnectionFactory();
    }
    return instance;
  }
  
  private static boolean isLinux() {
    String os = System.getProperty("os.name").toLowerCase();
    return os.contains("nix") || os.contains("nux");
  }
  
  CS3Connection getConnectionImpl() {
    if (controllerConnection == null) {
      
      String lastUsedIp = RunUtil.readProperty(LAST_USED_IP_PROP_FILE, "cs-ip-address");
      
      if (lastUsedIp != null) {
        try {
          this.controllerHost = InetAddress.getByName(lastUsedIp);
          Logger.trace("Using last used CS IP Address: " + lastUsedIp);
        } catch (UnknownHostException ex) {
          Logger.trace("Last used CS IP: " + lastUsedIp + " is invalid!");
          lastUsedIp = null;
        }
      }
      
      if (this.controllerHost == null) {
        Logger.trace("Try to discover a Marklin CS3...");
        JCS.logProgress("Discovering a Marklin Central Station...");
        sendMobileAppPing();
      }
      
      if (controllerHost != null) {
        if(lastUsedIp == null) {
          //Write the last used IP Addres for faster discovery next time
          RunUtil.writeProperty(LAST_USED_IP_PROP_FILE, "cs-ip-address", controllerHost.getHostAddress());
        }
        
        
        Logger.trace("CS3 ip: " + controllerHost.getHostName());
        controllerConnection = new TCPConnection(controllerHost);
      } else {
        Logger.warn("Can't find a Marklin Controller host!");
        JCS.logProgress("Can't find a Marklin Central Station on the Network");
      }
    }
    return this.controllerConnection;
  }
  
  public static CS3Connection getConnection() {
    return getInstance().getConnectionImpl();
  }
  
  public static void disconnectAll() {
    try {
      instance.controllerConnection.close();
    } catch (Exception ex) {
      Logger.trace("Error during disconnect " + ex);
    }
    instance.controllerConnection = null;
    instance.httpConnection = null;
  }
  
  HTTPConnection getHTTPConnectionImpl() {
    if (controllerConnection == null) {
      getConnectionImpl();
    }
    if (httpConnection == null) {
      httpConnection = new HTTPConnection(controllerHost);
    }
    return this.httpConnection;
  }
  
  public static HTTPConnection getHTTPConnection() {
    return getInstance().getHTTPConnectionImpl();
  }
  
  void sendMobileAppPing() {
    try {
      InetAddress localAddress;
      if (isLinux()) {
        localAddress = NetworkUtil.getIPv4HostAddress();
      } else {
        localAddress = InetAddress.getByName("0.0.0.0");
      }
      
      InetAddress broadcastAddress = InetAddress.getByName(BROADCAST_ADDRESS);
      
      CanMessage ping = CanMessageFactory.getMobileAppPingRequest();
      
      try (DatagramSocket requestSocket = new DatagramSocket()) {
        Logger.trace("Sending: " + ping);
        DatagramPacket requestPacket = new DatagramPacket(ping.getBytes(), ping.getLength(), broadcastAddress, CS3Connection.CS3_RX_PORT);
        requestSocket.send(requestPacket);
      }
      
      try (DatagramSocket responseSocket = new DatagramSocket(CS3Connection.CS3_TX_PORT, localAddress)) {
        responseSocket.setSoTimeout(3000);
        
        DatagramPacket responsePacket = new DatagramPacket(new byte[CanMessage.MESSAGE_SIZE], CanMessage.MESSAGE_SIZE, localAddress, CS3Connection.CS3_TX_PORT);
        responseSocket.receive(responsePacket);
        
        InetAddress replyHost = InetAddress.getByName(responsePacket.getAddress().getHostAddress());
        CanMessage response = new CanMessage(responsePacket.getData());
        
        Logger.trace("Received: " + response + " from: " + replyHost.getHostAddress());
        
        if (response.getCommand() == MarklinCan.SW_STATUS_REQ) {
          if (this.controllerHost == null) {
            this.controllerHost = replyHost;
          }
          JCS.logProgress("Found a Central Station in the network with IP: " + replyHost.getHostAddress());
        } else {
          Logger.debug("Received wrong command: " + response.getCommand() + " != " + MarklinCan.SW_STATUS_REQ + "...");
        }
      }
    } catch (SocketTimeoutException ste) {
      Logger.trace("No reply. " + ste.getMessage());
    } catch (IOException ex) {
      Logger.error(ex);
    }
  }
  
  String getControllerIpImpl() {
    if (this.controllerHost != null) {
      return this.controllerHost.getHostAddress();
    } else {
      return "Unknown";
    }
  }
  
  public static String getControllerIp() {
    return getInstance().getControllerIpImpl();
  }
  
}
