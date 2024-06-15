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
import jcs.commandStation.autopilot.AutoPilot;
import jcs.entities.BlockBean;
import jcs.entities.LocomotiveBean;
import jcs.persistence.PersistenceFactory;
import jcs.ui.layout.events.TileEvent;
import jcs.ui.layout.tiles.Block;
import jcs.ui.layout.tiles.TileFactory;
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
        if (bb == null) {
          Logger.warn("Block has no BlockBean. Creating one...");
          bb = new BlockBean();
          bb.setId(block.getId());
          bb.setTile(block);
          bb.setTileId(block.getId());
          bb.setBlockState(BlockBean.BlockState.FREE);
        }
        this.block.setBlockBean(bb);
      }

      this.blockIdTF.setText(block.getId());
      this.blockNameTF.setText(bb.getDescription());

      if (bb.getLocomotiveId() != null && bb.getLocomotive() == null) {
        bb.setLocomotive(PersistenceFactory.getService().getLocomotive(bb.getLocomotiveId()));
        this.startLocButton.setEnabled(true);
      }

      if (bb.getLocomotive() != null) {
        this.locomotiveCB.setSelectedItem(bb.getLocomotive());
        if (bb.getBlockState() == null) {
          bb.setBlockState(BlockBean.BlockState.OCCUPIED);
        }

        if (bb.getLocomotive().getLocIcon() != null) {
          this.locomotiveIconLbl.setIcon(new ImageIcon(bb.getLocomotive().getLocIcon()));
          this.locomotiveIconLbl.setText(null);
        } else {
          this.locomotiveIconLbl.setText(bb.getLocomotive().getName());
        }

        if (LocomotiveBean.Direction.BACKWARDS == bb.getLocomotive().getDirection()) {
          this.backwardsRB.setSelected(true);
        } else {
          this.forwardsRB.setSelected(true);
        }

        this.startLocButton.setEnabled(AutoPilot.getInstance().isRunning());
        this.startLocButton.setSelected(AutoPilot.getInstance().isRunning(bb.getLocomotive()));
      } else {
        this.locomotiveCB.setSelectedItem(emptyBean);
        this.startLocButton.setEnabled(false);

        if (bb.getBlockState() == null) {
          bb.setBlockState(BlockBean.BlockState.FREE);
        }
      }
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    locomotiveDirectionBG = new javax.swing.ButtonGroup();
    headingPanel = new javax.swing.JPanel();
    headingLbl = new javax.swing.JLabel();
    deviceIdPanel = new javax.swing.JPanel();
    blockIdLbl = new javax.swing.JLabel();
    blockIdTF = new javax.swing.JTextField();
    namePanel = new javax.swing.JPanel();
    blockDescLbl = new javax.swing.JLabel();
    blockNameTF = new javax.swing.JTextField();
    locomotivePanel = new javax.swing.JPanel();
    directionsPanel = new javax.swing.JPanel();
    arrivalPanel = new javax.swing.JPanel();
    reverseArrivalBtn = new javax.swing.JButton();
    locDirPanel = new javax.swing.JPanel();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
    backwardsRB = new javax.swing.JRadioButton();
    forwardsRB = new javax.swing.JRadioButton();
    imagePanel = new javax.swing.JPanel();
    locomotiveIconLbl = new javax.swing.JLabel();
    locomotiveSelectionPanel = new javax.swing.JPanel();
    locomotiveLbl = new javax.swing.JLabel();
    locomotiveCB = new javax.swing.JComboBox<>();
    bottomPanel = new javax.swing.JPanel();
    leftPanel = new javax.swing.JPanel();
    startLocButton = new javax.swing.JToggleButton();
    rightPanel = new javax.swing.JPanel();
    saveExitBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Block Control Properties");
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

    locomotivePanel.setPreferredSize(new java.awt.Dimension(290, 60));
    locomotivePanel.setLayout(new java.awt.GridLayout(1, 2));

    directionsPanel.setLayout(new java.awt.GridLayout(2, 1));

    java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout5.setAlignOnBaseline(true);
    arrivalPanel.setLayout(flowLayout5);

    reverseArrivalBtn.setText("Reverse Arrival");
    reverseArrivalBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        reverseArrivalBtnActionPerformed(evt);
      }
    });
    arrivalPanel.add(reverseArrivalBtn);

    directionsPanel.add(arrivalPanel);

    java.awt.FlowLayout flowLayout8 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout8.setAlignOnBaseline(true);
    locDirPanel.setLayout(flowLayout8);
    locDirPanel.add(filler1);

    locomotiveDirectionBG.add(backwardsRB);
    backwardsRB.setText("<<");
    backwardsRB.setToolTipText("");
    backwardsRB.setActionCommand("BACKWARDS");
    backwardsRB.setDoubleBuffered(true);
    backwardsRB.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
    backwardsRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        backwardsRBActionPerformed(evt);
      }
    });
    locDirPanel.add(backwardsRB);

    locomotiveDirectionBG.add(forwardsRB);
    forwardsRB.setText(">>");
    forwardsRB.setActionCommand("FORWARDS");
    forwardsRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        forwardsRBActionPerformed(evt);
      }
    });
    locDirPanel.add(forwardsRB);

    directionsPanel.add(locDirPanel);

    locomotivePanel.add(directionsPanel);

    locomotiveIconLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    locomotiveIconLbl.setDoubleBuffered(true);
    locomotiveIconLbl.setPreferredSize(new java.awt.Dimension(120, 60));
    imagePanel.add(locomotiveIconLbl);

    locomotivePanel.add(imagePanel);

    getContentPane().add(locomotivePanel);

    locomotiveSelectionPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout3.setAlignOnBaseline(true);
    locomotiveSelectionPanel.setLayout(flowLayout3);

    locomotiveLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    locomotiveLbl.setText("Locomotive:");
    locomotiveLbl.setDoubleBuffered(true);
    locomotiveLbl.setPreferredSize(new java.awt.Dimension(100, 17));
    locomotiveSelectionPanel.add(locomotiveLbl);

    locomotiveCB.setPreferredSize(new java.awt.Dimension(150, 23));
    locomotiveCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        locomotiveCBActionPerformed(evt);
      }
    });
    locomotiveSelectionPanel.add(locomotiveCB);

    getContentPane().add(locomotiveSelectionPanel);

    bottomPanel.setPreferredSize(new java.awt.Dimension(290, 50));
    java.awt.FlowLayout flowLayout4 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
    flowLayout4.setAlignOnBaseline(true);
    bottomPanel.setLayout(flowLayout4);

    leftPanel.setPreferredSize(new java.awt.Dimension(145, 50));
    java.awt.FlowLayout flowLayout7 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout7.setAlignOnBaseline(true);
    leftPanel.setLayout(flowLayout7);

    startLocButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/direction-right-24.png"))); // NOI18N
    startLocButton.setToolTipText("Start Locomotive");
    startLocButton.setDoubleBuffered(true);
    startLocButton.setPreferredSize(new java.awt.Dimension(35, 35));
    startLocButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/circle-stop.png"))); // NOI18N
    startLocButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startLocButtonActionPerformed(evt);
      }
    });
    leftPanel.add(startLocButton);

    bottomPanel.add(leftPanel);

    rightPanel.setPreferredSize(new java.awt.Dimension(145, 50));
    java.awt.FlowLayout flowLayout6 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
    flowLayout6.setAlignOnBaseline(true);
    rightPanel.setLayout(flowLayout6);

    saveExitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/save-24.png"))); // NOI18N
    saveExitBtn.setToolTipText("Save and Exit");
    saveExitBtn.setPreferredSize(new java.awt.Dimension(35, 35));
    saveExitBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveExitBtnActionPerformed(evt);
      }
    });
    rightPanel.add(saveExitBtn);

    bottomPanel.add(rightPanel);

    getContentPane().add(bottomPanel);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void saveExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExitBtnActionPerformed
      if (this.block != null && this.block.getBlockBean() != null) {
        BlockBean bb = this.block.getBlockBean();
        PersistenceFactory.getService().persist(bb);
        if (bb.getLocomotive() != null) {
          LocomotiveBean loc = bb.getLocomotive();
          PersistenceFactory.getService().persist(loc);
        }
        TileEvent tileEvent = new TileEvent(bb);
        TileFactory.fireTileEventListener(tileEvent);
      }

      this.setVisible(false);
      this.dispose();
      Logger.trace(evt.getActionCommand() + "Block " + block.getId() + " Locomotive: " + this.block.getBlockBean().getLocomotive());
    }//GEN-LAST:event_saveExitBtnActionPerformed

  private void locomotiveCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locomotiveCBActionPerformed
    Logger.trace(evt.getActionCommand() + " -> " + this.locomotiveComboBoxModel.getSelectedItem());

    LocomotiveBean selected = (LocomotiveBean) this.locomotiveComboBoxModel.getSelectedItem();

    block.getBlockBean().setLocomotive(selected);
    if (block.getBlockBean().getLocomotiveId() != null) {
      block.getBlockBean().setBlockState(BlockBean.BlockState.OCCUPIED);
    } else {
      block.getBlockBean().setBlockState(BlockBean.BlockState.FREE);
    }

    if (selected.getLocIcon() != null) {
      locomotiveIconLbl.setIcon(new ImageIcon(selected.getLocIcon()));
      locomotiveIconLbl.setText(null);
    } else {
      locomotiveIconLbl.setText(selected.getName());
    }

    if (LocomotiveBean.Direction.BACKWARDS == selected.getDirection()) {
      this.backwardsRB.setSelected(true);
    } else {
      this.forwardsRB.setSelected(true);
    }

    if (block.getBlockBean().getLocomotiveId() != null) {
      startLocButton.setEnabled(true);
    } else {
      startLocButton.setEnabled(false);
    }
  }//GEN-LAST:event_locomotiveCBActionPerformed

  private void startLocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startLocButtonActionPerformed
    LocomotiveBean loc = this.block.getBlockBean().getLocomotive();
    if (loc != null) {
      AutoPilot.getInstance().startStopLocomotive(loc, this.startLocButton.isSelected());
    }
  }//GEN-LAST:event_startLocButtonActionPerformed

  private void reverseArrivalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reverseArrivalBtnActionPerformed
    String suffix = block.getBlockBean().getArrivalSuffix();
    if ("+".equals(suffix)) {
      block.getBlockBean().setArrivalSuffix("-");
    } else {
      block.getBlockBean().setArrivalSuffix("+");
    }
    block.getBlockBean().setReverseArrival(!block.getBlockBean().isReverseArrival());
  }//GEN-LAST:event_reverseArrivalBtnActionPerformed

  private void backwardsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backwardsRBActionPerformed
    LocomotiveBean loc = this.block.getBlockBean().getLocomotive();
    loc.setDirection(LocomotiveBean.Direction.BACKWARDS);
  }//GEN-LAST:event_backwardsRBActionPerformed

  private void forwardsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardsRBActionPerformed
    LocomotiveBean loc = this.block.getBlockBean().getLocomotive();
    loc.setDirection(LocomotiveBean.Direction.FORWARDS);
  }//GEN-LAST:event_forwardsRBActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  javax.swing.JPanel arrivalPanel;
  javax.swing.JRadioButton backwardsRB;
  javax.swing.JLabel blockDescLbl;
  javax.swing.JLabel blockIdLbl;
  javax.swing.JTextField blockIdTF;
  javax.swing.JTextField blockNameTF;
  javax.swing.JPanel bottomPanel;
  javax.swing.JPanel deviceIdPanel;
  javax.swing.JPanel directionsPanel;
  javax.swing.Box.Filler filler1;
  javax.swing.JRadioButton forwardsRB;
  javax.swing.JLabel headingLbl;
  javax.swing.JPanel headingPanel;
  javax.swing.JPanel imagePanel;
  javax.swing.JPanel leftPanel;
  javax.swing.JPanel locDirPanel;
  javax.swing.JComboBox<LocomotiveBean> locomotiveCB;
  javax.swing.ButtonGroup locomotiveDirectionBG;
  javax.swing.JLabel locomotiveIconLbl;
  javax.swing.JLabel locomotiveLbl;
  javax.swing.JPanel locomotivePanel;
  javax.swing.JPanel locomotiveSelectionPanel;
  javax.swing.JPanel namePanel;
  javax.swing.JButton reverseArrivalBtn;
  javax.swing.JPanel rightPanel;
  javax.swing.JButton saveExitBtn;
  javax.swing.JToggleButton startLocButton;
  // End of variables declaration//GEN-END:variables
}
