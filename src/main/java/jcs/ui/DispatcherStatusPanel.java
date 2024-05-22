/*
 * Copyright 2024 FJA.
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
package jcs.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.tinylog.Logger;

/**
 *
 * @author FJA
 */
public class DispatcherStatusPanel extends javax.swing.JPanel {

  /**
   * Creates new form DispatcherStatusPanel
   */
  public DispatcherStatusPanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabsPane = new javax.swing.JTabbedPane();
    locomotiveTablePanel = new jcs.ui.table.LocomotiveTablePanel();
    dispatcherPanel = new javax.swing.JPanel();
    dispatcherSP = new javax.swing.JScrollPane();
    dispatcherTable = new javax.swing.JTable();

    setLayout(new java.awt.BorderLayout());

    tabsPane.setPreferredSize(new java.awt.Dimension(300, 800));
    tabsPane.addTab("Locomotives", locomotiveTablePanel);

    dispatcherPanel.setLayout(new java.awt.BorderLayout());

    dispatcherTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    dispatcherSP.setViewportView(dispatcherTable);

    dispatcherPanel.add(dispatcherSP, java.awt.BorderLayout.CENTER);

    tabsPane.addTab("Dispatcher", dispatcherPanel);

    add(tabsPane, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel dispatcherPanel;
  private javax.swing.JScrollPane dispatcherSP;
  private javax.swing.JTable dispatcherTable;
  private jcs.ui.table.LocomotiveTablePanel locomotiveTablePanel;
  private javax.swing.JTabbedPane tabsPane;
  // End of variables declaration//GEN-END:variables

//For standalone testing only
  public static void main(String args[]) {
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.error(ex);
    }

    java.awt.EventQueue.invokeLater(() -> {
      JFrame f = new JFrame("DispatcherPanel Tester");
      DispatcherStatusPanel dispatcherTestPanel = new DispatcherStatusPanel();
      f.add(dispatcherTestPanel);

      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.pack();

      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
      f.setVisible(true);
    });
  }  
  
  
}
