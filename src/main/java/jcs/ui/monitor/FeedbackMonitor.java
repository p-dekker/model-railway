/*
 * Copyright (C) 2022 frans.
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
package jcs.ui.monitor;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import jcs.trackservice.TrackServiceFactory;
import jcs.ui.options.table.SensorTableModel;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class FeedbackMonitor extends javax.swing.JFrame {

    private final SensorTableModel sensorTableModel;

    /**
     * Creates new form FeedbackMonitor
     */
    public FeedbackMonitor() {
        sensorTableModel = new SensorTableModel();
        initComponents();
        alignSensorTable();
    }

    private void alignSensorTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        this.sensorTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        this.sensorTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        this.sensorTable.getColumnModel().getColumn(1).setPreferredWidth(50);

        this.sensorTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        this.sensorTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        this.sensorTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        this.sensorTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        this.sensorTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        this.sensorTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        this.sensorTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        this.sensorTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        this.sensorTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        this.sensorTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
    }

    public void showMonitor() {
        this.sensorTableModel.clear();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sensorTableSP = new javax.swing.JScrollPane();
        sensorTable = new javax.swing.JTable();
        topPanel = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        mainPanel.setPreferredSize(new java.awt.Dimension(750, 300));

        sensorTableSP.setPreferredSize(new java.awt.Dimension(750, 345));

        sensorTable.setModel(sensorTableModel);
        sensorTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sensorTable.setDoubleBuffered(true);
        sensorTable.setFillsViewportHeight(true);
        sensorTable.setGridColor(new java.awt.Color(204, 204, 204));
        sensorTable.setName(""); // NOI18N
        sensorTable.setRowHeight(18);
        sensorTable.setShowGrid(true);
        sensorTable.setShowHorizontalLines(true);
        sensorTableSP.setViewportView(sensorTable);

        mainPanel.add(sensorTableSP);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        topPanel.setLayout(flowLayout1);

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/delete-24.png"))); // NOI18N
        clearButton.setToolTipText("Clear");
        clearButton.setMaximumSize(new java.awt.Dimension(40, 40));
        clearButton.setMinimumSize(new java.awt.Dimension(40, 40));
        clearButton.setPreferredSize(new java.awt.Dimension(40, 40));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        topPanel.add(clearButton);

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        sensorTableModel.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (TrackServiceFactory.getTrackService() != null) {
            TrackServiceFactory.getTrackService().removeSensorListener(sensorTableModel);
            Logger.trace("Removed sensor listener");
        }
        this.sensorTableModel.clear();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (TrackServiceFactory.getTrackService() != null) {
            TrackServiceFactory.getTrackService().addSensorListener(sensorTableModel);
            Logger.trace("Added sensor listener");
        }
    }//GEN-LAST:event_formWindowActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable sensorTable;
    private javax.swing.JScrollPane sensorTableSP;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
