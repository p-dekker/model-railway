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
package jcs.ui.widgets;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import jcs.JCS;
import jcs.commandStation.events.AccessoryEvent;
import jcs.commandStation.events.AccessoryEventListener;
import jcs.entities.AccessoryBean;
import jcs.entities.AccessoryBean.AccessoryValue;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class SwitchPanel extends JPanel {

  private int panelNumber;

  public SwitchPanel() {
    this(1);
  }

  public SwitchPanel(int panelNumber) {
    this.panelNumber = panelNumber;
    initComponents();
    initButtonText();
  }

  private void initButtonText() {
    int offset = (panelNumber - 1) * 16;

    tb1.setText((1 + offset) + "");
    presetButtonboolean(tb1);
    tb2.setText((2 + offset) + "");
    presetButtonboolean(tb2);
    tb3.setText((3 + offset) + "");
    presetButtonboolean(tb3);
    tb4.setText((4 + offset) + "");
    presetButtonboolean(tb4);
    tb5.setText((5 + offset) + "");
    presetButtonboolean(tb5);
    tb6.setText((6 + offset) + "");
    presetButtonboolean(tb6);
    tb7.setText((7 + offset) + "");
    presetButtonboolean(tb7);
    tb8.setText((8 + offset) + "");
    presetButtonboolean(tb8);
    tb9.setText((9 + offset) + "");
    presetButtonboolean(tb9);
    tb10.setText((10 + offset) + "");
    presetButtonboolean(tb10);
    tb11.setText((11 + offset) + "");
    presetButtonboolean(tb11);
    tb12.setText((12 + offset) + "");
    presetButtonboolean(tb12);
    tb13.setText((13 + offset) + "");
    presetButtonboolean(tb13);
    tb14.setText((14 + offset) + "");
    presetButtonboolean(tb14);
    tb15.setText((15 + offset) + "");
    presetButtonboolean(tb15);
    tb16.setText((16 + offset) + "");
    presetButtonboolean(tb16);

    setTitle();
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);

    this.tb1.setEnabled(enabled);
    this.tb2.setEnabled(enabled);
    this.tb3.setEnabled(enabled);
    this.tb4.setEnabled(enabled);
    this.tb5.setEnabled(enabled);
    this.tb6.setEnabled(enabled);
    this.tb7.setEnabled(enabled);
    this.tb8.setEnabled(enabled);
    this.tb9.setEnabled(enabled);
    this.tb10.setEnabled(enabled);
    this.tb11.setEnabled(enabled);
    this.tb12.setEnabled(enabled);
    this.tb13.setEnabled(enabled);
    this.tb14.setEnabled(enabled);
    this.tb15.setEnabled(enabled);
    this.tb16.setEnabled(enabled);
  }

  private void presetButtonboolean(JToggleButton button) {
    button.setActionCommand((button.getText()));
    Integer address = Integer.valueOf(button.getActionCommand());

    if (JCS.getJcsCommandStation() != null) {
      AccessoryBean ab = PersistenceFactory.getService().getAccessoryByAddress(address);
      if (ab != null) {
        button.setForeground(new Color(0, 153, 0));
        button.setSelected(AccessoryValue.RED.equals(ab.getAccessoryValue()));
        button.setToolTipText(ab.getName());
      } else {
        button.setForeground(new Color(0, 0, 0));
      }
      AccessoryStatusListener asl = new AccessoryStatusListener(button, address);
      JCS.getJcsCommandStation().addAccessoryEventListener(asl);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tb1 = new javax.swing.JToggleButton();
        tb2 = new javax.swing.JToggleButton();
        tb3 = new javax.swing.JToggleButton();
        tb4 = new javax.swing.JToggleButton();
        tb5 = new javax.swing.JToggleButton();
        tb6 = new javax.swing.JToggleButton();
        tb7 = new javax.swing.JToggleButton();
        tb8 = new javax.swing.JToggleButton();
        tb9 = new javax.swing.JToggleButton();
        tb10 = new javax.swing.JToggleButton();
        tb11 = new javax.swing.JToggleButton();
        tb12 = new javax.swing.JToggleButton();
        tb13 = new javax.swing.JToggleButton();
        tb14 = new javax.swing.JToggleButton();
        tb15 = new javax.swing.JToggleButton();
        tb16 = new javax.swing.JToggleButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "1 - 16"));
        setMinimumSize(new java.awt.Dimension(845, 75));
        setPreferredSize(new java.awt.Dimension(1000, 75));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 1);
        flowLayout1.setAlignOnBaseline(true);
        setLayout(flowLayout1);

        tb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb1.setText("1");
        tb1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb1.setMaximumSize(new java.awt.Dimension(50, 50));
        tb1.setMinimumSize(new java.awt.Dimension(50, 50));
        tb1.setPreferredSize(new java.awt.Dimension(50, 50));
        tb1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb1ActionPerformed(evt);
            }
        });
        add(tb1);

        tb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb2.setText("2");
        tb2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb2.setMaximumSize(new java.awt.Dimension(50, 50));
        tb2.setMinimumSize(new java.awt.Dimension(50, 50));
        tb2.setPreferredSize(new java.awt.Dimension(50, 50));
        tb2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb2ActionPerformed(evt);
            }
        });
        add(tb2);

        tb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb3.setText("3");
        tb3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb3.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb3.setMaximumSize(new java.awt.Dimension(50, 50));
        tb3.setMinimumSize(new java.awt.Dimension(50, 50));
        tb3.setPreferredSize(new java.awt.Dimension(50, 50));
        tb3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb3ActionPerformed(evt);
            }
        });
        add(tb3);

        tb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb4.setText("4");
        tb4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb4.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb4.setMaximumSize(new java.awt.Dimension(50, 50));
        tb4.setMinimumSize(new java.awt.Dimension(50, 50));
        tb4.setPreferredSize(new java.awt.Dimension(50, 50));
        tb4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb4ActionPerformed(evt);
            }
        });
        add(tb4);

        tb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb5.setText("5");
        tb5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb5.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb5.setMaximumSize(new java.awt.Dimension(50, 50));
        tb5.setMinimumSize(new java.awt.Dimension(50, 50));
        tb5.setPreferredSize(new java.awt.Dimension(50, 50));
        tb5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb5ActionPerformed(evt);
            }
        });
        add(tb5);

        tb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb6.setText("6");
        tb6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb6.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb6.setMaximumSize(new java.awt.Dimension(50, 50));
        tb6.setMinimumSize(new java.awt.Dimension(50, 50));
        tb6.setPreferredSize(new java.awt.Dimension(50, 50));
        tb6.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb6ActionPerformed(evt);
            }
        });
        add(tb6);

        tb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb7.setText("7");
        tb7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb7.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb7.setMaximumSize(new java.awt.Dimension(50, 50));
        tb7.setMinimumSize(new java.awt.Dimension(50, 50));
        tb7.setPreferredSize(new java.awt.Dimension(50, 50));
        tb7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb7ActionPerformed(evt);
            }
        });
        add(tb7);

        tb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb8.setText("8");
        tb8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb8.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb8.setMaximumSize(new java.awt.Dimension(50, 50));
        tb8.setMinimumSize(new java.awt.Dimension(50, 50));
        tb8.setPreferredSize(new java.awt.Dimension(50, 50));
        tb8.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb8ActionPerformed(evt);
            }
        });
        add(tb8);

        tb9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb9.setText("9");
        tb9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb9.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb9.setMaximumSize(new java.awt.Dimension(50, 50));
        tb9.setMinimumSize(new java.awt.Dimension(50, 50));
        tb9.setPreferredSize(new java.awt.Dimension(50, 50));
        tb9.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb9ActionPerformed(evt);
            }
        });
        add(tb9);

        tb10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb10.setText("10");
        tb10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb10.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb10.setMaximumSize(new java.awt.Dimension(50, 50));
        tb10.setMinimumSize(new java.awt.Dimension(50, 50));
        tb10.setPreferredSize(new java.awt.Dimension(50, 50));
        tb10.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb10ActionPerformed(evt);
            }
        });
        add(tb10);

        tb11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb11.setText("11");
        tb11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb11.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb11.setMaximumSize(new java.awt.Dimension(50, 50));
        tb11.setMinimumSize(new java.awt.Dimension(50, 50));
        tb11.setPreferredSize(new java.awt.Dimension(50, 50));
        tb11.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb11ActionPerformed(evt);
            }
        });
        add(tb11);

        tb12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb12.setText("12");
        tb12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb12.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb12.setMaximumSize(new java.awt.Dimension(50, 50));
        tb12.setMinimumSize(new java.awt.Dimension(50, 50));
        tb12.setPreferredSize(new java.awt.Dimension(50, 50));
        tb12.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb12ActionPerformed(evt);
            }
        });
        add(tb12);

        tb13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb13.setText("13");
        tb13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb13.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb13.setMaximumSize(new java.awt.Dimension(50, 50));
        tb13.setMinimumSize(new java.awt.Dimension(50, 50));
        tb13.setPreferredSize(new java.awt.Dimension(50, 50));
        tb13.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb13ActionPerformed(evt);
            }
        });
        add(tb13);

        tb14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb14.setText("14");
        tb14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb14.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb14.setMaximumSize(new java.awt.Dimension(50, 50));
        tb14.setMinimumSize(new java.awt.Dimension(50, 50));
        tb14.setPreferredSize(new java.awt.Dimension(50, 50));
        tb14.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb14ActionPerformed(evt);
            }
        });
        add(tb14);

        tb15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb15.setText("15");
        tb15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb15.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb15.setMaximumSize(new java.awt.Dimension(50, 50));
        tb15.setMinimumSize(new java.awt.Dimension(50, 50));
        tb15.setPreferredSize(new java.awt.Dimension(50, 50));
        tb15.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb15ActionPerformed(evt);
            }
        });
        add(tb15);

        tb16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Green-14px.png"))); // NOI18N
        tb16.setText("16");
        tb16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tb16.setMargin(new java.awt.Insets(1, 1, 1, 1));
        tb16.setMaximumSize(new java.awt.Dimension(50, 50));
        tb16.setMinimumSize(new java.awt.Dimension(50, 50));
        tb16.setPreferredSize(new java.awt.Dimension(50, 50));
        tb16.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Button-Red-14px.png"))); // NOI18N
        tb16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb16ActionPerformed(evt);
            }
        });
        add(tb16);
    }// </editor-fold>//GEN-END:initComponents

  private void sendCommand(String actionCommand, String name, boolean selected) {
    String id = actionCommand;
    Integer address = Integer.getInteger(id);
    AccessoryValue value = selected ? AccessoryValue.RED : AccessoryValue.GREEN;
    Logger.trace("ID: " + id + " Value: " + value);

    if (JCS.getJcsCommandStation() != null) {
      AccessoryBean a = new AccessoryBean(id, address, (name != null ? name : actionCommand), null, (selected ? 1 : 0), null, null, null);
      JCS.getJcsCommandStation().switchAccessory(value, a);
    }
  }

  private void tb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb1ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb1ActionPerformed

  private void tb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb2ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb2ActionPerformed

  private void tb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb3ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb3ActionPerformed

  private void tb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb4ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb4ActionPerformed

  private void tb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb5ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb5ActionPerformed

  private void tb6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb6ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb6ActionPerformed

  private void tb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb7ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb7ActionPerformed

  private void tb8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb8ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb8ActionPerformed

  private void tb9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb9ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb9ActionPerformed

  private void tb10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb10ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb10ActionPerformed

  private void tb11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb11ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb11ActionPerformed

  private void tb12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb12ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb12ActionPerformed

  private void tb13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb13ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb13ActionPerformed

  private void tb14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb14ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb14ActionPerformed

  private void tb15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb15ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb15ActionPerformed

  private void tb16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb16ActionPerformed
    sendCommand(evt.getActionCommand(), ((JToggleButton) evt.getSource()).getToolTipText(), ((JToggleButton) evt.getSource()).isSelected());
  }//GEN-LAST:event_tb16ActionPerformed

  private void setTitle() {
    int first = (panelNumber - 1) * 16 + 1;
    int last = (panelNumber - 1) * 16 + 16;

    ((TitledBorder) this.getBorder()).setTitle(first + " - " + last);
  }

  public int getPanelNumber() {
    return panelNumber;
  }

  private class AccessoryStatusListener implements AccessoryEventListener {

    private final JToggleButton button;
    private final Integer address;

    AccessoryStatusListener(JToggleButton button, Integer address) {
      this.button = button;
      this.address = address;
    }

    @Override
    public void onAccessoryChange(AccessoryEvent event) {
      if (event.getAccessoryBean().getAddress().equals(address)) {
        this.button.setSelected(AccessoryValue.RED.equals(event.getAccessoryBean().getAccessoryValue()));
      }
    }
  }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton tb1;
    private javax.swing.JToggleButton tb10;
    private javax.swing.JToggleButton tb11;
    private javax.swing.JToggleButton tb12;
    private javax.swing.JToggleButton tb13;
    private javax.swing.JToggleButton tb14;
    private javax.swing.JToggleButton tb15;
    private javax.swing.JToggleButton tb16;
    private javax.swing.JToggleButton tb2;
    private javax.swing.JToggleButton tb3;
    private javax.swing.JToggleButton tb4;
    private javax.swing.JToggleButton tb5;
    private javax.swing.JToggleButton tb6;
    private javax.swing.JToggleButton tb7;
    private javax.swing.JToggleButton tb8;
    private javax.swing.JToggleButton tb9;
    // End of variables declaration//GEN-END:variables
}
