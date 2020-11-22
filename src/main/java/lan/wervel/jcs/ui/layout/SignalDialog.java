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
import lan.wervel.jcs.entities.LayoutTile;
import lan.wervel.jcs.entities.Signal;
import lan.wervel.jcs.trackservice.TrackServiceFactory;
import lan.wervel.jcs.ui.layout.tiles.SignalTile;
import org.pmw.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class SignalDialog extends javax.swing.JDialog {

    private final SignalTile tile;

    public SignalDialog(java.awt.Frame parent, boolean modal, SignalTile tile) {
        super(parent, modal);
        this.tile = tile;

        initComponents();
        postInit();
    }

    private void postInit() {
        LayoutTile lt = tile.getLayoutTile();
        if (lt.getSignal() != null) {
            Signal s = lt.getSignal();
            this.signalCB.setSelectedItem(s);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/media/jcs-train-64.png")));
        //setLocationRelativeTo(null);
        this.setLocation(tile.getCenter());
    }

    private ComboBoxModel<Signal> getSignalComboBoxModel() {
        ComboBoxModel signalCBM;
        if (TrackServiceFactory.getTrackService() != null) {
            List<Signal> sl = new ArrayList<>();
            Signal def = new Signal();
            def.setName("-");
            sl.add(def);
            sl.addAll(TrackServiceFactory.getTrackService().getSignals());

            Signal[] signals = new Signal[sl.size()];
            sl.toArray(signals);

            signalCBM = new DefaultComboBoxModel<>(signals);
        } else {
            signalCBM = new DefaultComboBoxModel<>();
        }
        return signalCBM;
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
        signalCB = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        closeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        selectLbl.setText("Signal:");
        selectLbl.setPreferredSize(new java.awt.Dimension(80, 16));
        jPanel1.add(selectLbl);

        signalCB.setModel(getSignalComboBoxModel());
        signalCB.setPreferredSize(new java.awt.Dimension(150, 27));
        signalCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signalCBActionPerformed(evt);
            }
        });
        jPanel1.add(signalCB);

        getContentPane().add(jPanel1);

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

    private void signalCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signalCBActionPerformed
        Signal s = (Signal) this.signalCB.getSelectedItem();
        if (this.tile.getLayoutTile() != null && s != null) {
            Logger.trace("Setting signal " + s);
            if ("-".equals(s.getName())) {
                tile.getLayoutTile().setSolenoidAccessoiry(null);
            } else {
                tile.getLayoutTile().setSolenoidAccessoiry(s);
            }
        }
    }//GEN-LAST:event_signalCBActionPerformed

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel selectLbl;
    private javax.swing.JComboBox<Signal> signalCB;
    // End of variables declaration//GEN-END:variables
}
