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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import jcs.JCS;
import jcs.entities.SensorBean;
import jcs.entities.TileBean;
import jcs.persistence.PersistenceFactory;
import jcs.ui.layout.tiles.Sensor;
import jcs.ui.layout.tiles.Tile;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class SensorDialog extends javax.swing.JDialog {

  private final Sensor sensor;
  private final SensorBeanComboBoxModel sensorComboBoxModel;

  /**
   * Creates new form SensorDialog
   *
   * @param parent
   * @param tile
   */
  public SensorDialog(java.awt.Frame parent, Tile tile) {
    super(parent, true);
    this.sensor = (Sensor) tile;
    sensorComboBoxModel = new SensorBeanComboBoxModel();
    initComponents();

    postInit();
  }

  private void postInit() {
    setLocationRelativeTo(null);
    String text = this.headingLbl.getText() + " " + this.sensor.getId();
    this.headingLbl.setText(text);

    List<SensorBean> sensors = PersistenceFactory.getService().getSensors();
    List<TileBean> sensorTiles = PersistenceFactory.getService().getTileBeansByTileType(TileBean.TileType.SENSOR);

    Set<String> usedSensorIds = new HashSet<>();
    for (TileBean tb : sensorTiles) {
      if (tb.getSensorId() != null) {
        usedSensorIds.add(tb.getSensorId());
      }
    }
    //Filter the unused turnouts
    List<SensorBean> filtered = new ArrayList<>();
    for (SensorBean sb : sensors) {
      if (!usedSensorIds.contains(sb.getId())) {
        filtered.add(sb);
      }
    }
    //Ensure the selected is still there
    if (this.sensor.getSensorBean() != null) {
      filtered.add(this.sensor.getSensorBean());
    } else {
      if (sensor.getSensorId() != null && this.sensor.getSensorBean() == null) {
        this.sensor.setSensorBean(PersistenceFactory.getService().getSensor(sensor.getSensorId()));
        filtered.add(this.sensor.getSensorBean());
      }
    }

    //Expand with an empty one for display
    SensorBean emptyBean = new SensorBean();
    filtered.add(emptyBean);

    sensorComboBoxModel.removeAllElements();
    sensorComboBoxModel.addAll(filtered);
    this.sensorCB.setModel(sensorComboBoxModel);

    SensorBean sb = this.sensor.getSensorBean();
    if (sb == null) {
      sb = emptyBean;
      //Use the SensorTileId as id
      sb.setId(this.sensor.getId());
    }

    this.sensor.setSensorBean(sb);
    this.sensorComboBoxModel.setSelectedItem(sb);

    if (this.sensor.getSensorBean().getName() != null) {
      this.nameTF.setText(this.sensor.getSensorBean().getName());
    } else {
      //Use the tile name
      this.nameTF.setText(this.sensor.getTileBean().getId());
    }

    if (this.sensor.getSensorBean().getDeviceId() != null) {
      this.deviceIdSpinner.setValue(this.sensor.getSensorBean().getDeviceId());
    }
    if (this.sensor.getSensorBean().getContactId() != null) {
      this.contactIdSpinner.setValue(this.sensor.getSensorBean().getContactId());
    }

    if (JCS.getJcsCommandStation() != null) {
      //Unregister as properties might change
      JCS.getJcsCommandStation().removeSensorEventListener(this.sensor);
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
    selectPanel = new javax.swing.JPanel();
    sensorLbl = new javax.swing.JLabel();
    sensorCB = new javax.swing.JComboBox<>();
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

    selectPanel.setMinimumSize(new java.awt.Dimension(290, 40));
    selectPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout5.setAlignOnBaseline(true);
    selectPanel.setLayout(flowLayout5);

    sensorLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    sensorLbl.setText("Select Sensor:");
    sensorLbl.setPreferredSize(new java.awt.Dimension(100, 16));
    selectPanel.add(sensorLbl);

    sensorCB.setPreferredSize(new java.awt.Dimension(150, 27));
    sensorCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sensorCBActionPerformed(evt);
      }
    });
    selectPanel.add(sensorCB);

    getContentPane().add(selectPanel);

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
          JCS.getJcsCommandStation().addSensorEventListener(sensor);
        }
      } else if (this.sensor != null && this.sensor.getSensorBean() == null) {
        SensorBean sensorBean = new SensorBean(this.nameTF.getText(), (Integer) this.deviceIdSpinner.getValue(), (Integer) contactIdSpinner.getValue());

        if (PersistenceFactory.getService() != null) {
          sensorBean = PersistenceFactory.getService().persist(sensorBean);
          sensor.setSensorBean(sensorBean);
          Logger.trace("Created " + sensorBean);

          PersistenceFactory.getService().persist((sensor));
          JCS.getJcsCommandStation().addSensorEventListener(sensor);
        }
      }

      Logger.trace(evt.getActionCommand() + " Linking Sensor " + sensor.getId() + " to " + sensor.getSensorId());

      this.setVisible(false);
      this.dispose();
    }//GEN-LAST:event_saveExitBtnActionPerformed

  private void sensorCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sensorCBActionPerformed
    SensorBean selected = (SensorBean) this.sensorComboBoxModel.getSelectedItem();
    Logger.trace(evt.getActionCommand() + " Selected: " + selected.toLogString());
    this.sensor.setSensorBean(selected);

    this.nameTF.setText(this.sensor.getSensorBean().getName());
    if (this.sensor.getSensorBean().getDeviceId() != null) {
      this.deviceIdSpinner.setValue(this.sensor.getSensorBean().getDeviceId());
    } else {
      this.deviceIdSpinner.setValue(0);
    }
    if (this.sensor.getSensorBean().getContactId() != null) {
      this.contactIdSpinner.setValue(this.sensor.getSensorBean().getContactId());
    } else {
      this.contactIdSpinner.setValue(0);
    }
  }//GEN-LAST:event_sensorCBActionPerformed

  class SensorBeanByIdSorter implements Comparator<SensorBean> {

    @Override
    public int compare(SensorBean a, SensorBean b) {
      //Avoid null pointers
      String aa = a.getId();
      if (aa == null) {
        aa = "000";
      }
      String bb = b.getId();
      if (bb == null) {
        bb = "000";
      }

      return aa.compareTo(bb);
    }
  }

  class SensorBeanComboBoxModel extends DefaultComboBoxModel<SensorBean> {

    private final List<SensorBean> model;

    public SensorBeanComboBoxModel() {
      model = new ArrayList<>();
    }

    @Override
    public void addAll(int index, Collection<? extends SensorBean> elements) {
      model.addAll(index, elements);
      Collections.sort(model, new SensorBeanByIdSorter());

      fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void addAll(Collection<? extends SensorBean> elements) {
      model.addAll(elements);
      Collections.sort(model, new SensorBeanByIdSorter());

      fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void addElement(SensorBean element) {
      if (model.add(element)) {
        Collections.sort(model, new SensorBeanByIdSorter());

        fireContentsChanged(this, 0, getSize());
      }
    }

    @Override
    public SensorBean getElementAt(int index) {
      return (SensorBean) model.toArray()[index];
    }

    @Override
    public int getIndexOf(Object object) {
      return this.model.indexOf(object);
    }

    @Override
    public int getSize() {
      return model.size();
    }

    @Override
    public void insertElementAt(SensorBean sensorBean, int index) {
      this.model.add(index, sensorBean);
      this.fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void removeAllElements() {
      model.clear();
      fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void removeElement(Object anObject) {
      int index = this.getIndexOf(anObject);
      removeElementAt(index);
    }

    @Override
    public void removeElementAt(int index) {
      this.model.remove(index);
      fireContentsChanged(this, 0, getSize());
    }
  }


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
  private javax.swing.JPanel selectPanel;
  private javax.swing.JComboBox<SensorBean> sensorCB;
  private javax.swing.JLabel sensorLbl;
  // End of variables declaration//GEN-END:variables
}
