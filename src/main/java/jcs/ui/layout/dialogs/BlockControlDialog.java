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

import java.util.LinkedList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import jcs.entities.BlockBean;
import jcs.entities.LocomotiveBean;
import jcs.persistence.PersistenceFactory;
import jcs.ui.layout.tiles.Block;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class BlockControlDialog extends javax.swing.JDialog {

  private final Block block;

  private ComboBoxModel<LocomotiveBean> locomotiveComboBoxModel;

  /**
   * Creates new form SensorDialog
   *
   * @param parent
   * @param block
   */
  public BlockControlDialog(java.awt.Frame parent, Block block) {
    super(parent, true);
    this.block = block;
    initComponents();

    postInit();
  }

  private void postInit() {
    setLocationRelativeTo(null);
    String text = this.headingLbl.getText() + " " + this.block.getId();
    this.headingLbl.setText(text);

    if (this.block != null) {
      List<LocomotiveBean> locos = new LinkedList<>();
      LocomotiveBean emptyBean = new LocomotiveBean();
      locos.add(emptyBean);
      locos.addAll(PersistenceFactory.getService().getLocomotives());
      locomotiveComboBoxModel = new DefaultComboBoxModel(locos.toArray());
      this.locomotiveCB.setModel(locomotiveComboBoxModel);

      BlockBean bb = this.block.getBlockBean();
      if (bb == null) {
        bb = PersistenceFactory.getService().getBlockByTileId(block.getId());
        if(bb == null) {   
          Logger.warn("Block has no BlockBean. Creating one...");
          bb = new BlockBean();
          bb.setTile(block);
          bb.setTileId(this.block.getId());
        }  
        this.block.setBlockBean(bb);
      }

      this.blockIdTF.setText(this.block.getId());
      this.blockNameTF.setText(bb.getDescription());

      if (bb.getLocomotiveId() != null && bb.getLocomotive() == null) {
        bb.setLocomotive(PersistenceFactory.getService().getLocomotive(bb.getLocomotiveId()));
      }

      if (bb.getLocomotive() != null) {
        this.locomotiveCB.setSelectedItem(bb.getLocomotive());
      } else {
        this.locomotiveCB.setSelectedItem(emptyBean);
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
    deviceIdPanel = new javax.swing.JPanel();
    blockIdLbl = new javax.swing.JLabel();
    blockIdTF = new javax.swing.JTextField();
    namePanel = new javax.swing.JPanel();
    blockDescLbl = new javax.swing.JLabel();
    blockNameTF = new javax.swing.JTextField();
    locomotiveImagePanel = new javax.swing.JPanel();
    locomotiveIconLbl = new javax.swing.JLabel();
    locomotivePanel = new javax.swing.JPanel();
    locomotiveLbl = new javax.swing.JLabel();
    locomotiveCB = new javax.swing.JComboBox<>();
    saveExitPanel = new javax.swing.JPanel();
    saveExitBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Block Properties");
    setMinimumSize(new java.awt.Dimension(290, 200));
    getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

    headingPanel.setMinimumSize(new java.awt.Dimension(290, 40));
    headingPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    headingPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    headingLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/new-block.png"))); // NOI18N
    headingLbl.setText("Block, assign Locomotive");
    headingPanel.add(headingLbl);

    getContentPane().add(headingPanel);

    deviceIdPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout2.setAlignOnBaseline(true);
    deviceIdPanel.setLayout(flowLayout2);

    blockIdLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    blockIdLbl.setText("Id:");
    blockIdLbl.setPreferredSize(new java.awt.Dimension(100, 16));
    deviceIdPanel.add(blockIdLbl);

    blockIdTF.setEnabled(false);
    blockIdTF.setPreferredSize(new java.awt.Dimension(150, 23));
    deviceIdPanel.add(blockIdTF);

    getContentPane().add(deviceIdPanel);

    namePanel.setMinimumSize(new java.awt.Dimension(290, 40));
    namePanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout1.setAlignOnBaseline(true);
    namePanel.setLayout(flowLayout1);

    blockDescLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    blockDescLbl.setText("Name:");
    blockDescLbl.setToolTipText("");
    blockDescLbl.setPreferredSize(new java.awt.Dimension(100, 16));
    namePanel.add(blockDescLbl);

    blockNameTF.setEditable(false);
    blockNameTF.setPreferredSize(new java.awt.Dimension(150, 23));
    namePanel.add(blockNameTF);

    getContentPane().add(namePanel);

    locomotiveImagePanel.setPreferredSize(new java.awt.Dimension(290, 60));
    java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
    flowLayout5.setAlignOnBaseline(true);
    locomotiveImagePanel.setLayout(flowLayout5);

    locomotiveIconLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    locomotiveIconLbl.setDoubleBuffered(true);
    locomotiveIconLbl.setPreferredSize(new java.awt.Dimension(120, 60));
    locomotiveImagePanel.add(locomotiveIconLbl);

    getContentPane().add(locomotiveImagePanel);

    locomotivePanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout3.setAlignOnBaseline(true);
    locomotivePanel.setLayout(flowLayout3);

    locomotiveLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    locomotiveLbl.setText("Locomotive:");
    locomotiveLbl.setDoubleBuffered(true);
    locomotiveLbl.setPreferredSize(new java.awt.Dimension(100, 17));
    locomotivePanel.add(locomotiveLbl);

    locomotiveCB.setPreferredSize(new java.awt.Dimension(150, 23));
    locomotiveCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        locomotiveCBActionPerformed(evt);
      }
    });
    locomotivePanel.add(locomotiveCB);

    getContentPane().add(locomotivePanel);

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
      if (this.block != null && this.block.getBlockBean() != null) {
        BlockBean bb = this.block.getBlockBean();
        PersistenceFactory.getService().persist(bb);
      }

      this.setVisible(false);
      this.dispose();
      Logger.trace(evt.getActionCommand() + "Block " + block.getId() + " Locomotive: " + this.block.getBlockBean().getLocomotive());
    }//GEN-LAST:event_saveExitBtnActionPerformed

  private void locomotiveCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locomotiveCBActionPerformed
    Logger.trace(evt.getActionCommand() + " -> " + this.locomotiveComboBoxModel.getSelectedItem());

    LocomotiveBean selected = (LocomotiveBean) this.locomotiveComboBoxModel.getSelectedItem();

    this.block.getBlockBean().setLocomotive(selected);

    if (selected.getLocIcon() != null) {
      this.locomotiveIconLbl.setIcon(new ImageIcon(selected.getLocIcon()));
      this.locomotiveIconLbl.setText(null);
    } else {
      this.locomotiveIconLbl.setText(selected.getName());
    }


  }//GEN-LAST:event_locomotiveCBActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel blockDescLbl;
  private javax.swing.JLabel blockIdLbl;
  private javax.swing.JTextField blockIdTF;
  private javax.swing.JTextField blockNameTF;
  private javax.swing.JPanel deviceIdPanel;
  private javax.swing.JLabel headingLbl;
  private javax.swing.JPanel headingPanel;
  private javax.swing.JComboBox<LocomotiveBean> locomotiveCB;
  private javax.swing.JLabel locomotiveIconLbl;
  private javax.swing.JPanel locomotiveImagePanel;
  private javax.swing.JLabel locomotiveLbl;
  private javax.swing.JPanel locomotivePanel;
  private javax.swing.JPanel namePanel;
  private javax.swing.JButton saveExitBtn;
  private javax.swing.JPanel saveExitPanel;
  // End of variables declaration//GEN-END:variables
}
