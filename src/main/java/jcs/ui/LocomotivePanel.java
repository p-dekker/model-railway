/*
 * Copyright (C) 2022 fransjacobs.
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
package jcs.ui;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jcs.entities.LocomotiveBean;
import jcs.entities.enums.Direction;
import jcs.trackservice.TrackServiceFactory;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class LocomotivePanel extends javax.swing.JPanel {

    public LocomotivePanel() {
        initComponents();
    }

    public void loadLocomotives() {
        if (TrackServiceFactory.getTrackService() != null) {
            List<LocomotiveBean> locos = TrackServiceFactory.getTrackService().getLocomotives();
            ListModel<LocomotiveBean> listModel = new DefaultListModel<>();
            ((DefaultListModel) listModel).addAll(locos);
            this.locoList.setModel(listModel);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        directionBG = new javax.swing.ButtonGroup();
        locIdPanel = new javax.swing.JPanel();
        locNamePanel = new javax.swing.JPanel();
        iconLbl = new javax.swing.JLabel();
        nameLbL = new javax.swing.JLabel();
        velocityBtnPanel = new javax.swing.JPanel();
        speedBtn1 = new javax.swing.JButton();
        speedBtn2 = new javax.swing.JButton();
        speedBtn3 = new javax.swing.JButton();
        directionPanel = new javax.swing.JPanel();
        backForwPanel = new javax.swing.JPanel();
        backwardsBtn = new javax.swing.JToggleButton();
        forwardsBtn = new javax.swing.JToggleButton();
        stopPanel = new javax.swing.JPanel();
        stopBtn = new javax.swing.JButton();
        funcSpeedPanel = new javax.swing.JPanel();
        tachoPanel = new javax.swing.JPanel();
        velocityLbl = new javax.swing.JLabel();
        maxTachoLbl = new javax.swing.JLabel();
        functionsPanel = new jcs.ui.widgets.FunctionsPanel();
        speedPanel = new javax.swing.JPanel();
        velocitySlider = new javax.swing.JSlider();
        locSelectionPanel = new javax.swing.JPanel();
        locoScrollPane = new javax.swing.JScrollPane();
        locoList = new javax.swing.JList<>();

        setMinimumSize(new java.awt.Dimension(240, 600));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(220, 600));
        setLayout(new java.awt.BorderLayout());

        locIdPanel.setName("locIdPanel"); // NOI18N
        locIdPanel.setPreferredSize(new java.awt.Dimension(130, 214));
        locIdPanel.setLayout(new javax.swing.BoxLayout(locIdPanel, javax.swing.BoxLayout.Y_AXIS));

        locNamePanel.setName("locNamePanel"); // NOI18N
        locNamePanel.setPreferredSize(new java.awt.Dimension(120, 80));
        locNamePanel.setLayout(new java.awt.GridLayout(2, 1, 2, 0));

        iconLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLbl.setText("icon");
        iconLbl.setDoubleBuffered(true);
        iconLbl.setName("iconLbl"); // NOI18N
        iconLbl.setPreferredSize(new java.awt.Dimension(120, 60));
        locNamePanel.add(iconLbl);

        nameLbL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLbL.setText("name");
        nameLbL.setName("nameLbL"); // NOI18N
        nameLbL.setPreferredSize(new java.awt.Dimension(120, 16));
        locNamePanel.add(nameLbL);

        locIdPanel.add(locNamePanel);

        velocityBtnPanel.setName("velocityBtnPanel"); // NOI18N
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 2);
        flowLayout2.setAlignOnBaseline(true);
        velocityBtnPanel.setLayout(flowLayout2);

        speedBtn1.setText("1");
        speedBtn1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        speedBtn1.setMaximumSize(new java.awt.Dimension(40, 40));
        speedBtn1.setMinimumSize(new java.awt.Dimension(40, 40));
        speedBtn1.setName("speedBtn1"); // NOI18N
        speedBtn1.setPreferredSize(new java.awt.Dimension(40, 40));
        speedBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedBtn1ActionPerformed(evt);
            }
        });
        velocityBtnPanel.add(speedBtn1);

        speedBtn2.setText("2");
        speedBtn2.setToolTipText("");
        speedBtn2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        speedBtn2.setMaximumSize(new java.awt.Dimension(40, 40));
        speedBtn2.setMinimumSize(new java.awt.Dimension(40, 40));
        speedBtn2.setName("speedBtn2"); // NOI18N
        speedBtn2.setPreferredSize(new java.awt.Dimension(40, 40));
        speedBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedBtn2ActionPerformed(evt);
            }
        });
        velocityBtnPanel.add(speedBtn2);

        speedBtn3.setText("3");
        speedBtn3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        speedBtn3.setMaximumSize(new java.awt.Dimension(40, 40));
        speedBtn3.setMinimumSize(new java.awt.Dimension(40, 40));
        speedBtn3.setName("speedBtn3"); // NOI18N
        speedBtn3.setPreferredSize(new java.awt.Dimension(40, 40));
        speedBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speedBtn3ActionPerformed(evt);
            }
        });
        velocityBtnPanel.add(speedBtn3);

        locIdPanel.add(velocityBtnPanel);

        directionPanel.setName("directionPanel"); // NOI18N
        directionPanel.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        backForwPanel.setName("backForwPanel"); // NOI18N
        java.awt.FlowLayout flowLayout4 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 2);
        flowLayout4.setAlignOnBaseline(true);
        backForwPanel.setLayout(flowLayout4);

        directionBG.add(backwardsBtn);
        backwardsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/left-24.png"))); // NOI18N
        backwardsBtn.setActionCommand("Backwards");
        backwardsBtn.setName("backwardsBtn"); // NOI18N
        backwardsBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/left-Y-24.png"))); // NOI18N
        backwardsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backwardsBtnActionPerformed(evt);
            }
        });
        backForwPanel.add(backwardsBtn);

        directionBG.add(forwardsBtn);
        forwardsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/right-24.png"))); // NOI18N
        forwardsBtn.setSelected(true);
        forwardsBtn.setActionCommand("Forwards");
        forwardsBtn.setName("forwardsBtn"); // NOI18N
        forwardsBtn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/right-Y-24.png"))); // NOI18N
        forwardsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardsBtnActionPerformed(evt);
            }
        });
        backForwPanel.add(forwardsBtn);

        directionPanel.add(backForwPanel);

        stopPanel.setName("stopPanel"); // NOI18N
        java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
        flowLayout3.setAlignOnBaseline(true);
        stopPanel.setLayout(flowLayout3);

        stopBtn.setText("Stop");
        stopBtn.setMaximumSize(new java.awt.Dimension(100, 40));
        stopBtn.setMinimumSize(new java.awt.Dimension(100, 40));
        stopBtn.setName("stopBtn"); // NOI18N
        stopBtn.setPreferredSize(new java.awt.Dimension(100, 40));
        stopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopBtnActionPerformed(evt);
            }
        });
        stopPanel.add(stopBtn);

        directionPanel.add(stopPanel);

        locIdPanel.add(directionPanel);

        add(locIdPanel, java.awt.BorderLayout.NORTH);

        funcSpeedPanel.setMinimumSize(new java.awt.Dimension(240, 205));
        funcSpeedPanel.setName("funcSpeedPanel"); // NOI18N
        funcSpeedPanel.setPreferredSize(new java.awt.Dimension(235, 215));
        funcSpeedPanel.setLayout(new java.awt.BorderLayout());

        tachoPanel.setName("tachoPanel"); // NOI18N
        java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0);
        flowLayout5.setAlignOnBaseline(true);
        tachoPanel.setLayout(flowLayout5);

        velocityLbl.setText("0%");
        velocityLbl.setMaximumSize(new java.awt.Dimension(110, 16));
        velocityLbl.setMinimumSize(new java.awt.Dimension(110, 16));
        velocityLbl.setName("velocityLbl"); // NOI18N
        velocityLbl.setPreferredSize(new java.awt.Dimension(110, 16));
        tachoPanel.add(velocityLbl);

        maxTachoLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maxTachoLbl.setText("100");
        maxTachoLbl.setMinimumSize(new java.awt.Dimension(45, 16));
        maxTachoLbl.setName("maxTachoLbl"); // NOI18N
        maxTachoLbl.setPreferredSize(new java.awt.Dimension(45, 16));
        tachoPanel.add(maxTachoLbl);

        funcSpeedPanel.add(tachoPanel, java.awt.BorderLayout.NORTH);

        functionsPanel.setName("functionsPanel"); // NOI18N
        funcSpeedPanel.add(functionsPanel, java.awt.BorderLayout.CENTER);

        speedPanel.setName("speedPanel"); // NOI18N
        speedPanel.setLayout(new java.awt.GridLayout());

        velocitySlider.setMinorTickSpacing(10);
        velocitySlider.setOrientation(javax.swing.JSlider.VERTICAL);
        velocitySlider.setPaintLabels(true);
        velocitySlider.setPaintTicks(true);
        velocitySlider.setValue(0);
        velocitySlider.setDoubleBuffered(true);
        velocitySlider.setName("velocitySlider"); // NOI18N
        velocitySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                velocitySliderStateChanged(evt);
            }
        });
        speedPanel.add(velocitySlider);

        funcSpeedPanel.add(speedPanel, java.awt.BorderLayout.EAST);

        add(funcSpeedPanel, java.awt.BorderLayout.CENTER);

        locSelectionPanel.setName("locSelectionPanel"); // NOI18N
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 2);
        flowLayout1.setAlignOnBaseline(true);
        locSelectionPanel.setLayout(flowLayout1);

        locoScrollPane.setName("locoScrollPane"); // NOI18N
        locoScrollPane.setPreferredSize(new java.awt.Dimension(220, 110));

        locoList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        locoList.setDoubleBuffered(true);
        locoList.setName("locoList"); // NOI18N
        locoList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                locoListValueChanged(evt);
            }
        });
        locoScrollPane.setViewportView(locoList);

        locSelectionPanel.add(locoScrollPane);

        add(locSelectionPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void locoListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_locoListValueChanged

        if (!evt.getValueIsAdjusting()) {
            if (this.locoList.getSelectedIndex() != -1) {
                LocomotiveBean selected = this.locoList.getSelectedValue();
                this.functionsPanel.setLocomotive(selected);
                this.nameLbL.setText(selected.getName());
                this.iconLbl.setIcon(new ImageIcon(selected.getLocIcon()));
                this.velocitySlider.setMaximum(selected.getTachoMax());
                this.maxTachoLbl.setText(selected.getTachoMax() + "");
                this.velocitySlider.setValue(selected.getVelocity());

                Direction d = selected.getDirection();
                if (Direction.BACKWARDS.equals(d)) {
                    this.backwardsBtn.setSelected(true);
                } else {
                    this.forwardsBtn.setSelected(true);
                }

                iconLbl.setText("");
            }
        }
    }//GEN-LAST:event_locoListValueChanged

    private void velocitySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_velocitySliderStateChanged
        JSlider slider = (JSlider) evt.getSource();
        if (!slider.getValueIsAdjusting()) {
            LocomotiveBean selected = this.locoList.getSelectedValue();
            if (selected != null) {
                int max = selected.getTachoMax();
                double value = slider.getValue();
                int velocity = (int) Math.round(value / max * 100);
                this.velocityLbl.setText(velocity + "%");
                Logger.trace("Slider value: " + value + " velocity: " + velocity);

                if (TrackServiceFactory.getTrackService() != null) {
                    TrackServiceFactory.getTrackService().changeVelocity(velocity, selected);
                }
            }
        }
    }//GEN-LAST:event_velocitySliderStateChanged

    private void speedBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speedBtn1ActionPerformed
        LocomotiveBean selected = this.locoList.getSelectedValue();
        if (selected != null) {
            int tachoMax = selected.getTachoMax();
            this.velocitySlider.setValue(tachoMax / 4);
        }
    }//GEN-LAST:event_speedBtn1ActionPerformed

    private void speedBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speedBtn2ActionPerformed
        LocomotiveBean selected = this.locoList.getSelectedValue();
        if (selected != null) {
            int tachoMax = selected.getTachoMax();
            this.velocitySlider.setValue(tachoMax / 2);
        }
    }//GEN-LAST:event_speedBtn2ActionPerformed

    private void speedBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speedBtn3ActionPerformed
        LocomotiveBean selected = this.locoList.getSelectedValue();
        if (selected != null) {
            int tachoMax = selected.getTachoMax();
            this.velocitySlider.setValue((tachoMax / 4) * 3);
        }
    }//GEN-LAST:event_speedBtn3ActionPerformed

    private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBtnActionPerformed
        this.velocitySlider.setValue(0);
    }//GEN-LAST:event_stopBtnActionPerformed

    private void changeDirection(Direction direction) {
        LocomotiveBean locomotive = this.locoList.getSelectedValue();
        if (TrackServiceFactory.getTrackService() != null && locomotive != null) {
            TrackServiceFactory.getTrackService().changeDirection(direction, locomotive);
        }
    }

    private void forwardsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardsBtnActionPerformed
        changeDirection(Direction.get(evt.getActionCommand()));
    }//GEN-LAST:event_forwardsBtnActionPerformed

    private void backwardsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backwardsBtnActionPerformed
        changeDirection(Direction.get(evt.getActionCommand()));
    }//GEN-LAST:event_backwardsBtnActionPerformed

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.error("Can't set the LookAndFeel: " + ex);
        }
        java.awt.EventQueue.invokeLater(() -> {

            LocomotivePanel testPanel = new LocomotivePanel();
            JFrame testFrame = new JFrame("FunctionsPanel Tester");

            testFrame.add(testPanel);

            testFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            testFrame.pack();
            testFrame.setLocationRelativeTo(null);

            testPanel.loadLocomotives();
            testFrame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backForwPanel;
    private javax.swing.JToggleButton backwardsBtn;
    private javax.swing.ButtonGroup directionBG;
    private javax.swing.JPanel directionPanel;
    private javax.swing.JToggleButton forwardsBtn;
    private javax.swing.JPanel funcSpeedPanel;
    private jcs.ui.widgets.FunctionsPanel functionsPanel;
    private javax.swing.JLabel iconLbl;
    private javax.swing.JPanel locIdPanel;
    private javax.swing.JPanel locNamePanel;
    private javax.swing.JPanel locSelectionPanel;
    private javax.swing.JList<LocomotiveBean> locoList;
    private javax.swing.JScrollPane locoScrollPane;
    private javax.swing.JLabel maxTachoLbl;
    private javax.swing.JLabel nameLbL;
    private javax.swing.JButton speedBtn1;
    private javax.swing.JButton speedBtn2;
    private javax.swing.JButton speedBtn3;
    private javax.swing.JPanel speedPanel;
    private javax.swing.JButton stopBtn;
    private javax.swing.JPanel stopPanel;
    private javax.swing.JPanel tachoPanel;
    private javax.swing.JPanel velocityBtnPanel;
    private javax.swing.JLabel velocityLbl;
    private javax.swing.JSlider velocitySlider;
    // End of variables declaration//GEN-END:variables
}
