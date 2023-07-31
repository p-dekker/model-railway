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
package jcs.ui.layout.dialogs;

import jcs.entities.SensorBean;
import jcs.persistence.PersistenceFactory;
import jcs.trackservice.TrackControllerFactory;
import jcs.ui.layout.tiles.Sensor;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class SensorDialog extends javax.swing.JDialog {

  private final Sensor sensor;

  /**
   * Creates new form SensorDialog
   *
   * @param parent
   * @param sensor
   */
  public SensorDialog(java.awt.Frame parent, Sensor sensor) {
    super(parent, true);
    this.sensor = sensor;
    initComponents();

    postInit();
  }

  private void postInit() {
    setLocationRelativeTo(null);
    String text = this.headingLbl.getText() + " " + this.sensor.getId();
    this.headingLbl.setText(text);

    if (this.sensor != null) {
      SensorBean sb = this.sensor.getSensorBean();
      if (sb == null && sensor.getSensorId() != null) {
        sb = PersistenceFactory.getService().getSensor(sensor.getSensorId());
        sensor.setSensorBean(sb);
      }
      if (sb == null) {
        sb = new SensorBean();
        this.sensor.setSensorBean(sb);
      } else {
        if (TrackControllerFactory.getTrackController() != null) {
          //Unregister is properties might change
          TrackControllerFactory.getTrackController().removeSensorListener(this.sensor);
        }
      }

      this.nameTF.setText(this.sensor.getSensorBean().getName());
      if (this.sensor.getSensorBean().getDeviceId() != null) {
        this.deviceIdSpinner.setValue(this.sensor.getSensorBean().getDeviceId());
      }
      if (this.sensor.getSensorBean().getContactId() != null) {
        this.contactIdSpinner.setValue(this.sensor.getSensorBean().getContactId());
      }
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headingPanel = new javax.swing.JPanel();
        headingLbl = new javax.swing.JLabel();
        namePanel = new javax.swing.JPanel();
        nameLbl = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        deviceIdPanel = new javax.swing.JPanel();
        deviceIdLbl = new javax.swing.JLabel();
        deviceIdSpinner = new javax.swing.JSpinner();
        contactIdPanel = new javax.swing.JPanel();
        contactIdLbl = new javax.swing.JLabel();
        contactIdSpinner = new javax.swing.JSpinner();
        saveExitPanel = new javax.swing.JPanel();
        saveExitBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sensor Properties");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        headingPanel.setMinimumSize(new java.awt.Dimension(290, 40));
        headingPanel.setPreferredSize(new java.awt.Dimension(290, 40));
        headingPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        headingLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/new-straight-feedback.png"))); // NOI18N
        headingLbl.setText("Sensor");
        headingPanel.add(headingLbl);

        getContentPane().add(headingPanel);

        namePanel.setMinimumSize(new java.awt.Dimension(290, 40));
        namePanel.setPreferredSize(new java.awt.Dimension(290, 40));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        namePanel.setLayout(flowLayout1);

        nameLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLbl.setText("Name:");
        nameLbl.setPreferredSize(new java.awt.Dimension(100, 16));
        namePanel.add(nameLbl);

        nameTF.setPreferredSize(new java.awt.Dimension(150, 26));
        namePanel.add(nameTF);

        getContentPane().add(namePanel);

        deviceIdPanel.setPreferredSize(new java.awt.Dimension(290, 40));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout2.setAlignOnBaseline(true);
        deviceIdPanel.setLayout(flowLayout2);

        deviceIdLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        deviceIdLbl.setText("Device ID:");
        deviceIdLbl.setPreferredSize(new java.awt.Dimension(100, 16));
        deviceIdPanel.add(deviceIdLbl);

        deviceIdSpinner.setModel(new javax.swing.SpinnerNumberModel());
        deviceIdSpinner.setPreferredSize(new java.awt.Dimension(80, 26));
        deviceIdPanel.add(deviceIdSpinner);

        getContentPane().add(deviceIdPanel);

        contactIdPanel.setPreferredSize(new java.awt.Dimension(290, 40));
        java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout3.setAlignOnBaseline(true);
        contactIdPanel.setLayout(flowLayout3);

        contactIdLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        contactIdLbl.setText("Contact ID:");
        contactIdLbl.setPreferredSize(new java.awt.Dimension(100, 16));
        contactIdPanel.add(contactIdLbl);

        contactIdSpinner.setModel(new javax.swing.SpinnerNumberModel());
        contactIdSpinner.setPreferredSize(new java.awt.Dimension(80, 26));
        contactIdPanel.add(contactIdSpinner);

        getContentPane().add(contactIdPanel);

        saveExitPanel.setPreferredSize(new java.awt.Dimension(290, 50));
        java.awt.FlowLayout flowLayout4 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout4.setAlignOnBaseline(true);
        saveExitPanel.setLayout(flowLayout4);

        saveExitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/save-24.png"))); // NOI18N
        saveExitBtn.setToolTipText("Save and Exit");
        saveExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveExitBtnActionPerformed(evt);
            }
        });
        saveExitPanel.add(saveExitBtn);

        getContentPane().add(saveExitPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExitBtnActionPerformed

      if (this.sensor != null && this.sensor.getSensorBean() != null) {
        SensorBean sensorBean = this.sensor.getSensorBean();

        sensorBean.setContactId((Integer) this.contactIdSpinner.getValue());
        sensorBean.setDeviceId((Integer) this.deviceIdSpinner.getValue());
        sensorBean.setName(this.nameTF.getText());

        sensor.setSensorBean(sensorBean);

        if (PersistenceFactory.getService() != null) {
          PersistenceFactory.getService().persist(sensorBean);

          PersistenceFactory.getService().persist((sensor));
          TrackControllerFactory.getTrackController().addSensorListener(sensor);
        }
      } else if (this.sensor != null && this.sensor.getSensorBean() == null) {
        SensorBean sensorBean = new SensorBean(this.nameTF.getText(), (Integer) this.deviceIdSpinner.getValue(), (Integer) contactIdSpinner.getValue());

        if (PersistenceFactory.getService() != null) {
          sensorBean = PersistenceFactory.getService().persist(sensorBean);

          sensor.setSensorBean(sensorBean);
          Logger.trace("Created " + sensorBean);

          PersistenceFactory.getService().persist((sensor));
          TrackControllerFactory.getTrackController().addSensorListener(sensor);

        }
      }

      Logger.trace(evt.getActionCommand() + " Linking Sensor " + sensor.getId() + " to " + sensor.getSensorId());

      this.setVisible(false);
      this.dispose();
    }//GEN-LAST:event_saveExitBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactIdLbl;
    private javax.swing.JPanel contactIdPanel;
    private javax.swing.JSpinner contactIdSpinner;
    private javax.swing.JLabel deviceIdLbl;
    private javax.swing.JPanel deviceIdPanel;
    private javax.swing.JSpinner deviceIdSpinner;
    private javax.swing.JLabel headingLbl;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JPanel namePanel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JButton saveExitBtn;
    private javax.swing.JPanel saveExitPanel;
    // End of variables declaration//GEN-END:variables
}
