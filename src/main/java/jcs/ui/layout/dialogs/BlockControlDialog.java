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
      locos.addAll(PersistenceFactory.getService().getLocomotives(true));
      //Only Loc's which should be shown
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
          bb.setMaxWaitTime(0);
          bb.setMinWaitTime(10);
        }
        this.block.setBlockBean(bb);
      }
      
      this.blockIdTF.setText(block.getId());
      this.blockNameTF.setText(bb.getDescription());
      
      if (bb.getLocomotiveId() != null && bb.getLocomotive() == null) {
        bb.setLocomotive(PersistenceFactory.getService().getLocomotive(bb.getLocomotiveId()));
        this.startLocButton.setEnabled(true);
      }
      
      if (bb.getMinWaitTime() != null) {
        this.minWaitSpinner.setValue(bb.getMinWaitTime());
      }
      
      if (bb.getMaxWaitTime() != null) {
        this.maxWaitSpinner.setValue(bb.getMaxWaitTime());
      }
      
      this.alwaysStopCB.setSelected(bb.isAlwaysStop());
      this.randomWaitCB.setSelected(bb.isRandomWait());
      
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
        
        this.startLocButton.setEnabled(AutoPilot.isAutoModeActive());
        this.startLocButton.setSelected(AutoPilot.isRunning(bb.getLocomotive()));
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
    propertiesTP = new javax.swing.JTabbedPane();
    propPanel = new javax.swing.JPanel();
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
    backwardsRB = new javax.swing.JRadioButton();
    forwardsRB = new javax.swing.JRadioButton();
    imagePanel = new javax.swing.JPanel();
    locomotiveIconLbl = new javax.swing.JLabel();
    locomotiveSelectionPanel = new javax.swing.JPanel();
    locomotiveLbl = new javax.swing.JLabel();
    locomotiveCB = new javax.swing.JComboBox<>();
    filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
    startLocButton = new javax.swing.JToggleButton();
    waitPanel = new javax.swing.JPanel();
    waitPropPanel = new javax.swing.JPanel();
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(20, 32767));
    alwaysStopCB = new javax.swing.JCheckBox();
    randomWaitCB = new javax.swing.JCheckBox();
    minTimePanel = new javax.swing.JPanel();
    minWaitLbl = new javax.swing.JLabel();
    minWaitSpinner = new javax.swing.JSpinner();
    maxTimePanel = new javax.swing.JPanel();
    maxWaitLbl = new javax.swing.JLabel();
    maxWaitSpinner = new javax.swing.JSpinner();
    bottomPanel = new javax.swing.JPanel();
    saveExitBtn = new javax.swing.JButton();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Block Control Properties");
    setMinimumSize(new java.awt.Dimension(410, 310));
    setSize(new java.awt.Dimension(410, 410));

    headingPanel.setMinimumSize(new java.awt.Dimension(290, 40));
    headingPanel.setPreferredSize(new java.awt.Dimension(290, 40));
    headingPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    headingLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/new-block.png"))); // NOI18N
    headingLbl.setText("Block properties");
    headingPanel.add(headingLbl);

    getContentPane().add(headingPanel, java.awt.BorderLayout.NORTH);

    propPanel.setLayout(new javax.swing.BoxLayout(propPanel, javax.swing.BoxLayout.Y_AXIS));

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

    propPanel.add(deviceIdPanel);

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

    propPanel.add(namePanel);

    locomotivePanel.setPreferredSize(new java.awt.Dimension(290, 60));
    locomotivePanel.setLayout(new java.awt.GridLayout(1, 2));

    directionsPanel.setLayout(new java.awt.GridLayout(2, 1));

    java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout5.setAlignOnBaseline(true);
    arrivalPanel.setLayout(flowLayout5);

    reverseArrivalBtn.setText("Reverse Arrival");
    reverseArrivalBtn.setToolTipText("Reverse the Block arrival side");
    reverseArrivalBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
    reverseArrivalBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        reverseArrivalBtnActionPerformed(evt);
      }
    });
    arrivalPanel.add(reverseArrivalBtn);

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
    arrivalPanel.add(backwardsRB);

    locomotiveDirectionBG.add(forwardsRB);
    forwardsRB.setText(">>");
    forwardsRB.setActionCommand("FORWARDS");
    forwardsRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        forwardsRBActionPerformed(evt);
      }
    });
    arrivalPanel.add(forwardsRB);

    directionsPanel.add(arrivalPanel);

    locomotivePanel.add(directionsPanel);

    locomotiveIconLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    locomotiveIconLbl.setDoubleBuffered(true);
    locomotiveIconLbl.setPreferredSize(new java.awt.Dimension(120, 60));
    imagePanel.add(locomotiveIconLbl);

    locomotivePanel.add(imagePanel);

    propPanel.add(locomotivePanel);

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
    locomotiveSelectionPanel.add(filler3);

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
    locomotiveSelectionPanel.add(startLocButton);

    propPanel.add(locomotiveSelectionPanel);

    propertiesTP.addTab("Assignments", propPanel);

    waitPanel.setLayout(new javax.swing.BoxLayout(waitPanel, javax.swing.BoxLayout.Y_AXIS));

    java.awt.FlowLayout flowLayout9 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout9.setAlignOnBaseline(true);
    waitPropPanel.setLayout(flowLayout9);
    waitPropPanel.add(filler2);

    alwaysStopCB.setText("Always Stop");
    alwaysStopCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        alwaysStopCBActionPerformed(evt);
      }
    });
    waitPropPanel.add(alwaysStopCB);

    randomWaitCB.setText("Random Wait");
    randomWaitCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        randomWaitCBActionPerformed(evt);
      }
    });
    waitPropPanel.add(randomWaitCB);

    waitPanel.add(waitPropPanel);

    java.awt.FlowLayout flowLayout10 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout10.setAlignOnBaseline(true);
    minTimePanel.setLayout(flowLayout10);

    minWaitLbl.setLabelFor(minWaitSpinner);
    minWaitLbl.setText("Min. Wait Time");
    minWaitLbl.setPreferredSize(new java.awt.Dimension(100, 17));
    minTimePanel.add(minWaitLbl);

    minWaitSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 0, null, 1));
    minWaitSpinner.setPreferredSize(new java.awt.Dimension(75, 23));
    minWaitSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        minWaitSpinnerStateChanged(evt);
      }
    });
    minTimePanel.add(minWaitSpinner);

    waitPanel.add(minTimePanel);

    java.awt.FlowLayout flowLayout11 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout11.setAlignOnBaseline(true);
    maxTimePanel.setLayout(flowLayout11);

    maxWaitLbl.setLabelFor(maxWaitSpinner);
    maxWaitLbl.setText("Max. Wait Time");
    maxWaitLbl.setPreferredSize(new java.awt.Dimension(100, 17));
    maxTimePanel.add(maxWaitLbl);

    maxWaitSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 600, 1));
    maxWaitSpinner.setPreferredSize(new java.awt.Dimension(75, 23));
    maxWaitSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        maxWaitSpinnerStateChanged(evt);
      }
    });
    maxTimePanel.add(maxWaitSpinner);

    waitPanel.add(maxTimePanel);

    propertiesTP.addTab("Wait Times", waitPanel);

    getContentPane().add(propertiesTP, java.awt.BorderLayout.CENTER);

    bottomPanel.setPreferredSize(new java.awt.Dimension(290, 50));
    java.awt.FlowLayout flowLayout4 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
    flowLayout4.setAlignOnBaseline(true);
    bottomPanel.setLayout(flowLayout4);

    saveExitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/save-24.png"))); // NOI18N
    saveExitBtn.setToolTipText("Save and Exit");
    saveExitBtn.setPreferredSize(new java.awt.Dimension(35, 35));
    saveExitBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveExitBtnActionPerformed(evt);
      }
    });
    bottomPanel.add(saveExitBtn);
    bottomPanel.add(filler1);

    getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void saveExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExitBtnActionPerformed
      if (this.block != null && this.block.getBlockBean() != null) {
        BlockBean bb = this.block.getBlockBean();
        PersistenceFactory.getService().persist(bb);
        if (bb.getLocomotive() != null && bb.getLocomotive().getName() != null) {
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
      AutoPilot.startStopLocomotive(loc, this.startLocButton.isSelected());
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
    block.getBlockBean().setLogicalDirection(LocomotiveBean.Direction.BACKWARDS.getDirection());
  }//GEN-LAST:event_backwardsRBActionPerformed

  private void forwardsRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardsRBActionPerformed
    block.getBlockBean().setLogicalDirection(LocomotiveBean.Direction.FORWARDS.getDirection());
  }//GEN-LAST:event_forwardsRBActionPerformed

  private void alwaysStopCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alwaysStopCBActionPerformed
    this.block.getBlockBean().setAlwaysStop(this.alwaysStopCB.isSelected());
  }//GEN-LAST:event_alwaysStopCBActionPerformed

  private void randomWaitCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomWaitCBActionPerformed
    this.block.getBlockBean().setRandomWait(this.randomWaitCB.isSelected());
  }//GEN-LAST:event_randomWaitCBActionPerformed

  private void minWaitSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minWaitSpinnerStateChanged
    this.block.getBlockBean().setMinWaitTime((Integer) this.minWaitSpinner.getValue());
  }//GEN-LAST:event_minWaitSpinnerStateChanged

  private void maxWaitSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxWaitSpinnerStateChanged
    this.block.getBlockBean().setMaxWaitTime((Integer) this.maxWaitSpinner.getValue());
  }//GEN-LAST:event_maxWaitSpinnerStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  javax.swing.JCheckBox alwaysStopCB;
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
  javax.swing.Box.Filler filler2;
  javax.swing.Box.Filler filler3;
  javax.swing.JRadioButton forwardsRB;
  javax.swing.JLabel headingLbl;
  javax.swing.JPanel headingPanel;
  javax.swing.JPanel imagePanel;
  javax.swing.JComboBox<LocomotiveBean> locomotiveCB;
  javax.swing.ButtonGroup locomotiveDirectionBG;
  javax.swing.JLabel locomotiveIconLbl;
  javax.swing.JLabel locomotiveLbl;
  javax.swing.JPanel locomotivePanel;
  javax.swing.JPanel locomotiveSelectionPanel;
  javax.swing.JPanel maxTimePanel;
  javax.swing.JLabel maxWaitLbl;
  javax.swing.JSpinner maxWaitSpinner;
  javax.swing.JPanel minTimePanel;
  javax.swing.JLabel minWaitLbl;
  javax.swing.JSpinner minWaitSpinner;
  javax.swing.JPanel namePanel;
  javax.swing.JPanel propPanel;
  javax.swing.JTabbedPane propertiesTP;
  javax.swing.JCheckBox randomWaitCB;
  javax.swing.JButton reverseArrivalBtn;
  javax.swing.JButton saveExitBtn;
  javax.swing.JToggleButton startLocButton;
  javax.swing.JPanel waitPanel;
  javax.swing.JPanel waitPropPanel;
  // End of variables declaration//GEN-END:variables
}
