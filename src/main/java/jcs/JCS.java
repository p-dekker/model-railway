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
package jcs;

import jcs.ui.util.ProcessFactory;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jcs.controller.cs3.events.PowerEvent;
import jcs.controller.cs3.events.PowerEventListener;
import jcs.persistence.PersistenceFactory;
import jcs.persistence.PersistenceService;
import jcs.persistence.util.H2DatabaseUtil;
import jcs.controller.ControllerFactory;
import jcs.ui.JCSFrame;
import jcs.ui.splash.JCSSplash;
import jcs.ui.util.MacOsAdapter;
import jcs.util.RunUtil;
import jcs.util.VersionInfo;
import org.tinylog.Logger;
import jcs.controller.Controller;

/**
 *
 * JCS. This is the run time start point for the application.
 *
 */
public class JCS extends Thread {

  private static JCS instance = null;
  private static JCSSplash splashScreen;
  private static PersistenceService persistentStore;
  private static Controller trackController;

  private static MacOsAdapter osAdapter;
  private static JCSFrame jcsFrame;
  private static VersionInfo versionInfo;

  private JCS() {
    versionInfo = new VersionInfo(JCS.class, "jcs", "ui");
  }

  public static void logProgress(String message) {
    if (splashScreen != null) {
      splashScreen.logProgress(message);
    } else {
      Logger.info(message);
    }
  }

  public static void showTouchbar(JCSFrame frame) {
    if (RunUtil.isMacOSX()) {
      osAdapter.showTouchbar(frame);
    }
  }

  public static VersionInfo getVersionInfo() {
    return versionInfo;
  }

  public static JCSFrame getParentFrame() {
    return jcsFrame;
  }

  private void startGui() {
    JCS.logProgress("Check OS...");

    if (RunUtil.isMacOSX()) {
      MacOsAdapter.setMacOsProperties();
      osAdapter = new MacOsAdapter();
    }

    java.awt.EventQueue.invokeLater(() -> {
      jcsFrame = new JCSFrame();
      if (RunUtil.isMacOSX()) {
        osAdapter.setUiCallback(jcsFrame);
      }

      URL iconUrl = JCS.class.getResource("/media/jcs-train-64.png");
      //URL iconUrl = JCS.class.getResource("/media/jcs-train-2-512.png");
      if (iconUrl != null) {
        jcsFrame.setIconImage(new ImageIcon(iconUrl).getImage());
      }

      jcsFrame.pack();
      jcsFrame.setLocationRelativeTo(null);
      jcsFrame.setVisible(true);

      jcsFrame.toFront();

      jcsFrame.showOverviewPanel();
      if ("true".equalsIgnoreCase(System.getProperty("controller.autoconnect", "true"))) {
        jcsFrame.connect(true);
      }

    });

//    if (!ControllerFactory.getController().isConnected()) {
//      if ("true".equalsIgnoreCase(System.getProperty("controller.autoconnect", "true"))) {
//        jcsFrame.connect(true);
//      }
//    }
    JCS.logProgress("JCS started...");

    int mb = 1024 * 1024;
    Runtime runtime = Runtime.getRuntime();

    StringBuilder sb = new StringBuilder();
    sb.append("Used Memory: ");
    sb.append((runtime.totalMemory() - runtime.freeMemory()) / mb);
    sb.append(" [MB]. Free Memory: ");
    sb.append(runtime.freeMemory() / mb);
    sb.append(" [MB]. Available Memory: ");
    sb.append(runtime.totalMemory() / mb);
    sb.append(" [MB]. Max Memory: ");
    sb.append(runtime.maxMemory() / mb);
    sb.append(" [MB].");

    Logger.info(sb);
    splashScreen.hideSplash(200);
    splashScreen.close();
  }

  /**
   * Executed at shutdown in response to a Ctrl-C etc.
   */
  @Override
  public void run() {
    // Perform shutdown methods.
    Logger.trace("Shutting Down...");
    ProcessFactory.getInstance().shutdown();
    Logger.info("Finished...");
  }

  public static JCS getInstance() {
    if (instance == null) {
      instance = new JCS();
      // Prepare for shutdown...
      Runtime.getRuntime().addShutdownHook(instance);
    }
    return instance;
  }

  public static void main(String[] args) {
    if (GraphicsEnvironment.isHeadless()) {
      Logger.error("This JDK environment is headless, can't start a GUI!");
      //Quit....
      System.exit(1);
    }
    //Load properties
    RunUtil.loadProperties();
    RunUtil.loadExternalProperties();

    try {
      String plaf = System.getProperty("jcs.plaf", "com.formdev.flatlaf.FlatLightLaf");
      if (plaf != null) {
        UIManager.setLookAndFeel(plaf);
      } else {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.error(ex);
    }

    splashScreen = new JCSSplash();
    splashScreen.showSplash();
    splashScreen.setProgressMax(25);

    logProgress("JCS is Starting...");

    //Check the persistent properties prepare environment
    if (H2DatabaseUtil.databaseFileExists(false)) {
      //Database files are there so try to create connection
      logProgress("Connecting to existing Database...");

    } else {
      //No Database file so maybe first start lets creat one
      logProgress("Create new Database...");
      H2DatabaseUtil.createDatabaseUsers(false);
      H2DatabaseUtil.createDatabase();
    }

    persistentStore = PersistenceFactory.getService();
    if (persistentStore != null) {
      logProgress("Aquire Track Controller...");
      trackController = ControllerFactory.getController();
      if ("true".equalsIgnoreCase(System.getProperty("controller.autoconnect"))) {
        if (trackController != null) {
          boolean connected = trackController.connect();
          if (connected) {
            logProgress("Connected with Track Controller...");

            boolean power = trackController.isPowerOn();
            logProgress("Track Power is " + (power ? "on" : "off"));
            Logger.info("Track Power is " + (power ? "on" : "off"));
            trackController.addPowerEventListener(new JCS.Powerlistener());
          } else {
            logProgress("Could NOT connect with Track Controller...");
          }
        }
      }

      logProgress("Starting UI...");

      JCS jcs = JCS.getInstance();
      jcs.startGui();
    } else {
      Logger.error("Could not obtain a Persistent store. Quitting....");
      logProgress("Error! Can't Obtain a Persistent store!");
      splashScreen.hideSplash(500);
      splashScreen.close();
      System.exit(0);
    }
  }

  private static class Powerlistener implements PowerEventListener {

    Powerlistener() {
    }

    @Override
    public void onPowerChange(PowerEvent event) {
      Logger.info("Track Power is " + (event.isPower() ? "on" : "off"));

      if (JCS.jcsFrame != null) {
        JCS.jcsFrame.powerChanged(event);
      }
    }
  }

}
