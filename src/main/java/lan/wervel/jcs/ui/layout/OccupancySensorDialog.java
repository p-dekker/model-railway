/*
 * Copyright (C) 2020 fransjacobs.
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
package lan.wervel.jcs.ui.layout;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import lan.wervel.jcs.entities.FeedbackModule;
import lan.wervel.jcs.entities.LayoutTile;
import lan.wervel.jcs.trackservice.TrackServiceFactory;
import lan.wervel.jcs.ui.layout.tiles.FeedbackPort;
import lan.wervel.jcs.ui.layout.tiles.OccupancyDetector;
import org.pmw.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class OccupancySensorDialog extends javax.swing.JDialog {

    private final OccupancyDetector tile;

    public OccupancySensorDialog(java.awt.Frame parent, boolean modal, OccupancyDetector tile) {
        super(parent, modal);
        this.tile = tile;

        initComponents();
        postInit();
    }

    private void postInit() {
        LayoutTile lt = tile.getLayoutTile();
        if (lt.getFeedbackModule() != null) {
            FeedbackModule fm = lt.getFeedbackModule();
            Integer port = lt.getPort();
            if (port > 0) {
                this.portSpinner.setValue(port);
            }
            this.feedbackModuleCB.setSelectedItem(fm);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/media/jcs-train-64.png")));
        //setLocationRelativeTo(null);
        this.setLocation(tile.getCenter());
    }

    private ComboBoxModel<FeedbackModule> getFeedbackModuleComboBoxModel() {
        ComboBoxModel feedbackCBM;
        if (TrackServiceFactory.getTrackService() != null) {
            List<FeedbackModule> fbml = new ArrayList<>();
            FeedbackModule def = new FeedbackModule();
            def.setName("-");
            fbml.add(def);
            fbml.addAll(TrackServiceFactory.getTrackService().getFeedbackModules());

            FeedbackModule[] modules = new FeedbackModule[fbml.size()];
            fbml.toArray(modules);

            feedbackCBM = new DefaultComboBoxModel<>(modules);
        } else {
            feedbackCBM = new DefaultComboBoxModel<>();
        }
        return feedbackCBM;
    }

    private void setModuleAndPort() {
        FeedbackModule fm = (FeedbackModule) this.feedbackModuleCB.getSelectedItem();
        Integer port = (Integer) this.portSpinner.getValue();

        if (this.tile.getLayoutTile() != null && fm != null) {
            if ("-".equals(fm.getName())) {
                this.tile.getLayoutTile().setFeedbackModule(null);
            } else {
                if (port > 0) {
                    Logger.trace("Setting FeedbackModule " + fm + " and Port " + port);
                    this.tile.getLayoutTile().setFeedbackModuleAndPort(fm, port);
                    this.contactIdLbl.setText("Contact#: " + fm.getContactId(port));
                } else {
                    Logger.trace("Setting FeedbackModule " + fm);
                    this.tile.getLayoutTile().setFeedbackModuleAndPort(fm, null);
                    this.contactIdLbl.setText("Contact#: -");
                }
            }
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

        jPanel1 = new javax.swing.JPanel();
        selectLbl = new javax.swing.JLabel();
        feedbackModuleCB = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        portSpinner = new javax.swing.JSpinner();
        contactIdLbl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        closeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        selectLbl.setText("Module:");
        selectLbl.setPreferredSize(new java.awt.Dimension(80, 16));
        jPanel1.add(selectLbl);

        feedbackModuleCB.setModel(getFeedbackModuleComboBoxModel());
        feedbackModuleCB.setPreferredSize(new java.awt.Dimension(150, 27));
        feedbackModuleCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedbackModuleCBActionPerformed(evt);
            }
        });
        jPanel1.add(feedbackModuleCB);

        getContentPane().add(jPanel1);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Port:");
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 16));
        jPanel3.add(jLabel1);

        portSpinner.setModel(new javax.swing.SpinnerNumberModel(0, null, 16, 1));
        portSpinner.setPreferredSize(new java.awt.Dimension(50, 26));
        portSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                portSpinnerStateChanged(evt);
            }
        });
        jPanel3.add(portSpinner);

        contactIdLbl.setText("Contact #:");
        jPanel3.add(contactIdLbl);

        getContentPane().add(jPanel3);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel2.setLayout(flowLayout1);

        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        jPanel2.add(closeBtn);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void feedbackModuleCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedbackModuleCBActionPerformed
        setModuleAndPort();
    }//GEN-LAST:event_feedbackModuleCBActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed

    private void portSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_portSpinnerStateChanged
        setModuleAndPort();
    }//GEN-LAST:event_portSpinnerStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeBtn;
    private javax.swing.JLabel contactIdLbl;
    private javax.swing.JComboBox<FeedbackModule> feedbackModuleCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner portSpinner;
    private javax.swing.JLabel selectLbl;
    // End of variables declaration//GEN-END:variables
}
