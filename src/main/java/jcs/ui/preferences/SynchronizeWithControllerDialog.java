/*
 * Copyright 2023 frans.
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
package jcs.ui.preferences;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jcs.JCS;
import jcs.trackservice.TrackControllerFactory;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class SynchronizeWithControllerDialog extends JDialog {

  private final Timer iconAnimator;
  private int counter;

  /**
   *
   * @param component
   * @param modal
   */
  public SynchronizeWithControllerDialog(Component component, boolean modal) {
    super(getFrame(component), modal);
    initComponents();

    this.animatedIconLbl.setIcon(new AnimatedTurningIcon());
    setLocationRelativeTo(null);
    setIcon();
    this.pack();

    int delay = 250;
    ActionListener taskPerformer = (ActionEvent evt) -> {
      animateIcon();
    };

    iconAnimator = new Timer(delay, taskPerformer);
    AnimatedTurningIcon ati = (AnimatedTurningIcon) this.animatedIconLbl.getIcon();
    ati.setRunning(true);
    iconAnimator.start();
  }

  public void appendText(String str) {
    this.progressMessageTA.append(str);
    progressMessageTA.setCaretPosition(progressMessageTA.getDocument().getLength());
  }

  private void animateIcon() {
    AnimatedTurningIcon ati = (AnimatedTurningIcon) this.animatedIconLbl.getIcon();
    ati.next();
    this.animatedIconLbl.repaint();
  }

  private void setIcon() {
    URL iconUrl = JCS.class.getResource("/media/jcs-train-64.png");
    if (iconUrl != null) {
      setIconImage(new ImageIcon(iconUrl).getImage());
    }
  }

  private static Frame getFrame(Component component) {
    if (component == null) {
      return new javax.swing.JFrame();
    }

    Container p = component.getParent();
    while (p != null) {
      if (p instanceof Frame) {
        return (Frame) p;
      }
      p = p.getParent();
    }
    return new javax.swing.JFrame();
  }

  @Override
  public void setVisible(boolean b) {
    if (b) {
      counter = 0;
      SynchronizeTask st = new SynchronizeTask(this.synchProgressBar, this.progressMessageTA);
      AnimatedTurningIcon ati = (AnimatedTurningIcon) this.animatedIconLbl.getIcon();
      ati.setRunning(b);
      st.execute();
    }
    super.setVisible(b);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
   * method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    topPanel = new javax.swing.JPanel();
    animatedIconLbl = new javax.swing.JLabel();
    mainSP = new javax.swing.JScrollPane();
    progressMessageTA = new javax.swing.JTextArea();
    statusPanel = new javax.swing.JPanel();
    progressPanel = new javax.swing.JPanel();
    synchProgressBar = new javax.swing.JProgressBar();
    okButtonPanel = new javax.swing.JPanel();
    okBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setPreferredSize(new java.awt.Dimension(650, 250));

    topPanel.setMinimumSize(new java.awt.Dimension(50, 50));
    topPanel.setPreferredSize(new java.awt.Dimension(340, 45));
    java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
    flowLayout2.setAlignOnBaseline(true);
    topPanel.setLayout(flowLayout2);

    animatedIconLbl.setDoubleBuffered(true);
    animatedIconLbl.setMaximumSize(new java.awt.Dimension(35, 35));
    animatedIconLbl.setMinimumSize(new java.awt.Dimension(35, 35));
    animatedIconLbl.setPreferredSize(new java.awt.Dimension(35, 35));
    topPanel.add(animatedIconLbl);

    getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

    mainSP.setPreferredSize(new java.awt.Dimension(635, 100));
    mainSP.setViewportView(progressMessageTA);

    progressMessageTA.setColumns(20);
    progressMessageTA.setRows(5);
    mainSP.setViewportView(progressMessageTA);

    getContentPane().add(mainSP, java.awt.BorderLayout.CENTER);

    statusPanel.setLayout(new java.awt.BorderLayout());

    progressPanel.setPreferredSize(new java.awt.Dimension(650, 40));
    progressPanel.setLayout(new javax.swing.BoxLayout(progressPanel, javax.swing.BoxLayout.LINE_AXIS));

    synchProgressBar.setMaximum(1600);
    synchProgressBar.setMaximumSize(new java.awt.Dimension(32767, 20));
    synchProgressBar.setMinimumSize(new java.awt.Dimension(640, 20));
    synchProgressBar.setPreferredSize(new java.awt.Dimension(640, 20));
    progressPanel.add(synchProgressBar);

    statusPanel.add(progressPanel, java.awt.BorderLayout.PAGE_START);

    okButtonPanel.setPreferredSize(new java.awt.Dimension(340, 35));
    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
    flowLayout1.setAlignOnBaseline(true);
    okButtonPanel.setLayout(flowLayout1);

    okBtn.setText("Ok");
    okBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okBtnActionPerformed(evt);
      }
    });
    okButtonPanel.add(okBtn);

    statusPanel.add(okButtonPanel, java.awt.BorderLayout.PAGE_END);

    getContentPane().add(statusPanel, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
    this.setVisible(false);
    this.dispose();
  }//GEN-LAST:event_okBtnActionPerformed

  class ProgressListener implements PropertyChangeListener {

    private final JProgressBar progressBar;
    private final JTextArea textArea;

    protected ProgressListener(JProgressBar progressBar, JTextArea textArea) {
      this.progressBar = progressBar;
      this.textArea = textArea;
      this.progressBar.setValue(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
      if ("synchProcess".equals(pce.getPropertyName())) {
        textArea.append((String) pce.getNewValue());
        textArea.append("\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());

        int pp = this.progressBar.getValue();
        this.progressBar.setValue(++pp);
      }
    }
  }

  class SynchronizeTask extends SwingWorker<String, Object> {

    private final JProgressBar progressBar;
    private final JTextArea textArea;

    protected SynchronizeTask(JProgressBar progressBar, JTextArea textArea) {
      this.progressBar = progressBar;
      this.textArea = textArea;
    }

    @Override
    public String doInBackground() {
      Logger.trace("Starting");
      ProgressListener pl = new ProgressListener(this.progressBar, this.textArea);
      TrackControllerFactory.getTrackController().synchronizeLocomotivesWithController(pl);
      return "done";
    }

    @Override
    protected void done() {
      progressBar.setValue(progressBar.getMaximum());
      AnimatedTurningIcon ati = (AnimatedTurningIcon) animatedIconLbl.getIcon();
      ati.setRunning(true);
      iconAnimator.stop();
      animatedIconLbl.setVisible(false);
    }
  }

  private class AnimatedTurningIcon implements Icon {
    private static final Color ELLIPSE_COLOR = Color.blue;
    private static final double R = 4d;
    private static final double SX = 2d;
    private static final double SY = 2d;
    private static final int WIDTH = (int) (R * 8 + SX * 2);
    private static final int HEIGHT = (int) (R * 8 + SY * 2);
    private final List<Shape> list = new ArrayList<>(Arrays.asList(
            new Ellipse2D.Double(SX + 3 * R, SY + 0 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 5 * R, SY + 1 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 6 * R, SY + 3 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 5 * R, SY + 5 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 3 * R, SY + 6 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 1 * R, SY + 5 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 0 * R, SY + 3 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 1 * R, SY + 1 * R, 2 * R, 2 * R)));

    private boolean running;

    public void next() {
      if (running) {
        Collections.rotate(list, 1);
      }
    }

    public void setRunning(boolean running) {
      this.running = running;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.translate(x, y);
      g2.setPaint(Optional.ofNullable(c).map(Component::getBackground).orElse(Color.WHITE));
      g2.fillRect(0, 0, getIconWidth(), getIconHeight());
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setPaint(ELLIPSE_COLOR);
      list.forEach(s -> {
        float alpha = running ? (list.indexOf(s) + 1f) / list.size() : .5f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fill(s);
      });
      g2.dispose();
    }

    @Override
    public int getIconWidth() {
      return WIDTH;
    }

    @Override
    public int getIconHeight() {
      return HEIGHT;
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    try {
      String plaf = System.getProperty("jcs.plaf", "com.formdev.flatlaf.FlatLightLaf");
      if (plaf != null) {
        UIManager.setLookAndFeel(plaf);
      } else {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.error(ex);
    }

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        SynchronizeWithControllerDialog dialog = new SynchronizeWithControllerDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          @Override
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel animatedIconLbl;
  private javax.swing.JScrollPane mainSP;
  private javax.swing.JButton okBtn;
  private javax.swing.JPanel okButtonPanel;
  private javax.swing.JTextArea progressMessageTA;
  private javax.swing.JPanel progressPanel;
  private javax.swing.JPanel statusPanel;
  private javax.swing.JProgressBar synchProgressBar;
  private javax.swing.JPanel topPanel;
  // End of variables declaration//GEN-END:variables
}
