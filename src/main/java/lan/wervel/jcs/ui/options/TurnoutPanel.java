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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import lan.wervel.jcs.entities.Turnout;
import lan.wervel.jcs.entities.enums.AccessoryValue;
import lan.wervel.jcs.trackservice.TrackServiceFactory;
import lan.wervel.jcs.ui.options.table.TurnoutTableModel;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Logger;

/**
 *
 * @author frans
 */
public class TurnoutPanel extends JPanel {
  
  private final TurnoutTableModel turnoutTableModel;

  public TurnoutPanel() {
    turnoutTableModel = new TurnoutTableModel();
    initComponents();
    alignTurnoutTable();
  }
  
  private void alignTurnoutTable() {
    this.turnoutTable.getColumnModel().getColumn(0).setPreferredWidth(50);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    this.turnoutTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
   * method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("deprecation")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    directionBG = new ButtonGroup();
    selectedTurnout = new Turnout();
    topPanel = new JPanel();
    refreshBtn = new JButton();
    newBtn = new JButton();
    centerPanel = new JPanel();
    centerSplitPane = new JSplitPane();
    turnoutTableScrollPane = new JScrollPane();
    turnoutTable = new JTable();
    turnoutDetailPanel = new JPanel();
    row1Panel = new JPanel();
    addressLbl = new JLabel();
    addressSpinner = new JSpinner();
    idLbl = new JLabel();
    row2Panel = new JPanel();
    defaultDirectionLbl = new JLabel();
    leftRB = new JRadioButton();
    rightRB = new JRadioButton();
    crossRB = new JRadioButton();
    row3Panel = new JPanel();
    nameLbl = new JLabel();
    nameTF = new JTextField();
    row4Panel = new JPanel();
    catalogeNrLbl = new JLabel();
    catalogNrTF = new JTextField();
    row5Panel = new JPanel();
    row6Panel = new JPanel();
    row7Panel = new JPanel();
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

    refreshBtn.setIcon(new ImageIcon(getClass().getResource("/media/refresh-24.png"))); // NOI18N
    refreshBtn.setText("Refresh");
    refreshBtn.setMargin(new Insets(2, 2, 2, 2));
    refreshBtn.setMaximumSize(new Dimension(120, 36));
    refreshBtn.setMinimumSize(new Dimension(120, 36));
    refreshBtn.setPreferredSize(new Dimension(120, 36));
    refreshBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        refreshBtnActionPerformed(evt);
      }
    });
    topPanel.add(refreshBtn);

    newBtn.setIcon(new ImageIcon(getClass().getResource("/media/add-24.png"))); // NOI18N
    newBtn.setText("New");
    newBtn.setToolTipText("Create new Locomotive");
    newBtn.setMaximumSize(new Dimension(120, 36));
    newBtn.setMinimumSize(new Dimension(120, 36));
    newBtn.setPreferredSize(new Dimension(120, 36));
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

    addressLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    addressLbl.setLabelFor(addressSpinner);
    addressLbl.setText("Address");
    addressLbl.setPreferredSize(new Dimension(120, 16));
    row1Panel.add(addressLbl);

    addressSpinner.setModel(new SpinnerNumberModel(0, 0, 256, 1));
    addressSpinner.setDoubleBuffered(true);
    addressSpinner.setEditor(new JSpinner.NumberEditor(addressSpinner, ""));
    addressSpinner.setMinimumSize(new Dimension(60, 26));
    addressSpinner.setName(""); // NOI18N
    addressSpinner.setNextFocusableComponent(nameTF);
    addressSpinner.setPreferredSize(new Dimension(60, 26));
    row1Panel.add(addressSpinner);

    idLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    idLbl.setText("ID: ");
    idLbl.setPreferredSize(new Dimension(120, 16));
    row1Panel.add(idLbl);

    turnoutDetailPanel.add(row1Panel);

    row2Panel.setMinimumSize(new Dimension(380, 30));
    row2Panel.setPreferredSize(new Dimension(380, 30));
    FlowLayout flowLayout3 = new FlowLayout(FlowLayout.LEFT);
    flowLayout3.setAlignOnBaseline(true);
    row2Panel.setLayout(flowLayout3);

    defaultDirectionLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    defaultDirectionLbl.setLabelFor(rightRB);
    defaultDirectionLbl.setText("Direction");
    defaultDirectionLbl.setMaximumSize(new Dimension(120, 16));
    defaultDirectionLbl.setPreferredSize(new Dimension(120, 16));
    row2Panel.add(defaultDirectionLbl);

    directionBG.add(leftRB);
    leftRB.setText("Left");
    row2Panel.add(leftRB);

    directionBG.add(rightRB);
    rightRB.setText("Right");
    row2Panel.add(rightRB);

    directionBG.add(crossRB);
    crossRB.setText("Cross");
    row2Panel.add(crossRB);

    turnoutDetailPanel.add(row2Panel);

    row3Panel.setMinimumSize(new Dimension(380, 30));
    row3Panel.setPreferredSize(new Dimension(380, 30));
    FlowLayout flowLayout4 = new FlowLayout(FlowLayout.LEFT);
    flowLayout4.setAlignOnBaseline(true);
    row3Panel.setLayout(flowLayout4);

    nameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    nameLbl.setLabelFor(nameTF);
    nameLbl.setText("Name");
    nameLbl.setPreferredSize(new Dimension(120, 16));
    row3Panel.add(nameLbl);

    nameTF.setMinimumSize(new Dimension(120, 26));
    nameTF.setPreferredSize(new Dimension(120, 26));
    row3Panel.add(nameTF);

    turnoutDetailPanel.add(row3Panel);

    row4Panel.setMinimumSize(new Dimension(380, 30));
    row4Panel.setPreferredSize(new Dimension(380, 30));
    FlowLayout flowLayout5 = new FlowLayout(FlowLayout.LEFT);
    flowLayout5.setAlignOnBaseline(true);
    row4Panel.setLayout(flowLayout5);

    catalogeNrLbl.setHorizontalAlignment(SwingConstants.TRAILING);
    catalogeNrLbl.setLabelFor(catalogNrTF);
    catalogeNrLbl.setText("Catalog Number");
    catalogeNrLbl.setPreferredSize(new Dimension(120, 16));
    row4Panel.add(catalogeNrLbl);

    catalogNrTF.setMinimumSize(new Dimension(120, 26));
    catalogNrTF.setPreferredSize(new Dimension(120, 26));
    row4Panel.add(catalogNrTF);

    turnoutDetailPanel.add(row4Panel);

    row5Panel.setMinimumSize(new Dimension(380, 30));
    FlowLayout flowLayout6 = new FlowLayout(FlowLayout.LEFT);
    flowLayout6.setAlignOnBaseline(true);
    row5Panel.setLayout(flowLayout6);
    turnoutDetailPanel.add(row5Panel);

    row6Panel.setMinimumSize(new Dimension(380, 30));
    row6Panel.setPreferredSize(new Dimension(380, 30));
    FlowLayout flowLayout7 = new FlowLayout(FlowLayout.LEFT);
    flowLayout7.setAlignOnBaseline(true);
    row6Panel.setLayout(flowLayout7);
    turnoutDetailPanel.add(row6Panel);

    row7Panel.setMinimumSize(new Dimension(380, 30));
    row7Panel.setPreferredSize(new Dimension(380, 30));
    FlowLayout flowLayout8 = new FlowLayout(FlowLayout.LEFT);
    flowLayout8.setAlignOnBaseline(true);
    row7Panel.setLayout(flowLayout8);
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
    selectedTurnout = new Turnout(0, "U", null);
    Integer a  = turnoutTableModel.getRowCount()+1;
    
    selectedTurnout.setName("W " + a);
    selectedTurnout.setAddress(a);
    selectedTurnout.setValue(AccessoryValue.GREEN);
    
    setComponentValues(selectedTurnout);
    Logger.debug("Create new Turnout..." + this.selectedTurnout);
  }//GEN-LAST:event_newBtnActionPerformed

  private void turnoutTableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_turnoutTableMouseClicked
    JTable source = (JTable) evt.getSource();
    int row = source.rowAtPoint(evt.getPoint());
    
    Turnout t = this.turnoutTableModel.getControllableDeviceAt(row);
    if (t != null) {
      Logger.debug("Selected row: " + row + ", Turnout Address: " + t.getAddress());
      //Refresh from repo
      this.selectedTurnout = TrackServiceFactory.getTrackService().getTurnout(t.getAddress());
      this.setComponentValues(this.selectedTurnout);
    }
  }//GEN-LAST:event_turnoutTableMouseClicked

  private void saveBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
    this.selectedTurnout = setTurnoutValues();
    Logger.debug("Save the Turnout: " + this.selectedTurnout);
    
    Turnout t = TrackServiceFactory.getTrackService().getTurnout(selectedTurnout.getAddress());
    if (t != null) {
      this.selectedTurnout.setId(t.getId());
      this.selectedTurnout.setValue(t.getValue());
      Logger.debug("Found turnout with id " + t.getId());
    } else {
      this.selectedTurnout.setValue(AccessoryValue.GREEN);
    }
    
    selectedTurnout = TrackServiceFactory.getTrackService().persist(selectedTurnout);
    setComponentValues(selectedTurnout);
    turnoutTableModel.refresh();
    alignTurnoutTable();
  }//GEN-LAST:event_saveBtnActionPerformed

  private void deleteBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
    Logger.debug("Delete Turnout: " + this.selectedTurnout);
    TrackServiceFactory.getTrackService().remove(selectedTurnout);
    turnoutTableModel.refresh();
    selectedTurnout = null;
    setComponentValues(selectedTurnout);
    alignTurnoutTable();
  }//GEN-LAST:event_deleteBtnActionPerformed
  
  public void refresh() {
    turnoutTableModel.refresh();
    alignTurnoutTable();
  }

  private void refreshBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
    refresh();
  }//GEN-LAST:event_refreshBtnActionPerformed

  //Create Turnout from fields  
  protected Turnout setTurnoutValues() {
    Integer address = (Integer) addressSpinner.getValue();
    String name = nameTF.getText();
    
    String description = "";
    if (leftRB.isSelected()) {
      description = "L";
    } else if (rightRB.isSelected()) {
      description = "R";
    } else if (crossRB.isSelected()) {
      description = "X";
    } else {
      //None selected...
    }
    
    String catalogNumber = catalogNrTF.getText();
    
    Turnout t = new Turnout(address, description, catalogNumber);
    t.setName(name);
    
    return t;
  }

  protected void setComponentValues(Turnout turnout) {
    if (turnout != null) {
      addressSpinner.setValue(turnout.getAddress());
      nameTF.setText(turnout.getName());
      catalogNrTF.setText(turnout.getCatalogNumber());
      
      switch (turnout.getDescription()) {
        case "R":
          this.rightRB.setSelected(true);
          break;
        case "L":
          this.leftRB.setSelected(true);
          break;
        case "X":
          this.crossRB.setSelected(true);
          break;
        default:
          this.rightRB.setSelected(false);
          this.leftRB.setSelected(false);
          this.crossRB.setSelected(false);
          break;
      }
      
      this.idLbl.setText("ID: " + turnout.getId());
    } else {
      addressSpinner.setValue(0);
      nameTF.setText("");
      catalogNrTF.setText("");
      rightRB.setSelected(false);
      leftRB.setSelected(false);
      crossRB.setSelected(false);
      idLbl.setText("ID: --");
    }
  }
  
  public static void main(String args[]) {
    Configurator.defaultConfig().level(org.pmw.tinylog.Level.DEBUG).activate();
    
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      Logger.warn("Can't set the LookAndFeel: " + ex);
    }
    java.awt.EventQueue.invokeLater(() -> {
      
      TurnoutPanel testPanel = new TurnoutPanel();
      JFrame testFrame = new JFrame();
      JDialog testDialog = new JDialog(testFrame, true);
      
      testDialog.add(testPanel);
      testDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(testDialog.getClass().getResource("/media/jcs-train-64.png")));
      
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
  private JLabel addressLbl;
  private JSpinner addressSpinner;
  private JPanel bottomPanel;
  private JPanel buttonPanel;
  private JTextField catalogNrTF;
  private JLabel catalogeNrLbl;
  private JPanel centerPanel;
  private JSplitPane centerSplitPane;
  private JRadioButton crossRB;
  private JLabel defaultDirectionLbl;
  private JButton deleteBtn;
  private ButtonGroup directionBG;
  private Box.Filler filler1;
  private Box.Filler filler2;
  private JLabel idLbl;
  private JRadioButton leftRB;
  private JLabel nameLbl;
  private JTextField nameTF;
  private JButton newBtn;
  private JButton refreshBtn;
  private JRadioButton rightRB;
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
  private Turnout selectedTurnout;
  private JPanel topPanel;
  private JPanel turnoutDetailPanel;
  private JTable turnoutTable;
  private JScrollPane turnoutTableScrollPane;
  // End of variables declaration//GEN-END:variables
}
