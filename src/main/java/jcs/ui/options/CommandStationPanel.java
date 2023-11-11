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
package jcs.ui.options;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jcs.commandStation.CommandStation;
import jcs.entities.CommandStationBean;
import jcs.entities.CommandStationBean.ConnectionType;
import jcs.persistence.PersistenceFactory;
import jcs.util.Ping;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class CommandStationPanel extends JPanel implements PropertyChangeListener {

  private CommandStationBean selectedCommandStation;
  private ComboBoxModel<CommandStationBean> commandStationComboBoxModel;
  private ComboBoxModel<ConnectionType> connectionTypeComboBoxModel;
  private ComboBoxModel<SerialPort> serialPortComboBoxModel;

  private Task task;

  public CommandStationPanel() {
    initComponents();
    if (PersistenceFactory.getService() != null) {

      initModels();
    }
  }

  private void initModels() {
    selectedCommandStation = PersistenceFactory.getService().getDefaultCommandStation();
    List<CommandStationBean> commandStations = PersistenceFactory.getService().getCommandStations();

    if (selectedCommandStation == null) {
      selectedCommandStation = new CommandStationBean();
      //Add the empty Commandstation
      commandStations.add(selectedCommandStation);
    }

    commandStationComboBoxModel = new DefaultComboBoxModel(commandStations.toArray());
    commandStationComboBoxModel.setSelectedItem(selectedCommandStation);
    commandStationComboBox.setModel(commandStationComboBoxModel);

    connectionTypeComboBoxModel = new DefaultComboBoxModel(ConnectionType.toArray());
    this.connectionTypeCB.setModel(connectionTypeComboBoxModel);

    if (selectedCommandStation.getId() != null) {
      defaultCommandStationChkBox.setSelected(true);
    }

    SerialPort comPorts[] = SerialPort.getCommPorts();

    serialPortComboBoxModel = new DefaultComboBoxModel(comPorts);
    this.serialPortCB.setModel(serialPortComboBoxModel);

    setFieldValues();

    showApplicableFields();

    this.progressBar.setVisible(false);

  }

  private void setFieldValues() {
    if (selectedCommandStation != null && selectedCommandStation.getConnectionType() != null) {
      connectionTypeComboBoxModel.setSelectedItem(selectedCommandStation.getConnectionType());
      this.autoConfChkBox.setSelected(this.selectedCommandStation.isAutoIpConfiguration());
      this.ipAddressTF.setText(this.selectedCommandStation.getIpAddress());
      if (this.selectedCommandStation.getNetworkPort() != null) {
        this.portSpinner.setValue(this.selectedCommandStation.getNetworkPort());
      } else {
        this.portSpinner.setValue(0);
      }
    } else {
      this.autoConfChkBox.setSelected(false);
      this.ipAddressTF.setText("0.0.0.0");
      this.portSpinner.setValue(0);

    }
  }

  private void showApplicableFields() {
    if (selectedCommandStation != null && selectedCommandStation.getConnectionType() != null) {
      if (null == selectedCommandStation.getConnectionType()) {
        this.serialPortCB.setVisible(false);
        this.ipAddressTF.setVisible(false);
        this.connectionPropertiesLbl.setVisible(false);
        this.portLbl.setVisible(false);
        this.portSpinner.setVisible(false);
      } else {
        switch (selectedCommandStation.getConnectionType()) {
          case NETWORK -> {
            this.serialPortCB.setVisible(false);
            this.ipAddressTF.setVisible(true);
            this.connectionPropertiesLbl.setText("IP Address:");
            this.connectionPropertiesLbl.setVisible(true);
            this.portLbl.setVisible(true);
            this.portSpinner.setVisible(true);
            this.autoConfChkBox.setVisible(true);
          }
          case SERIAL -> {
            this.serialPortCB.setVisible(true);
            this.connectionPropertiesLbl.setText("Serial Port:");
            this.connectionPropertiesLbl.setVisible(true);
            this.ipAddressTF.setVisible(false);
            this.portLbl.setVisible(false);
            this.portSpinner.setVisible(false);
            this.autoConfChkBox.setVisible(false);
          }
          default -> {
            this.serialPortCB.setVisible(false);
            this.ipAddressTF.setVisible(false);
            this.connectionPropertiesLbl.setVisible(false);
            this.portLbl.setVisible(false);
            this.portSpinner.setVisible(false);
            this.autoConfChkBox.setVisible(false);
          }
        }
      }
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("deprecation")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    topPanel = new JPanel();
    commandStationSelectionPanel = new JPanel();
    commandStationLbl = new JLabel();
    commandStationComboBox = new JComboBox<>();
    defaultCommandStationChkBox = new JCheckBox();
    testConnectionPanel = new JPanel();
    connectionTestResultLbl = new JLabel();
    testConnectionBtn = new JButton();
    filler3 = new Box.Filler(new Dimension(26, 0), new Dimension(26, 0), new Dimension(20, 32767));
    progressBar = new JProgressBar();
    centerPanel = new JPanel();
    filler2 = new Box.Filler(new Dimension(40, 0), new Dimension(40, 0), new Dimension(40, 32767));
    connectionTypePanel = new JPanel();
    connectionTypeLbl = new JLabel();
    connectionTypeCB = new JComboBox<>();
    connectionPropertiesLbl = new JLabel();
    serialPortCB = new JComboBox<>();
    ipAddressTF = new JTextField();
    portLbl = new JLabel();
    portSpinner = new JSpinner();
    autoConfChkBox = new JCheckBox();
    bottomPanel = new JPanel();
    filler1 = new Box.Filler(new Dimension(50, 0), new Dimension(200, 0), new Dimension(150, 32767));
    saveBtn = new JButton();

    setMinimumSize(new Dimension(1000, 600));
    setPreferredSize(new Dimension(1000, 600));
    setLayout(new BorderLayout());

    topPanel.setMinimumSize(new Dimension(1000, 50));
    topPanel.setPreferredSize(new Dimension(1000, 50));
    topPanel.setRequestFocusEnabled(false);
    topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    commandStationSelectionPanel.setMinimumSize(new Dimension(400, 33));
    commandStationSelectionPanel.setPreferredSize(new Dimension(425, 33));
    FlowLayout flowLayout2 = new FlowLayout(FlowLayout.LEFT);
    flowLayout2.setAlignOnBaseline(true);
    commandStationSelectionPanel.setLayout(flowLayout2);

    commandStationLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    commandStationLbl.setLabelFor(commandStationComboBox);
    commandStationLbl.setText("Command Station:");
    commandStationLbl.setMaximumSize(new Dimension(110, 17));
    commandStationLbl.setMinimumSize(new Dimension(110, 17));
    commandStationLbl.setPreferredSize(new Dimension(110, 17));
    commandStationSelectionPanel.add(commandStationLbl);

    commandStationComboBox.setPreferredSize(new Dimension(200, 23));
    commandStationComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        commandStationComboBoxActionPerformed(evt);
      }
    });
    commandStationSelectionPanel.add(commandStationComboBox);

    defaultCommandStationChkBox.setText("Set default");
    defaultCommandStationChkBox.setHorizontalTextPosition(SwingConstants.LEADING);
    defaultCommandStationChkBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        defaultCommandStationChkBoxActionPerformed(evt);
      }
    });
    commandStationSelectionPanel.add(defaultCommandStationChkBox);

    topPanel.add(commandStationSelectionPanel);

    testConnectionPanel.setPreferredSize(new Dimension(550, 33));
    FlowLayout flowLayout4 = new FlowLayout(FlowLayout.LEFT);
    flowLayout4.setAlignOnBaseline(true);
    testConnectionPanel.setLayout(flowLayout4);

    connectionTestResultLbl.setText("Not Connected");
    connectionTestResultLbl.setPreferredSize(new Dimension(222, 17));
    testConnectionPanel.add(connectionTestResultLbl);
    connectionTestResultLbl.getAccessibleContext().setAccessibleName("");

    testConnectionBtn.setIcon(new ImageIcon(getClass().getResource("/media/connect-24.png"))); // NOI18N
    testConnectionBtn.setText("Test");
    testConnectionBtn.setToolTipText("Test Connection");
    testConnectionBtn.setFocusable(false);
    testConnectionBtn.setMargin(new Insets(2, 2, 2, 2));
    testConnectionBtn.setMaximumSize(new Dimension(60, 40));
    testConnectionBtn.setMinimumSize(new Dimension(60, 40));
    testConnectionBtn.setPreferredSize(new Dimension(60, 40));
    testConnectionBtn.setVerticalAlignment(SwingConstants.TOP);
    testConnectionBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        testConnectionBtnActionPerformed(evt);
      }
    });
    testConnectionPanel.add(testConnectionBtn);
    testConnectionPanel.add(filler3);

    progressBar.setPreferredSize(new Dimension(200, 4));
    testConnectionPanel.add(progressBar);

    topPanel.add(testConnectionPanel);

    add(topPanel, BorderLayout.NORTH);

    centerPanel.setMinimumSize(new Dimension(1000, 540));
    centerPanel.setPreferredSize(new Dimension(1000, 500));
    centerPanel.setLayout(new BorderLayout());
    centerPanel.add(filler2, BorderLayout.CENTER);

    FlowLayout flowLayout3 = new FlowLayout(FlowLayout.LEFT);
    flowLayout3.setAlignOnBaseline(true);
    connectionTypePanel.setLayout(flowLayout3);

    connectionTypeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    connectionTypeLbl.setText("Connection Type");
    connectionTypeLbl.setPreferredSize(new Dimension(115, 17));
    connectionTypePanel.add(connectionTypeLbl);

    connectionTypeCB.setEnabled(false);
    connectionTypeCB.setPreferredSize(new Dimension(200, 23));
    connectionTypeCB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        connectionTypeCBActionPerformed(evt);
      }
    });
    connectionTypePanel.add(connectionTypeCB);

    connectionPropertiesLbl.setText("Properties:");
    connectionPropertiesLbl.setPreferredSize(new Dimension(100, 17));
    connectionTypePanel.add(connectionPropertiesLbl);

    serialPortCB.setPreferredSize(new Dimension(150, 23));
    connectionTypePanel.add(serialPortCB);

    ipAddressTF.setText("0.0.0.0");
    ipAddressTF.setToolTipText("");
    ipAddressTF.setDoubleBuffered(true);
    ipAddressTF.setPreferredSize(new Dimension(150, 23));
    ipAddressTF.addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent evt) {
        ipAddressTFFocusLost(evt);
      }
    });
    connectionTypePanel.add(ipAddressTF);

    portLbl.setText("Port:");
    connectionTypePanel.add(portLbl);

    portSpinner.setModel(new SpinnerNumberModel(0, 0, 65563, 1));
    portSpinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        portSpinnerStateChanged(evt);
      }
    });
    connectionTypePanel.add(portSpinner);

    autoConfChkBox.setText("Auto IP Configuration");
    connectionTypePanel.add(autoConfChkBox);

    centerPanel.add(connectionTypePanel, BorderLayout.NORTH);

    add(centerPanel, BorderLayout.CENTER);

    bottomPanel.setPreferredSize(new Dimension(1014, 50));
    bottomPanel.setRequestFocusEnabled(false);
    FlowLayout flowLayout12 = new FlowLayout(FlowLayout.RIGHT);
    flowLayout12.setAlignOnBaseline(true);
    bottomPanel.setLayout(flowLayout12);
    bottomPanel.add(filler1);

    saveBtn.setIcon(new ImageIcon(getClass().getResource("/media/save-24.png"))); // NOI18N
    saveBtn.setToolTipText("Save and Exit");
    saveBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        saveBtnActionPerformed(evt);
      }
    });
    bottomPanel.add(saveBtn);

    add(bottomPanel, BorderLayout.SOUTH);
  }// </editor-fold>//GEN-END:initComponents


  private void testConnectionBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_testConnectionBtnActionPerformed
    Logger.trace(evt.getActionCommand());
    this.progressBar.setVisible(true);

    progressBar.setIndeterminate(true);

    task = new Task();
    task.addPropertyChangeListener(this);
    task.execute();
  }//GEN-LAST:event_testConnectionBtnActionPerformed

  private void commandStationComboBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_commandStationComboBoxActionPerformed
    selectedCommandStation = (CommandStationBean) commandStationComboBoxModel.getSelectedItem();
    defaultCommandStationChkBox.setSelected(selectedCommandStation.isDefault());
    setFieldValues();
    showApplicableFields();

    Logger.trace("Selected CS: " + this.selectedCommandStation.getName());
  }//GEN-LAST:event_commandStationComboBoxActionPerformed

  private void defaultCommandStationChkBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_defaultCommandStationChkBoxActionPerformed
    Logger.trace("Setting " + this.selectedCommandStation + " as default");
    PersistenceFactory.getService().changeDefaultCommandStation(selectedCommandStation);
  }//GEN-LAST:event_defaultCommandStationChkBoxActionPerformed

  private void connectionTypeCBActionPerformed(ActionEvent evt) {//GEN-FIRST:event_connectionTypeCBActionPerformed
    Logger.trace(evt.getActionCommand());
  }//GEN-LAST:event_connectionTypeCBActionPerformed

  private void saveBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
    Logger.trace(evt.getActionCommand());
  }//GEN-LAST:event_saveBtnActionPerformed

  private void ipAddressTFFocusLost(FocusEvent evt) {//GEN-FIRST:event_ipAddressTFFocusLost
    Logger.trace("IP address:" + ipAddressTF.getText());
    this.selectedCommandStation.setIpAddress(this.ipAddressTF.getText());
    PersistenceFactory.getService().persist(this.selectedCommandStation);
  }//GEN-LAST:event_ipAddressTFFocusLost

  private void portSpinnerStateChanged(ChangeEvent evt) {//GEN-FIRST:event_portSpinnerStateChanged
    Logger.trace("Port: " + this.portSpinner.getValue());
    this.selectedCommandStation.setNetworkPort((Integer) this.portSpinner.getValue());
    PersistenceFactory.getService().persist(this.selectedCommandStation);
  }//GEN-LAST:event_portSpinnerStateChanged

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if ("progress".equals(evt.getPropertyName())) {
      int progress = (Integer) evt.getNewValue();
      progressBar.setValue(progress);
      progressBar.setIndeterminate(progress > 20);

      if (task.isDone()) {
        testConnectionBtn.setEnabled(true);
      }
    }

    if ("done".equals(evt.getPropertyName())) {
      Logger.trace("Done: " + evt.getNewValue());

      this.connectionTestResultLbl.setText((String) evt.getNewValue());
      this.progressBar.setVisible(false);
    }
  }

  class Task extends SwingWorker<Void, Void> {

    @Override
    public Void doInBackground() {
      setProgress(0);

      if (ConnectionType.NETWORK == selectedCommandStation.getConnectionType()) {
        String ip = selectedCommandStation.getIpAddress();

        if (Ping.IsReachable(ip)) {
          setProgress(10);
          CommandStation commandStation = createCommandStation(selectedCommandStation);
          setProgress(30);
          boolean canConnect = checkConnection(commandStation);

          setProgress(100);

          Logger.trace("canConnect: " + canConnect);
          if (canConnect) {
            this.firePropertyChange("done", "", "Connection succeeded");
          } else {
            this.firePropertyChange("done", "", "Can't Connect");
          }
        } else {
          setProgress(100);

          this.firePropertyChange("done", "", "Can't Connect");
        }
      }
      return null;
    }

    @Override
    public void done() {
      Toolkit.getDefaultToolkit().beep();
      testConnectionBtn.setEnabled(true);

      //connectionTestResultLbl.setText("");
      //progressMonitor.setProgress(0);
    }
  }

  private CommandStation createCommandStation(CommandStationBean commandStationBean) {
    CommandStation commandStation = null;

    String commandStationImplClassName = commandStationBean.getClassName();
    try {
      commandStation = (CommandStation) Class.forName(commandStationImplClassName).getDeclaredConstructor(Boolean.class, CommandStationBean.class).newInstance(false, commandStationBean);
    } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
      Logger.trace("Can't instantiate a '" + commandStationImplClassName + "' " + ex.getMessage());
      Logger.trace(ex);
    }
    return commandStation;
  }

  private boolean checkConnection(final CommandStation commandStation) {
    if (commandStation != null) {
      if (commandStation.isConnected()) {
        return true;
      } else {
        boolean canConnect = commandStation.connect();
        if (canConnect) {
          commandStation.disconnect();
        }
        return canConnect;
      }
    }
    return false;

  }

  public static void main(String args[]) {
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.warn("Can't set the LookAndFeel: " + ex);
    }
    java.awt.EventQueue.invokeLater(() -> {

      CommandStationPanel testPanel = new CommandStationPanel();
      JFrame testFrame = new JFrame();
      JDialog testDialog = new JDialog(testFrame, true);

      testDialog.add(testPanel);

      testDialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          System.exit(0);
        }
      });
      testDialog.pack();
      testDialog.setLocationRelativeTo(null);

      testDialog.setVisible(true);
    });
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  JCheckBox autoConfChkBox;
  JPanel bottomPanel;
  JPanel centerPanel;
  JComboBox<CommandStationBean> commandStationComboBox;
  JLabel commandStationLbl;
  JPanel commandStationSelectionPanel;
  JLabel connectionPropertiesLbl;
  JLabel connectionTestResultLbl;
  JComboBox<ConnectionType> connectionTypeCB;
  JLabel connectionTypeLbl;
  JPanel connectionTypePanel;
  JCheckBox defaultCommandStationChkBox;
  Box.Filler filler1;
  Box.Filler filler2;
  Box.Filler filler3;
  JTextField ipAddressTF;
  JLabel portLbl;
  JSpinner portSpinner;
  JProgressBar progressBar;
  JButton saveBtn;
  JComboBox<SerialPort> serialPortCB;
  JButton testConnectionBtn;
  JPanel testConnectionPanel;
  JPanel topPanel;
  // End of variables declaration//GEN-END:variables
}
