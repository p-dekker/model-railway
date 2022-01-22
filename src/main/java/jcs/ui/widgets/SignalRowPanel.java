/*
 * Copyright (C) 2019 Frans Jacobs <frans.jacobs@gmail.com>.
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
package jcs.ui.widgets;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jcs.entities.AccessoryBean;
import jcs.entities.enums.AccessoryValue;
import jcs.entities.enums.SignalValue;
import jcs.trackservice.AccessoryEvent;
import jcs.trackservice.TrackServiceFactory;
import jcs.trackservice.events.AccessoryListener;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs <frans.jacobs@gmail.com>
 */
public class SignalRowPanel extends JPanel implements AccessoryListener {

    private AccessoryBean signal;

    private static final String SIGNAL4 = "/media/signal4.png";
    private static final String SIGNAL4_HP0 = "/media/signal4-Hp0.png";
    private static final String SIGNAL4_HP1 = "/media/signal4-Hp1.png";
    private static final String SIGNAL4_HP2 = "/media/signal4-Hp2.png";
    private static final String SIGNAL4_HP0SH1 = "/media/signal4-Hp0Sh1.png";
    private static final String SIGNAL2 = "/media/signal2.png";
    private static final String SIGNAL2_HP0 = "/media/signal2-Hp0.png";
    private static final String SIGNAL2_HP1 = "/media/signal2-Hp1.png";

    private static final String SIGNAL2M = "/media/signal2m.png";
    private static final String SIGNAL2M_HP0 = "/media/signal2m-Hp0.png";
    private static final String SIGNAL2M_HP1 = "/media/signal2m-Hp1.png";

    public static final int X_AXIS = BoxLayout.X_AXIS;
    public static final int Y_AXIS = BoxLayout.Y_AXIS;

    private final int axis;

    public SignalRowPanel() {
        this(null, X_AXIS);
    }

    public SignalRowPanel(AccessoryBean signal) {
        this(signal, X_AXIS);
    }

    public SignalRowPanel(AccessoryBean signal, int axis) {
        this.signal = signal;
        this.axis = axis;

        initComponents();
        postInit();
    }

    private void postInit() {
        setLayout(new javax.swing.BoxLayout(this, axis));

        if (Y_AXIS == this.axis) {
            setMinimumSize(new java.awt.Dimension(55, 280));
            setPreferredSize(new java.awt.Dimension(55, 280));
        }
        if (signal != null) {
            //setButtonImages(signal.getLightImages(), signal.getDescription());
            rowLbl.setText(signal.getName());
            setButtonStatus();
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

        signalBG = new javax.swing.ButtonGroup();
        rowLbl = new javax.swing.JLabel();
        btnHp0 = new javax.swing.JToggleButton();
        btnHp1 = new javax.swing.JToggleButton();
        btnHp2 = new javax.swing.JToggleButton();
        btnHp0Sh1 = new javax.swing.JToggleButton();

        setMinimumSize(new java.awt.Dimension(280, 55));
        setPreferredSize(new java.awt.Dimension(280, 55));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        rowLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rowLbl.setLabelFor(this);
        rowLbl.setText("S nn/nn");
        rowLbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rowLbl.setMaximumSize(new java.awt.Dimension(60, 16));
        rowLbl.setMinimumSize(new java.awt.Dimension(60, 16));
        rowLbl.setPreferredSize(new java.awt.Dimension(60, 16));
        add(rowLbl);

        signalBG.add(btnHp0);
        btnHp0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4.png"))); // NOI18N
        btnHp0.setToolTipText("Hp0");
        btnHp0.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnHp0.setMaximumSize(new java.awt.Dimension(55, 55));
        btnHp0.setPreferredSize(new java.awt.Dimension(55, 55));
        btnHp0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4-Hp0.png"))); // NOI18N
        btnHp0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHp0ActionPerformed(evt);
            }
        });
        add(btnHp0);

        signalBG.add(btnHp1);
        btnHp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4.png"))); // NOI18N
        btnHp1.setToolTipText("Hp1");
        btnHp1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnHp1.setMaximumSize(new java.awt.Dimension(55, 55));
        btnHp1.setPreferredSize(new java.awt.Dimension(55, 55));
        btnHp1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4-Hp1.png"))); // NOI18N
        btnHp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHp1ActionPerformed(evt);
            }
        });
        add(btnHp1);

        signalBG.add(btnHp2);
        btnHp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4.png"))); // NOI18N
        btnHp2.setToolTipText("Hp2");
        btnHp2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnHp2.setMaximumSize(new java.awt.Dimension(55, 55));
        btnHp2.setPreferredSize(new java.awt.Dimension(55, 55));
        btnHp2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4-Hp2.png"))); // NOI18N
        btnHp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHp2ActionPerformed(evt);
            }
        });
        add(btnHp2);

        signalBG.add(btnHp0Sh1);
        btnHp0Sh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4.png"))); // NOI18N
        btnHp0Sh1.setToolTipText("Hp0 + Sh1");
        btnHp0Sh1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btnHp0Sh1.setMaximumSize(new java.awt.Dimension(55, 55));
        btnHp0Sh1.setPreferredSize(new java.awt.Dimension(55, 55));
        btnHp0Sh1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/signal4-Hp0Sh1.png"))); // NOI18N
        btnHp0Sh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHp0Sh1ActionPerformed(evt);
            }
        });
        add(btnHp0Sh1);
    }// </editor-fold>//GEN-END:initComponents

  private void btnHp0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHp0ActionPerformed
      switchSignal(SignalValue.Hp0);
  }//GEN-LAST:event_btnHp0ActionPerformed

  private void btnHp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHp1ActionPerformed
      switchSignal(SignalValue.Hp1);
  }//GEN-LAST:event_btnHp1ActionPerformed

  private void btnHp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHp2ActionPerformed
      switchSignal(SignalValue.Hp2);
  }//GEN-LAST:event_btnHp2ActionPerformed

  private void btnHp0Sh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHp0Sh1ActionPerformed
      switchSignal(SignalValue.Hp0Sh1);
  }//GEN-LAST:event_btnHp0Sh1ActionPerformed

    private void setButtonImages(int lightImages, String type) {
        if (lightImages == 4) {
            btnHp0.setIcon(new ImageIcon(getClass().getResource(SIGNAL4)));
            btnHp0.setSelectedIcon(new ImageIcon(getClass().getResource(SIGNAL4_HP0)));

            btnHp1.setIcon(new ImageIcon(getClass().getResource(SIGNAL4)));
            btnHp1.setSelectedIcon(new ImageIcon(getClass().getResource(SIGNAL4_HP1)));

            btnHp2.setIcon(new ImageIcon(getClass().getResource(SIGNAL4)));
            btnHp2.setSelectedIcon(new ImageIcon(getClass().getResource(SIGNAL4_HP2)));
            btnHp2.setVisible(true);
            btnHp2.setEnabled(true);
            btnHp0Sh1.setIcon(new ImageIcon(getClass().getResource(SIGNAL4)));
            btnHp0Sh1.setSelectedIcon(new ImageIcon(getClass().getResource(SIGNAL4_HP0SH1)));
            btnHp0Sh1.setVisible(true);
            btnHp0Sh1.setEnabled(true);
        } else {
            if ("Midget".equals(type)) {
                btnHp0.setIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2M)));
                btnHp0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2M_HP0)));
                btnHp1.setIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2M)));
                btnHp1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2M_HP1)));
            } else {
                btnHp0.setIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2)));
                btnHp0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2_HP0)));
                btnHp1.setIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2)));
                btnHp1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource(SIGNAL2_HP1)));
            }
            btnHp2.setVisible(false);
            btnHp2.setEnabled(false);
            btnHp0Sh1.setVisible(false);
            btnHp0Sh1.setEnabled(false);
        }
    }

    public AccessoryBean getSignal() {
        return signal;
    }

    public void setSignal(AccessoryBean signal) {
        this.signal = signal;

        if (signal != null) {
            //setButtonImages(signal.getLightImages(), signal.getDescription());
            rowLbl.setText(signal.getName());
            setButtonStatus();
        }
    }

    @Override
    public void switched(AccessoryEvent event) {
        //if (this.signal != null && event.isEventFor(this.signal)) {
        //    signal.setSignalValue(event.getSignalValue());
        //    setButtonStatus();
        //}
    }

    private void switchSignal(SignalValue signalValue) {
        Logger.trace("Setting Signal Value: " + signalValue);
//        if (this.signal != null) {
//            this.signal.setSignalValue(signalValue);
//
//            switch (signalValue) {
//                case Hp0:
//                    sendCommand(AccessoryValue.RED, signal, false);
//                    break;
//                case Hp1:
//                    sendCommand(AccessoryValue.GREEN, signal, false);
//                    break;
//                case Hp2:
//                    sendCommand(AccessoryValue.GREEN, signal, true);
//                    break;
//                case Hp0Sh1:
//                    sendCommand(AccessoryValue.RED, signal, true);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    private void sendCommand(AccessoryValue value, AccessoryBean signal, boolean useValue2) {
        if (TrackServiceFactory.getTrackService() != null) {
            //TrackServiceFactory.getTrackService().switchAccessory(value, signal, useValue2);
        }
    }

    private void setButtonStatus() {
        if (this.signal != null) {
            //Logger.trace("SignalBean: " + signal + " SignalBean Value: " + signal.getSignalValue());

//            switch (signal.getSignalValue()) {
//                case Hp0:
//                    this.btnHp0.setSelected(true);
//                    //Logger.trace("Button Hp0: selected -> true.");
//                    break;
//                case Hp1:
//                    this.btnHp1.setSelected(true);
//                    //Logger.trace("Button Hp1: selected -> true.");
//                    break;
//                case Hp2:
//                    this.btnHp2.setSelected(true);
//                    //Logger.trace("Button Hp2: selected -> true.");
//                    break;
//                case Hp0Sh1:
//                    this.btnHp0Sh1.setSelected(true);
//                    //Logger.trace("Button Hp0Sh1: selected -> true.");
//                    break;
//                default:
//                    Logger.trace("Default called; Value: " + signal.getSignalValue());
//                    break;
//            }
        }
    }

    public static void main(String args[]) {
        JFrame f = new JFrame("SignalRowPanel Tester");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<AccessoryBean> signals = Collections.EMPTY_LIST; //TrackServiceFactory.getTrackService().getSignals();
        f.setLayout(new GridLayout(signals.size(), 1));

        for (AccessoryBean signal : signals) {
            SignalRowPanel signalRowPanel = new SignalRowPanel(signal);
            f.add(signalRowPanel);

            TrackServiceFactory.getTrackService().addAccessoiryListener(signalRowPanel);
        }

        f.pack();
        f.setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnHp0;
    private javax.swing.JToggleButton btnHp0Sh1;
    private javax.swing.JToggleButton btnHp1;
    private javax.swing.JToggleButton btnHp2;
    private javax.swing.JLabel rowLbl;
    private javax.swing.ButtonGroup signalBG;
    // End of variables declaration//GEN-END:variables
}
