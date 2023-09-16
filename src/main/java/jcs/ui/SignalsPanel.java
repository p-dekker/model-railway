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
package jcs.ui;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import jcs.entities.AccessoryBean;
import jcs.controller.ControllerFactory;
import jcs.ui.widgets.SignalRowPanel;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs
 */
public class SignalsPanel extends javax.swing.JPanel {

  /**
   * Creates new form SignalsPanel
   */
  public SignalsPanel() {
    initComponents();
    initSignalPanels();
  }

  private void initSignalPanels() {
    refreshPanel();
  }

  public void refreshPanel() {
    if (ControllerFactory.getController() == null) {
      return;
    }
    //stub
    List<AccessoryBean> signals = Collections.EMPTY_LIST; // ControllerFactory.getTrackService().getSignals();

    Logger.trace("There are " + signals.size() + " signals...");

    this.removeAll();
    int maxW = this.getPreferredSize().width;
    int w = 0, rows = 1, cols = 0;

    for (AccessoryBean signal : signals) {
      SignalRowPanel signalRowPanel = new SignalRowPanel(signal, SignalRowPanel.Y_AXIS);
      signalRowPanel.requestFocusInWindow();
      w = w + signalRowPanel.getPreferredSize().width;
      if (rows == 1) {
        cols++;
      }
      if (w > maxW) {
        //next row
        rows++;
        w = 0;
      }

      this.add(signalRowPanel);
      ControllerFactory.getController().addAccessoryEventListener(signalRowPanel);
    }
    GridLayout gl = new GridLayout(rows, cols);
    this.setLayout(gl);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 805));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });
        setLayout(new java.awt.GridLayout(1, 0));
    }// </editor-fold>//GEN-END:initComponents

  private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    Logger.debug("keyCode: " + evt.getKeyCode() + " paramString: " + evt.paramString() + "" + evt.getExtendedKeyCode());
  }//GEN-LAST:event_formKeyPressed

  private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
    Logger.debug("keyCode: " + evt.getKeyCode() + " paramString: " + evt.paramString() + "" + evt.getExtendedKeyCode());
  }//GEN-LAST:event_formKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
