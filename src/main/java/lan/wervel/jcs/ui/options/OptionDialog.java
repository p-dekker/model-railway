/*
 * Copyright (C) 2019 frans.
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
package lan.wervel.jcs.ui.options;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

/**
 *
 * @author frans
 */
public class OptionDialog extends javax.swing.JDialog {

  /**
   * Creates new form NewJDialog
   *
   * @param parent
   * @param modal
   */
  public OptionDialog(Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    init();
  }

  private void init() {
    this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/media/jcs-train-64.png")));
    setLocationRelativeTo(null);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
   * method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new JPanel();
        centerPanel = new JPanel();
        prefsTP = new JTabbedPane();
        southPanel = new JPanel();
        closeBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Options");
        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(1024, 750));
        setName("Options"); // NOI18N
        setPreferredSize(new Dimension(1024, 750));

        topPanel.setMinimumSize(new Dimension(1024, 20));
        topPanel.setName("topPanel"); // NOI18N
        topPanel.setPreferredSize(new Dimension(1024, 20));
        getContentPane().add(topPanel, BorderLayout.PAGE_START);

        centerPanel.setMinimumSize(new Dimension(1021, 750));
        centerPanel.setName("centerPanel"); // NOI18N
        centerPanel.setLayout(new BorderLayout());

        prefsTP.setName("prefsTP"); // NOI18N
        prefsTP.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                prefsTPStateChanged(evt);
            }
        });
        centerPanel.add(prefsTP, BorderLayout.CENTER);
        prefsTP.getAccessibleContext().setAccessibleName("Locomotives");

        getContentPane().add(centerPanel, BorderLayout.CENTER);

        southPanel.setName("southPanel"); // NOI18N
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        closeBtn.setIcon(new ImageIcon(getClass().getResource("/media/exit-24.png"))); // NOI18N
        closeBtn.setText("Close");
        closeBtn.setMaximumSize(new Dimension(100, 36));
        closeBtn.setMinimumSize(new Dimension(100, 36));
        closeBtn.setName("closeBtn"); // NOI18N
        closeBtn.setPreferredSize(new Dimension(100, 36));
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        southPanel.add(closeBtn);

        getContentPane().add(southPanel, BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void closeBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
    this.setVisible(false);
    this.dispose();

  }//GEN-LAST:event_closeBtnActionPerformed

  private void prefsTPStateChanged(ChangeEvent evt) {//GEN-FIRST:event_prefsTPStateChanged
    Component c = this.prefsTP.getSelectedComponent();

    if (c instanceof LocomotivePanel) {
      //this.locoPanel.refresh();
    } else if (c instanceof TurnoutPanel) {
      //this.turnoutPanel.refresh();
    } else if (c instanceof SignalPanel) {
      //this.signalPanel.refresh();
    } else if (c instanceof FeedbackModulePanel) {
      //this.feedbackModulePanel.refresh();
    } else if (c instanceof PropertiesPanel) {
      //this.propertiesPanel.refresh();
    }

    Logger.debug("Refreshed " + (c != null ? c.getName() : ""));
  }//GEN-LAST:event_prefsTPStateChanged

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    Configurator.defaultConfig().level(org.pmw.tinylog.Level.DEBUG).activate();

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.warn("Can't set the LookAndFeel: " + ex);
    }

    java.awt.EventQueue.invokeLater(() -> {
      OptionDialog dialog = new OptionDialog(new javax.swing.JFrame(), true);
      dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          System.exit(0);
        }
      });
      dialog.setVisible(true);
    });
  }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel centerPanel;
    private JButton closeBtn;
    private JTabbedPane prefsTP;
    private JPanel southPanel;
    private JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
