/*
 * Copyright (C) 2019 Frans Jacobs.
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
package lan.wervel.jcs.ui.widgets;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import lan.wervel.jcs.entities.Locomotive;
import lan.wervel.jcs.entities.enums.Direction;
import lan.wervel.jcs.trackservice.LocomotiveEvent;
import lan.wervel.jcs.trackservice.TrackServiceFactory;
import lan.wervel.jcs.trackservice.events.LocomotiveListener;
import org.tinylog.Logger;

/**
 *
 * @author fransjacobs
 */
public class LocoPanel extends JPanel implements LocomotiveListener {

    private Direction direction;

    private Preferences prefs;
    private BigDecimal id;

    public LocoPanel() {
        this(0);
    }

    /**
     * Creates new form LocoPanel
     *
     * @param panelNumber
     */
    public LocoPanel(int panelNumber) {
        direction = Direction.FORWARDS;
        id = new BigDecimal(0);

        initComponents();

        postInit(panelNumber);
    }

    public void refreshPanel() {
        TrackServiceFactory.getTrackService().removeLocomotiveListener(this);
        this.locoCB.setModel(createLocoCBM());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    addressPanel = new javax.swing.JPanel();
    locoCB = new javax.swing.JComboBox<>();
    speedFunctionPanel = new javax.swing.JPanel();
    speedViewPanel = new javax.swing.JPanel();
    speedLbl1 = new javax.swing.JLabel();
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
    speedPBar = new javax.swing.JProgressBar();
    filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(10, 32767));
    speedSettingPanel = new javax.swing.JPanel();
    directionBtn = new javax.swing.JButton();
    filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
    speedSlider = new javax.swing.JSlider();
    stopBtn = new javax.swing.JButton();
    functionPanel = new javax.swing.JPanel();
    f0Btn = new javax.swing.JToggleButton();
    f1Btn = new javax.swing.JToggleButton();
    f2Btn = new javax.swing.JToggleButton();
    f3Btn = new javax.swing.JToggleButton();
    f4Btn = new javax.swing.JToggleButton();

    setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Loco ..."));
    setMinimumSize(new java.awt.Dimension(275, 220));
    setPreferredSize(new java.awt.Dimension(275, 220));
    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

    addressPanel.setMinimumSize(new java.awt.Dimension(200, 35));
    addressPanel.setPreferredSize(new java.awt.Dimension(200, 35));
    java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
    flowLayout3.setAlignOnBaseline(true);
    addressPanel.setLayout(flowLayout3);

    locoCB.setModel(createLocoCBM());
    locoCB.setMinimumSize(new java.awt.Dimension(150, 35));
    locoCB.setPreferredSize(new java.awt.Dimension(150, 35));
    locoCB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        locoCBActionPerformed(evt);
      }
    });
    addressPanel.add(locoCB);

    add(addressPanel);

    speedFunctionPanel.setLayout(new javax.swing.BoxLayout(speedFunctionPanel, javax.swing.BoxLayout.PAGE_AXIS));

    speedViewPanel.setMinimumSize(new java.awt.Dimension(200, 30));
    speedViewPanel.setPreferredSize(new java.awt.Dimension(200, 30));
    speedViewPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

    speedLbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    speedLbl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/gauge-low.png"))); // NOI18N
    speedLbl1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    speedLbl1.setMaximumSize(new java.awt.Dimension(40, 30));
    speedLbl1.setMinimumSize(new java.awt.Dimension(40, 30));
    speedLbl1.setPreferredSize(new java.awt.Dimension(40, 30));
    speedViewPanel.add(speedLbl1);
    speedViewPanel.add(filler2);

    speedPBar.setMaximum(1023);
    speedPBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    speedPBar.setDoubleBuffered(true);
    speedPBar.setPreferredSize(new java.awt.Dimension(150, 20));
    speedViewPanel.add(speedPBar);
    speedViewPanel.add(filler4);

    speedFunctionPanel.add(speedViewPanel);

    speedSettingPanel.setMinimumSize(new java.awt.Dimension(200, 40));
    speedSettingPanel.setPreferredSize(new java.awt.Dimension(200, 40));
    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
    flowLayout1.setAlignOnBaseline(true);
    speedSettingPanel.setLayout(flowLayout1);

    directionBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/arrowhead-up.png"))); // NOI18N
    directionBtn.setMaximumSize(new java.awt.Dimension(40, 40));
    directionBtn.setMinimumSize(new java.awt.Dimension(40, 40));
    directionBtn.setPreferredSize(new java.awt.Dimension(40, 40));
    directionBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        directionBtnActionPerformed(evt);
      }
    });
    speedSettingPanel.add(directionBtn);
    speedSettingPanel.add(filler3);

    speedSlider.setMajorTickSpacing(200);
    speedSlider.setMaximum(1023);
    speedSlider.setMinorTickSpacing(100);
    speedSlider.setPaintTicks(true);
    speedSlider.setValue(0);
    speedSlider.setDoubleBuffered(true);
    speedSlider.setPreferredSize(new java.awt.Dimension(150, 30));
    speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        speedSliderStateChanged(evt);
      }
    });
    speedSettingPanel.add(speedSlider);

    stopBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/circle-stop.png"))); // NOI18N
    stopBtn.setMaximumSize(new java.awt.Dimension(40, 40));
    stopBtn.setMinimumSize(new java.awt.Dimension(40, 40));
    stopBtn.setPreferredSize(new java.awt.Dimension(40, 40));
    stopBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopBtnActionPerformed(evt);
      }
    });
    speedSettingPanel.add(stopBtn);

    speedFunctionPanel.add(speedSettingPanel);

    functionPanel.setMinimumSize(new java.awt.Dimension(200, 40));
    functionPanel.setPreferredSize(new java.awt.Dimension(200, 40));
    java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0);
    flowLayout2.setAlignOnBaseline(true);
    functionPanel.setLayout(flowLayout2);

    f0Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/bulb-off.png"))); // NOI18N
    f0Btn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/bulb-on.png"))); // NOI18N
    f0Btn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        f0BtnActionPerformed(evt);
      }
    });
    functionPanel.add(f0Btn);

    f1Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-one-off.png"))); // NOI18N
    f1Btn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-one-on.png"))); // NOI18N
    f1Btn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        f1BtnActionPerformed(evt);
      }
    });
    functionPanel.add(f1Btn);

    f2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-two-off.png"))); // NOI18N
    f2Btn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-two-on.png"))); // NOI18N
    f2Btn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        f2BtnActionPerformed(evt);
      }
    });
    functionPanel.add(f2Btn);

    f3Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-three-off.png"))); // NOI18N
    f3Btn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-three-on.png"))); // NOI18N
    f3Btn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        f3BtnActionPerformed(evt);
      }
    });
    functionPanel.add(f3Btn);

    f4Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-four-off.png"))); // NOI18N
    f4Btn.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/digital-four-on.png"))); // NOI18N
    f4Btn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        f4BtnActionPerformed(evt);
      }
    });
    functionPanel.add(f4Btn);

    speedFunctionPanel.add(functionPanel);

    add(speedFunctionPanel);

    getAccessibleContext().setAccessibleParent(this);
  }// </editor-fold>//GEN-END:initComponents

    private void setId(BigDecimal id) {
        this.id = id;
        if (this.getName() == null) {
            this.setName(this.getClass().getSimpleName());
        }

        Logger.trace("Store prefs: " + this.getName() + "_ID: " + id);
        this.prefs.put(this.getName() + "_ID", id.toString());
    }

    private void postInit(int panelNumber) {
        String name = this.getClass().getSimpleName();

        if (panelNumber > 0) {
            this.setName(name + panelNumber);
        } else {
            this.setName(name);
        }

        this.prefs = Preferences.userRoot().node(name);

        BigDecimal lid = new BigDecimal(prefs.getLong(this.getName() + "_ID", 0));
        Logger.trace("Retrieve prefs: " + this.getName() + "_ID: " + lid);

        if (TrackServiceFactory.getTrackService() != null) {
            Locomotive psl = TrackServiceFactory.getTrackService().getLocomotive(lid);
            if (psl != null) {
                this.id = psl.getId();
                locoCB.setSelectedItem(psl);
            }
        }
    }

  private void locoCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locoCBActionPerformed
      Locomotive sl = (Locomotive) locoCB.getSelectedItem();
      BigDecimal i = sl.getId();
      if (i != null) {
          setId(i);
          //Retrieve current values...
          if (TrackServiceFactory.getTrackService() == null) {
              return;
          }

          //unregister
          Logger.debug("Unreg: " + this.id);
          TrackServiceFactory.getTrackService().removeLocomotiveListener(this);

          Locomotive cl = TrackServiceFactory.getTrackService().getLocomotive(i);
          this.direction = cl.getDirection();
          setDirectionIcon();
          this.f0Btn.setSelected(cl.isF0());
          this.f1Btn.setSelected(cl.isF1());
          this.f2Btn.setSelected(cl.isF2());
          this.f3Btn.setSelected(cl.isF3());
          this.f4Btn.setSelected(cl.isF4());

          //int max = this.speedSlider.getMaximum();
          int sliderSpeed = cl.getSpeed();
          this.speedSlider.setValue(sliderSpeed);

          //max = this.speedPBar.getMaximum();
          int sp = cl.getSpeed();
          this.speedPBar.setValue(sp);

          this.f1Btn.setEnabled(cl.getFunctionCount() > 1);
          this.f2Btn.setEnabled(cl.getFunctionCount() > 1);
          this.f3Btn.setEnabled(cl.getFunctionCount() > 1);
          this.f4Btn.setEnabled(cl.getFunctionCount() > 1);

          Logger.trace("Loc: " + cl.getName() + " Dir: " + cl.getDirection() + " Speed: " + cl.getSpeed());

          this.setTitle(sl.getName());

          //register 
          TrackServiceFactory.getTrackService().addLocomotiveListener(this);
          Logger.debug("Reg: " + this.id);

      } else {
          this.direction = Direction.FORWARDS;
          setDirectionIcon();
          this.f0Btn.setSelected(false);
          this.f1Btn.setSelected(false);
          this.f2Btn.setSelected(false);
          this.f3Btn.setSelected(false);
          this.f4Btn.setSelected(false);
          this.speedSlider.setValue(0);
          this.speedPBar.setValue(0);
          this.f1Btn.setEnabled(true);
          this.f2Btn.setEnabled(true);
          this.f3Btn.setEnabled(true);
          this.f4Btn.setEnabled(true);
      }
  }//GEN-LAST:event_locoCBActionPerformed

  private void directionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionBtnActionPerformed
      toggleDirection();
      setDirectionIcon();
      Logger.debug(this.direction);

      Locomotive locomotive = getSelectedLoco();
      TrackServiceFactory.getTrackService().toggleDirection(direction, locomotive);
  }//GEN-LAST:event_directionBtnActionPerformed

  private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
      int speed = this.getSpeed();
      this.directionBtn.setEnabled(speed == 0);

      if (!speedSlider.getValueIsAdjusting()) {
          //int max = this.speedPBar.getMaximum();
          int sp = speed;
          this.speedPBar.setValue(sp);
          Logger.trace("Speed: " + speed);

          Locomotive locomotive = getSelectedLoco();

          if (TrackServiceFactory.getTrackService() != null) {
              TrackServiceFactory.getTrackService().changeSpeed(speed, locomotive);
          }
      }
  }//GEN-LAST:event_speedSliderStateChanged

    private Locomotive getSelectedLoco() {
        Locomotive l = (Locomotive) this.locoCB.getSelectedItem();

        Locomotive loco;
        if (l.getId().equals(this.id)) {
            loco = l;
        } else {
            loco = new Locomotive(0, "");
            loco.setId(id);
            Logger.trace("Use tmp Loco: " + loco);
        }

        loco.setF0(this.f0Btn.isSelected());
        loco.setF1(this.f1Btn.isSelected());
        loco.setF2(this.f2Btn.isSelected());
        loco.setF3(this.f3Btn.isSelected());
        loco.setF4(this.f4Btn.isSelected());
        loco.setDirection(this.direction);
        return loco;
    }

  private void f0BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f0BtnActionPerformed
      Logger.trace("F0: " + (f0Btn.isSelected() ? "On" : "Off"));
      Locomotive locomotive = getSelectedLoco();
      locomotive.setSpeed(getSpeed());
      //TrackServiceFactory.getTrackService().toggleFunction(f0Btn.isSelected(), locomotive);
      TrackServiceFactory.getTrackService().setFunction(f0Btn.isSelected(), 0, locomotive);
  }//GEN-LAST:event_f0BtnActionPerformed

  private void f1BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f1BtnActionPerformed
      Logger.trace("F1: " + (f1Btn.isSelected() ? "On" : "Off"));
      Locomotive locomotive = getSelectedLoco();
      locomotive.setSpeed(getSpeed());
      //TrackServiceFactory.getTrackService().toggleF1(f1Btn.isSelected(), locomotive);
      TrackServiceFactory.getTrackService().setFunction(f1Btn.isSelected(), 1, locomotive);
  }//GEN-LAST:event_f1BtnActionPerformed

  private void f2BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2BtnActionPerformed
      Logger.trace("F2: " + (f2Btn.isSelected() ? "On" : "Off"));
      Locomotive locomotive = getSelectedLoco();
      locomotive.setSpeed(getSpeed());
      //TrackServiceFactory.getTrackService().toggleF2(f2Btn.isSelected(), locomotive);
      TrackServiceFactory.getTrackService().setFunction(f2Btn.isSelected(), 2, locomotive);
  }//GEN-LAST:event_f2BtnActionPerformed

  private void f3BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f3BtnActionPerformed
      Logger.trace("F3: " + (f3Btn.isSelected() ? "On" : "Off"));
      Locomotive locomotive = getSelectedLoco();
      locomotive.setSpeed(getSpeed());
      //TrackServiceFactory.getTrackService().toggleF3(f3Btn.isSelected(), locomotive);
      TrackServiceFactory.getTrackService().setFunction(f3Btn.isSelected(), 3, locomotive);
  }//GEN-LAST:event_f3BtnActionPerformed

  private void f4BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f4BtnActionPerformed
      Logger.trace("F4: " + (f4Btn.isSelected() ? "On" : "Off"));
      Locomotive locomotive = getSelectedLoco();
      locomotive.setSpeed(getSpeed());
      //TrackServiceFactory.getTrackService().toggleF4(f4Btn.isSelected(), locomotive);
      TrackServiceFactory.getTrackService().setFunction(f4Btn.isSelected(), 4, locomotive);
  }//GEN-LAST:event_f4BtnActionPerformed

  private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBtnActionPerformed
      this.speedSlider.setValue(0);
      int speed = this.getSpeed();
      this.directionBtn.setEnabled(speed == 0);
      Locomotive locomotive = getSelectedLoco();
      TrackServiceFactory.getTrackService().changeSpeed(0, locomotive);
  }//GEN-LAST:event_stopBtnActionPerformed

    private void toggleDirection() {
        if (Direction.FORWARDS.equals(this.direction)) {
            this.direction = Direction.BACKWARDS;
        } else {
            this.direction = Direction.FORWARDS;
        }
    }

    private void setDirectionIcon() {
        if (Direction.FORWARDS.equals(this.direction)) {
            directionBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/arrowhead-up.png")));
        } else {
            directionBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/arrowhead-down.png")));
        }
    }

    private ComboBoxModel<Locomotive> createLocoCBM() {
        List<Locomotive> locoList = new ArrayList<>();
        //Need one "null or empty" loc on top in the list...
        Locomotive emtpy = new Locomotive(0, "");
        locoList.add(emtpy);
        //Add existing loco's  

        if (TrackServiceFactory.getTrackService() == null) {
            return new DefaultComboBoxModel<>();
        }

        locoList.addAll(TrackServiceFactory.getTrackService().getLocomotives());

        Locomotive[] locs = new Locomotive[locoList.size()];
        locoList.toArray(locs);

        ComboBoxModel<Locomotive> locoCBM = new DefaultComboBoxModel<>(locs);
        Logger.trace("Created Loco CBM with " + locs.length + " loco's...");
        return locoCBM;
    }

    private Integer getSpeed() {
        //int max = this.speedSlider.getMaximum();
        Integer speed = speedSlider.getValue();
        return speed;
    }

    private void setTitle(String title) {
        ((TitledBorder) this.getBorder()).setTitle(title);
        this.repaint();
    }

    @Override
    public void changed(LocomotiveEvent event) {
        Logger.debug(event);

        if (event.isEventFor(id)) {
            Logger.trace(event);

            this.f0Btn.setSelected(event.isF0());
            this.f1Btn.setSelected(event.isF1());
            this.f2Btn.setSelected(event.isF2());
            this.f3Btn.setSelected(event.isF3());
            this.f4Btn.setSelected(event.isF4());

            this.direction = event.getDirection();
            setDirectionIcon();

            //int max = this.speedSlider.getMaximum();
            int sliderSpeed = event.getSpeed();
            this.speedSlider.setValue(sliderSpeed);

            //max = this.speedPBar.getMaximum();
            int sp = event.getSpeed();
            this.speedPBar.setValue(sp);
        }

    }

    public static void main(String[] a) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.warn("Can't set the LookAndFeel: " + ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("LocoPanel Tester");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LocoPanel locoPanel = new LocoPanel();
            f.getContentPane().add(locoPanel, BorderLayout.CENTER);
            f.pack();
            f.setVisible(true);
        });
    }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel addressPanel;
  private javax.swing.JButton directionBtn;
  private javax.swing.JToggleButton f0Btn;
  private javax.swing.JToggleButton f1Btn;
  private javax.swing.JToggleButton f2Btn;
  private javax.swing.JToggleButton f3Btn;
  private javax.swing.JToggleButton f4Btn;
  private javax.swing.Box.Filler filler2;
  private javax.swing.Box.Filler filler3;
  private javax.swing.Box.Filler filler4;
  private javax.swing.JPanel functionPanel;
  private javax.swing.JComboBox<Locomotive> locoCB;
  private javax.swing.JPanel speedFunctionPanel;
  private javax.swing.JLabel speedLbl1;
  private javax.swing.JProgressBar speedPBar;
  private javax.swing.JPanel speedSettingPanel;
  private javax.swing.JSlider speedSlider;
  private javax.swing.JPanel speedViewPanel;
  private javax.swing.JButton stopBtn;
  // End of variables declaration//GEN-END:variables
}
