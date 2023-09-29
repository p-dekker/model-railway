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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import jcs.entities.AccessoryBean;
import jcs.persistence.PersistenceFactory;
import jcs.controller.ControllerFactory;
import jcs.entities.AccessoryBean;
import jcs.entities.TileBean;
import jcs.entities.enums.TileType;
import jcs.ui.layout.tiles.Switch;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class SwitchDialog extends javax.swing.JDialog {

  private final Switch turnout;
  private ComboBoxModel<AccessoryBean> accessoryComboBoxModel;

  /**
   * Creates new form SensorDialog
   *
   * @param parent
   * @param turnout
   */
  public SwitchDialog(java.awt.Frame parent, Switch turnout) {
    super(parent, true);
    this.turnout = turnout;
    initComponents();

    postInit();
  }

  private void postInit() {
    setLocationRelativeTo(null);
    String text = this.headingLbl.getText() + " " + this.turnout.getId();
    this.headingLbl.setText(text);

    if (this.turnout != null) {
      //Get a list of all available Turnouts
      List<AccessoryBean> accessoryBeans = PersistenceFactory.getService().getTurnouts();

      List<TileBean> turnoutTiles = PersistenceFactory.getService().getTileBeansByTileType(TileType.SWITCH);
      turnoutTiles.addAll(PersistenceFactory.getService().getTileBeansByTileType(TileType.CROSS));

      Set<String> usedAccessoryIds = new HashSet<>();
      for (TileBean tb : turnoutTiles) {
        if (tb.getAccessoryId() != null) {
          usedAccessoryIds.add(tb.getAccessoryId());
        }
      }
      //Filter the unused turnouts
      List<AccessoryBean> filtered = new ArrayList<>();
      for (AccessoryBean ab : accessoryBeans) {
        if (!usedAccessoryIds.contains(ab.getId())) {
          filtered.add(ab);
        }
      }
      //Ensure the selectes is still there
      if (this.turnout.getAccessoryBean() != null) {
        filtered.add(this.turnout.getAccessoryBean());
      }
      //Expand with an empty one for display
      AccessoryBean emptyBean = new AccessoryBean();
      filtered.add(emptyBean);

      accessoryComboBoxModel = new DefaultComboBoxModel(filtered.toArray());
      this.accessoryCB.setModel(accessoryComboBoxModel);

      AccessoryBean ab = this.turnout.getAccessoryBean();
      if (turnout.getAccessoryId() != null && ab == null) {
        ab = PersistenceFactory.getService().getAccessory(turnout.getAccessoryId());
      }
      if (ab == null) {
        ab = emptyBean;
      }
      this.turnout.setAccessoryBean(ab);
      this.accessoryComboBoxModel.setSelectedItem(ab);
      Logger.trace("Selected Turnout: " + ab);

      //Unregister as properties might change
      ControllerFactory.getController().removeAccessoryEventListener(turnout);
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
    deviceIdPanel = new javax.swing.JPanel();
    turnoutLbl = new javax.swing.JLabel();
    accessoryCB = new javax.swing.JComboBox<>();
    contactIdPanel = new javax.swing.JPanel();
    saveExitPanel = new javax.swing.JPanel();
    saveExitBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Turnout Properties");
    getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

    headingPanel.setMinimumSize(new java.awt.Dimension(290, 40));
    headingPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    headingPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    headingLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/new-R-turnout.png"))); // NOI18N
    headingLbl.setText("Turnout");
    headingPanel.add(headingLbl);

    getContentPane().add(headingPanel);

    namePanel.setMinimumSize(new java.awt.Dimension(290, 40));
    namePanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout1.setAlignOnBaseline(true);
    namePanel.setLayout(flowLayout1);
    getContentPane().add(namePanel);

    deviceIdPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout2.setAlignOnBaseline(true);
    deviceIdPanel.setLayout(flowLayout2);

    turnoutLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    turnoutLbl.setText("Turnout:");
    turnoutLbl.setPreferredSize(new java.awt.Dimension(100, 16));
    deviceIdPanel.add(turnoutLbl);

    accessoryCB.setPreferredSize(new java.awt.Dimension(150, 27));
    accessoryCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        accessoryCBActionPerformed(evt);
      }
    });
    deviceIdPanel.add(accessoryCB);

    getContentPane().add(deviceIdPanel);

    contactIdPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout3.setAlignOnBaseline(true);
    contactIdPanel.setLayout(flowLayout3);
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
      if (this.turnout != null && this.turnout.getAccessoryBean() != null) {
        if (this.turnout.getAccessoryBean().getName() != null) {
          PersistenceFactory.getService().persist((turnout));

          ControllerFactory.getController().addAccessoryEventListener(turnout);
        } else {
          this.turnout.setAccessoryBean(null);
          PersistenceFactory.getService().persist((turnout));
        }
      }
      this.setVisible(false);
      this.dispose();
      Logger.trace(evt.getActionCommand() + "Switch " + turnout.getId() + " linked to accessoryId: " + turnout.getAccessoryId());
    }//GEN-LAST:event_saveExitBtnActionPerformed

    private void accessoryCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessoryCBActionPerformed
      AccessoryBean selected = (AccessoryBean) this.accessoryComboBoxModel.getSelectedItem();
      Logger.trace(evt.getActionCommand() + " Selected: " + selected.toLogString());
      this.turnout.setAccessoryBean(selected);
    }//GEN-LAST:event_accessoryCBActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<AccessoryBean> accessoryCB;
  private javax.swing.JPanel contactIdPanel;
  private javax.swing.JPanel deviceIdPanel;
  private javax.swing.JLabel headingLbl;
  private javax.swing.JPanel headingPanel;
  private javax.swing.JPanel namePanel;
  private javax.swing.JButton saveExitBtn;
  private javax.swing.JPanel saveExitPanel;
  private javax.swing.JLabel turnoutLbl;
  // End of variables declaration//GEN-END:variables
}
