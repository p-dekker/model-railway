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
package jcs.ui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import jcs.entities.AccessoryBean;
import jcs.persistence.PersistenceFactory;
import jcs.controller.ControllerFactory;
import jcs.ui.options.table.TurnoutTableModel;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class TurnoutPreferencesPanel extends JPanel {

  private final TurnoutTableModel turnoutTableModel;

  public TurnoutPreferencesPanel() {
    turnoutTableModel = new TurnoutTableModel();
    initComponents();
    alignTurnoutTable();

    //Select the first row
    if (turnoutTableModel.getRowCount() > 0) {
      selectTurnout(0);
      this.turnoutTable.setRowSelectionInterval(0, 0);
    }
  }

  private void alignTurnoutTable() {
    this.turnoutTable.getColumnModel().getColumn(0).setPreferredWidth(40);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    this.turnoutTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

    this.turnoutTable.getColumnModel().getColumn(3).setPreferredWidth(40);
    this.turnoutTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
  }

  private AccessoryBean getAccessoryFromTrackService(AccessoryBean turnout) {
    return PersistenceFactory.getService().getAccessory(turnout.getId());
  }

  private void selectTurnout(int row) {
    AccessoryBean t = this.turnoutTableModel.getControllableDeviceAt(row);
    if (t != null) {
      this.selectedTurnout = getAccessoryFromTrackService(t);
      setComponentValues(selectedTurnout);
    } else {
      Logger.trace("No Turnout found @ row " + row);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("deprecation")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectedTurnout = new AccessoryBean();
        topPanel = new JPanel();
        synchronizeBtn = new JButton();
        refreshBtn = new JButton();
        newBtn = new JButton();
        centerPanel = new JPanel();
        centerSplitPane = new JSplitPane();
        turnoutTableScrollPane = new JScrollPane();
        turnoutTable = new JTable();
        turnoutDetailPanel = new JPanel();
        row1Panel = new JPanel();
        idLbl = new JLabel();
        idSpinner = new JSpinner();
        row2Panel = new JPanel();
        nameLbl = new JLabel();
        nameTF = new JTextField();
        row3Panel = new JPanel();
        typeLbl = new JLabel();
        typeTF = new JTextField();
        row4Panel = new JPanel();
        switchTimeLbl = new JLabel();
        switchTimeSpinner = new JSpinner();
        row5Panel = new JPanel();
        decoderTypeLbl = new JLabel();
        decoderTypeTF = new JTextField();
        row6Panel = new JPanel();
        decoderLbl = new JLabel();
        decoderTF = new JTextField();
        row7Panel = new JPanel();
        positionLbl = new JLabel();
        positionSpinner = new JSpinner();
        row8Panel = new JPanel();
        row9Panel = new JPanel();
        filler2 = new Box.Filler(new Dimension(0, 90), new Dimension(0, 90), new Dimension(32767, 300));
        buttonPanel = new JPanel();
        deleteBtn = new JButton();
        filler1 = new Box.Filler(new Dimension(50, 0), new Dimension(200, 0), new Dimension(150, 32767));
        saveBtn = new JButton();
        bottomPanel = new JPanel();

        setMinimumSize(new Dimension(1000, 600));
        setPreferredSize(new Dimension(1000, 600));
        setLayout(new BorderLayout());

        topPanel.setMinimumSize(new Dimension(1000, 50));
        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setRequestFocusEnabled(false);
        FlowLayout flowLayout1 = new FlowLayout(FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        topPanel.setLayout(flowLayout1);

        synchronizeBtn.setIcon(new ImageIcon(getClass().getResource("/media/CS2-3-Sync.png"))); // NOI18N
        synchronizeBtn.setMaximumSize(new Dimension(40, 40));
        synchronizeBtn.setMinimumSize(new Dimension(40, 40));
        synchronizeBtn.setPreferredSize(new Dimension(40, 40));
        synchronizeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                synchronizeBtnActionPerformed(evt);
            }
        });
        topPanel.add(synchronizeBtn);

        refreshBtn.setIcon(new ImageIcon(getClass().getResource("/media/refresh-24.png"))); // NOI18N
        refreshBtn.setMargin(new Insets(2, 2, 2, 2));
        refreshBtn.setMaximumSize(new Dimension(40, 40));
        refreshBtn.setMinimumSize(new Dimension(40, 40));
        refreshBtn.setPreferredSize(new Dimension(40, 40));
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        topPanel.add(refreshBtn);

        newBtn.setIcon(new ImageIcon(getClass().getResource("/media/add-24.png"))); // NOI18N
        newBtn.setToolTipText("Create new Locomotive");
        newBtn.setMaximumSize(new Dimension(40, 40));
        newBtn.setMinimumSize(new Dimension(40, 40));
        newBtn.setPreferredSize(new Dimension(40, 40));
        newBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });
        topPanel.add(newBtn);

        add(topPanel, BorderLayout.NORTH);

        centerPanel.setMinimumSize(new Dimension(1000, 500));
        centerPanel.setPreferredSize(new Dimension(1000, 500));
        centerPanel.setLayout(new BorderLayout());

        centerSplitPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        centerSplitPane.setDividerLocation(500);
        centerSplitPane.setDoubleBuffered(true);
        centerSplitPane.setMinimumSize(new Dimension(1000, 500));
        centerSplitPane.setPreferredSize(new Dimension(1000, 500));

        turnoutTableScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        turnoutTableScrollPane.setPreferredSize(new Dimension(500, 500));

        turnoutTable.setModel(turnoutTableModel);
        turnoutTable.setDoubleBuffered(true);
        turnoutTable.setGridColor(new Color(204, 204, 204));
        turnoutTable.setPreferredSize(new Dimension(480, 470));
        turnoutTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        turnoutTable.getTableHeader().setReorderingAllowed(false);
        turnoutTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                turnoutTableMouseClicked(evt);
            }
        });
        turnoutTableScrollPane.setViewportView(turnoutTable);

        centerSplitPane.setLeftComponent(turnoutTableScrollPane);

        turnoutDetailPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(), "Edit Turnout"));
        turnoutDetailPanel.setMinimumSize(new Dimension(490, 500));
        turnoutDetailPanel.setPreferredSize(new Dimension(390, 540));
        turnoutDetailPanel.setLayout(new BoxLayout(turnoutDetailPanel, BoxLayout.Y_AXIS));

        row1Panel.setMinimumSize(new Dimension(380, 30));
        row1Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout2 = new FlowLayout(FlowLayout.LEFT);
        flowLayout2.setAlignOnBaseline(true);
        row1Panel.setLayout(flowLayout2);

        idLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        idLbl.setLabelFor(idSpinner);
        idLbl.setText("Address / ID");
        idLbl.setPreferredSize(new Dimension(120, 16));
        row1Panel.add(idLbl);

        idSpinner.setModel(new SpinnerNumberModel(Long.valueOf(0L), Long.valueOf(0L), Long.valueOf(256L), Long.valueOf(1L)));
        idSpinner.setDoubleBuffered(true);
        idSpinner.setEditor(new JSpinner.NumberEditor(idSpinner, ""));
        idSpinner.setMinimumSize(new Dimension(70, 26));
        idSpinner.setName(""); // NOI18N
        idSpinner.setNextFocusableComponent(nameTF);
        idSpinner.setPreferredSize(new Dimension(70, 26));
        row1Panel.add(idSpinner);

        turnoutDetailPanel.add(row1Panel);

        row2Panel.setMinimumSize(new Dimension(380, 30));
        row2Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout3 = new FlowLayout(FlowLayout.LEFT);
        flowLayout3.setAlignOnBaseline(true);
        row2Panel.setLayout(flowLayout3);

        nameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        nameLbl.setLabelFor(nameTF);
        nameLbl.setText("Name");
        nameLbl.setPreferredSize(new Dimension(120, 16));
        row2Panel.add(nameLbl);

        nameTF.setMinimumSize(new Dimension(150, 26));
        nameTF.setPreferredSize(new Dimension(150, 26));
        row2Panel.add(nameTF);

        turnoutDetailPanel.add(row2Panel);

        row3Panel.setMinimumSize(new Dimension(380, 30));
        row3Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout4 = new FlowLayout(FlowLayout.LEFT);
        flowLayout4.setAlignOnBaseline(true);
        row3Panel.setLayout(flowLayout4);

        typeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        typeLbl.setLabelFor(typeTF);
        typeLbl.setText("Type");
        typeLbl.setPreferredSize(new Dimension(120, 16));
        row3Panel.add(typeLbl);

        typeTF.setMinimumSize(new Dimension(150, 26));
        typeTF.setPreferredSize(new Dimension(150, 26));
        row3Panel.add(typeTF);

        turnoutDetailPanel.add(row3Panel);

        row4Panel.setMinimumSize(new Dimension(380, 30));
        row4Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout5 = new FlowLayout(FlowLayout.LEFT);
        flowLayout5.setAlignOnBaseline(true);
        row4Panel.setLayout(flowLayout5);

        switchTimeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        switchTimeLbl.setText("Switch Time");
        switchTimeLbl.setMaximumSize(new Dimension(120, 16));
        switchTimeLbl.setMinimumSize(new Dimension(120, 16));
        switchTimeLbl.setPreferredSize(new Dimension(120, 16));
        row4Panel.add(switchTimeLbl);

        switchTimeSpinner.setModel(new SpinnerNumberModel(0, 0, 5000, 1));
        switchTimeSpinner.setDoubleBuffered(true);
        switchTimeSpinner.setEditor(new JSpinner.NumberEditor(switchTimeSpinner, ""));
        switchTimeSpinner.setMinimumSize(new Dimension(70, 26));
        switchTimeSpinner.setName(""); // NOI18N
        switchTimeSpinner.setNextFocusableComponent(nameTF);
        switchTimeSpinner.setPreferredSize(new Dimension(70, 26));
        row4Panel.add(switchTimeSpinner);

        turnoutDetailPanel.add(row4Panel);

        row5Panel.setMinimumSize(new Dimension(380, 30));
        FlowLayout flowLayout6 = new FlowLayout(FlowLayout.LEFT);
        flowLayout6.setAlignOnBaseline(true);
        row5Panel.setLayout(flowLayout6);

        decoderTypeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        decoderTypeLbl.setText("Decoder Type");
        decoderTypeLbl.setToolTipText("");
        decoderTypeLbl.setMaximumSize(new Dimension(120, 16));
        decoderTypeLbl.setMinimumSize(new Dimension(120, 16));
        decoderTypeLbl.setPreferredSize(new Dimension(120, 16));
        row5Panel.add(decoderTypeLbl);

        decoderTypeTF.setMinimumSize(new Dimension(150, 26));
        decoderTypeTF.setPreferredSize(new Dimension(150, 26));
        row5Panel.add(decoderTypeTF);

        turnoutDetailPanel.add(row5Panel);

        row6Panel.setMinimumSize(new Dimension(380, 30));
        row6Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout7 = new FlowLayout(FlowLayout.LEFT);
        flowLayout7.setAlignOnBaseline(true);
        row6Panel.setLayout(flowLayout7);

        decoderLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        decoderLbl.setText("Decoder");
        decoderLbl.setMaximumSize(new Dimension(120, 16));
        decoderLbl.setMinimumSize(new Dimension(120, 16));
        decoderLbl.setPreferredSize(new Dimension(120, 16));
        row6Panel.add(decoderLbl);

        decoderTF.setMinimumSize(new Dimension(150, 26));
        decoderTF.setPreferredSize(new Dimension(150, 26));
        row6Panel.add(decoderTF);

        turnoutDetailPanel.add(row6Panel);

        row7Panel.setMinimumSize(new Dimension(380, 30));
        row7Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout8 = new FlowLayout(FlowLayout.LEFT);
        flowLayout8.setAlignOnBaseline(true);
        row7Panel.setLayout(flowLayout8);

        positionLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        positionLbl.setText("Position");
        positionLbl.setMaximumSize(new Dimension(120, 16));
        positionLbl.setMinimumSize(new Dimension(120, 16));
        positionLbl.setPreferredSize(new Dimension(120, 16));
        row7Panel.add(positionLbl);

        positionSpinner.setModel(new SpinnerNumberModel(0, 0, 4, 1));
        positionSpinner.setDoubleBuffered(true);
        positionSpinner.setEditor(new JSpinner.NumberEditor(positionSpinner, ""));
        positionSpinner.setMinimumSize(new Dimension(70, 26));
        positionSpinner.setName(""); // NOI18N
        positionSpinner.setNextFocusableComponent(nameTF);
        positionSpinner.setPreferredSize(new Dimension(70, 26));
        row7Panel.add(positionSpinner);

        turnoutDetailPanel.add(row7Panel);

        row8Panel.setMinimumSize(new Dimension(380, 30));
        FlowLayout flowLayout9 = new FlowLayout(FlowLayout.LEFT);
        flowLayout9.setAlignOnBaseline(true);
        row8Panel.setLayout(flowLayout9);
        turnoutDetailPanel.add(row8Panel);

        row9Panel.setPreferredSize(new Dimension(380, 30));
        FlowLayout flowLayout10 = new FlowLayout(FlowLayout.LEFT);
        flowLayout10.setAlignOnBaseline(true);
        row9Panel.setLayout(flowLayout10);
        turnoutDetailPanel.add(row9Panel);
        turnoutDetailPanel.add(filler2);

        buttonPanel.setMinimumSize(new Dimension(380, 40));
        buttonPanel.setPreferredSize(new Dimension(380, 40));
        FlowLayout flowLayout11 = new FlowLayout();
        flowLayout11.setAlignOnBaseline(true);
        buttonPanel.setLayout(flowLayout11);

        deleteBtn.setIcon(new ImageIcon(getClass().getResource("/media/delete-24.png"))); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.setMaximumSize(new Dimension(100, 36));
        deleteBtn.setMinimumSize(new Dimension(100, 36));
        deleteBtn.setPreferredSize(new Dimension(100, 36));
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        buttonPanel.add(deleteBtn);
        buttonPanel.add(filler1);

        saveBtn.setIcon(new ImageIcon(getClass().getResource("/media/save-24.png"))); // NOI18N
        saveBtn.setText("Save");
        saveBtn.setMaximumSize(new Dimension(100, 36));
        saveBtn.setMinimumSize(new Dimension(100, 36));
        saveBtn.setPreferredSize(new Dimension(100, 36));
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        buttonPanel.add(saveBtn);

        turnoutDetailPanel.add(buttonPanel);

        centerSplitPane.setRightComponent(turnoutDetailPanel);

        centerPanel.add(centerSplitPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        bottomPanel.setPreferredSize(new Dimension(1014, 50));
        bottomPanel.setRequestFocusEnabled(false);

        GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        bottomPanelLayout.setVerticalGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        add(bottomPanel, BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


  private void newBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
    turnoutTableModel.refresh();
    alignTurnoutTable();

    Logger.debug("Create new Turnout...");
    selectedTurnout = new AccessoryBean();
    long idl = turnoutTableModel.getRowCount() + 1l;

    selectedTurnout.setName("W " + idl);
    selectedTurnout.setId(idl + "");
    selectedTurnout.setPosition(0);

    setComponentValues(selectedTurnout);
    Logger.debug("Created new Turnout..." + this.selectedTurnout);
  }//GEN-LAST:event_newBtnActionPerformed

  private void turnoutTableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_turnoutTableMouseClicked
    JTable source = (JTable) evt.getSource();
    int row = source.rowAtPoint(evt.getPoint());

    AccessoryBean t = this.turnoutTableModel.getControllableDeviceAt(row);
    if (t != null) {
      Logger.debug("Selected row: " + row + ", Turnout ID: " + t.getId());
      //Refresh from repo
      this.selectedTurnout = PersistenceFactory.getService().getAccessory(t.getId());
      this.setComponentValues(this.selectedTurnout);
    }
  }//GEN-LAST:event_turnoutTableMouseClicked

  private void saveBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
    this.selectedTurnout = setTurnoutValues();
    Logger.debug("Save the Turnout: " + this.selectedTurnout);

    AccessoryBean t = PersistenceFactory.getService().getAccessory(selectedTurnout.getId());
    if (t != null) {
      this.selectedTurnout.setId(t.getId());
      Logger.debug("Found turnout with id " + t.getId());
    } else {
      this.selectedTurnout.setPosition(0);
    }

    selectedTurnout = PersistenceFactory.getService().persist(selectedTurnout);
    setComponentValues(selectedTurnout);
    turnoutTableModel.refresh();
    alignTurnoutTable();
  }//GEN-LAST:event_saveBtnActionPerformed

  private void deleteBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
    Logger.debug("Delete Turnout: " + this.selectedTurnout);
    PersistenceFactory.getService().remove(selectedTurnout);
    turnoutTableModel.refresh();
    selectedTurnout = null;
    setComponentValues(selectedTurnout);
    alignTurnoutTable();
  }//GEN-LAST:event_deleteBtnActionPerformed

  public void refresh() {
    turnoutTableModel.refresh();
    alignTurnoutTable();
    //Select the first row
    if (turnoutTableModel.getRowCount() > 0) {
      selectTurnout(0);
      this.turnoutTable.setRowSelectionInterval(0, 0);
    }
  }

  private void synchronize() {
    ControllerFactory.getController().synchronizeTurnoutsWithController();
    refresh();
  }

    private void synchronizeBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_synchronizeBtnActionPerformed
      synchronize();
    }//GEN-LAST:event_synchronizeBtnActionPerformed

    private void refreshBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
      refresh();
    }//GEN-LAST:event_refreshBtnActionPerformed

  //Create Turnout from fields  
  protected AccessoryBean setTurnoutValues() {
    String id = (String) this.idSpinner.getValue();
    Integer address = Integer.getInteger(id);
    String name = nameTF.getText();
    String type = typeTF.getText();
    Integer switchTime = (Integer) this.switchTimeSpinner.getValue();
    String decoderType = this.decoderTypeTF.getText();
    String decoder = this.decoderTF.getText();
    Integer position = (Integer) this.positionSpinner.getValue();

    AccessoryBean turnout = new AccessoryBean(id, address, name, type, position, switchTime, decoderType, decoder);

    return turnout;
  }

  protected void setComponentValues(AccessoryBean turnout) {
    if (turnout != null) {
      this.idSpinner.setValue(turnout.getId());
      this.nameTF.setText(turnout.getName());
      this.typeTF.setText(turnout.getType());
      this.switchTimeSpinner.setValue(turnout.getSwitchTime());
      this.decoderTypeTF.setText(turnout.getDecoderType());
      this.decoderTF.setText(turnout.getDecoder());
      this.positionSpinner.setValue(turnout.getPosition());
    } else {
      this.idSpinner.setValue(0);
      this.nameTF.setText("");
      this.typeTF.setText("");
      this.switchTimeSpinner.setValue(0);
      this.decoderTypeTF.setText("");
      this.decoderTF.setText("");
      this.positionSpinner.setValue(0);
    }
  }

  public static void main(String args[]) {
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.error("Can't set the LookAndFeel: " + ex);
    }
    java.awt.EventQueue.invokeLater(() -> {

      TurnoutPreferencesPanel testPanel = new TurnoutPreferencesPanel();
      JFrame testFrame = new JFrame();
      JDialog testDialog = new JDialog(testFrame, true);

      testDialog.add(testPanel);

      testDialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          System.exit(0);
        }
      });
      testDialog.pack();
      testDialog.setLocationRelativeTo(null);

      testDialog.setVisible(true);
    });
  }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JPanel centerPanel;
    private JSplitPane centerSplitPane;
    private JLabel decoderLbl;
    private JTextField decoderTF;
    private JLabel decoderTypeLbl;
    private JTextField decoderTypeTF;
    private JButton deleteBtn;
    private Box.Filler filler1;
    private Box.Filler filler2;
    private JLabel idLbl;
    private JSpinner idSpinner;
    private JLabel nameLbl;
    private JTextField nameTF;
    private JButton newBtn;
    private JLabel positionLbl;
    private JSpinner positionSpinner;
    private JButton refreshBtn;
    private JPanel row1Panel;
    private JPanel row2Panel;
    private JPanel row3Panel;
    private JPanel row4Panel;
    private JPanel row5Panel;
    private JPanel row6Panel;
    private JPanel row7Panel;
    private JPanel row8Panel;
    private JPanel row9Panel;
    private JButton saveBtn;
    private AccessoryBean selectedTurnout;
    private JLabel switchTimeLbl;
    private JSpinner switchTimeSpinner;
    private JButton synchronizeBtn;
    private JPanel topPanel;
    private JPanel turnoutDetailPanel;
    private JTable turnoutTable;
    private JScrollPane turnoutTableScrollPane;
    private JLabel typeLbl;
    private JTextField typeTF;
    // End of variables declaration//GEN-END:variables
}
