/*
 * Copyright 2024 fransjacobs.
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
package jcs.commandStation.autopilot;

import java.util.List;
import jcs.commandStation.autopilot.state.TrainDispatcher;
import jcs.entities.RouteBean;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class DispatcherTestDialog extends javax.swing.JDialog {
  
  private final TrainDispatcher dispatcher;

  /**
   * Creates new form DispatcherTestDialog
   * @param parent
   * @param modal
   * @param dispatcher
   */
  public DispatcherTestDialog(java.awt.Frame parent, boolean modal, TrainDispatcher dispatcher) {
    super(parent, modal);
    this.dispatcher = dispatcher;
    initComponents();
    if (this.dispatcher != null) {
      this.setTitle(dispatcher.getName());
      this.stateLabel.setText(dispatcher.getState().toString());
    } else {
      this.setTitle("No TrainDispatcher set");
      this.stateLabel.setText("No TrainDispatcher set");
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    northPanel = new javax.swing.JPanel();
    statePanel = new javax.swing.JPanel();
    stateLabel = new javax.swing.JLabel();
    buttenPanel = new javax.swing.JPanel();
    previousButton = new javax.swing.JButton();
    actionButton = new javax.swing.JButton();
    nextButton = new javax.swing.JButton();
    unlockButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    northPanel.setLayout(new jcs.ui.swing.layout.VerticalFlowLayout());

    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout1.setAlignOnBaseline(true);
    statePanel.setLayout(flowLayout1);

    stateLabel.setText("State");
    statePanel.add(stateLabel);

    northPanel.add(statePanel);

    previousButton.setLabel("Previous State");
    previousButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        previousButtonActionPerformed(evt);
      }
    });
    buttenPanel.add(previousButton);

    actionButton.setLabel("Perform Action");
    actionButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        actionButtonActionPerformed(evt);
      }
    });
    buttenPanel.add(actionButton);

    nextButton.setLabel("Next State");
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nextButtonActionPerformed(evt);
      }
    });
    buttenPanel.add(nextButton);

    unlockButton.setLabel("Unlock Routes");
    unlockButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        unlockButtonActionPerformed(evt);
      }
    });
    buttenPanel.add(unlockButton);

    northPanel.add(buttenPanel);

    getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
    if (this.dispatcher != null) {
      dispatcher.previousState();
      this.stateLabel.setText(dispatcher.getState().toString());
    }
  }//GEN-LAST:event_previousButtonActionPerformed

  private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
    if (this.dispatcher != null) {
      dispatcher.nextState();
      this.stateLabel.setText(dispatcher.getState().toString());
    }
  }//GEN-LAST:event_nextButtonActionPerformed

  private void actionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonActionPerformed
    if (this.dispatcher != null) {
      dispatcher.performAction();
    }
  }//GEN-LAST:event_actionButtonActionPerformed

  private void unlockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unlockButtonActionPerformed
    List<RouteBean> routes = PersistenceFactory.getService().getRoutes();
    int lockedCounter = 0;
    for (RouteBean route : routes) {
      if (route.isLocked()) {
        route.setLocked(false);
        PersistenceFactory.getService().persist(route);
        lockedCounter++;
      }
    }
    Logger.debug("Unlocked " + lockedCounter + " routes out of " + routes.size());
  }//GEN-LAST:event_unlockButtonActionPerformed
  
  public static void showDialog(TrainDispatcher dispatcher) {
    
    java.awt.EventQueue.invokeLater(() -> {
      DispatcherTestDialog dialog = new DispatcherTestDialog(new javax.swing.JFrame(), false, dispatcher);
      dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          //System.exit(0);
          dialog.dispose();
        }
      });
      dialog.setLocationRelativeTo(null);
      dialog.pack();
      dialog.setVisible(true);
    });
    
  }

  /**
   * @param args the command line arguments
   */
//  public static void main(String args[]) {
//    try {
//      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
//    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//      Logger.warn("Can't set the LookAndFeel: " + ex);
//    }
//
//    /* Create and display the dialog */
//    java.awt.EventQueue.invokeLater(new Runnable() {
//      public void run() {
//        DispatcherTestDialog dialog = new DispatcherTestDialog(new javax.swing.JFrame(), true, null);
//        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//          @Override
//          public void windowClosing(java.awt.event.WindowEvent e) {
//            System.exit(0);
//          }
//        });
//        dialog.setLocationRelativeTo(null);
//        dialog.pack();
//        dialog.setVisible(true);
//      }
//    });
//  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton actionButton;
  private javax.swing.JPanel buttenPanel;
  private javax.swing.JButton nextButton;
  private javax.swing.JPanel northPanel;
  private javax.swing.JButton previousButton;
  private javax.swing.JLabel stateLabel;
  private javax.swing.JPanel statePanel;
  private javax.swing.JButton unlockButton;
  // End of variables declaration//GEN-END:variables
}
