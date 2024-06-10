/*
 * Copyright 2023 Frans Jacobs
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
package jcs.ui.table;

import com.twelvemonkeys.image.ImageUtil;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.RowSorterEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import jcs.commandStation.autopilot.AutoPilot;
import jcs.commandStation.autopilot.state.LocomotiveDispatcher;
import jcs.entities.LocomotiveBean;
import jcs.ui.DriverCabDialog;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class DispatcherTablePanel extends javax.swing.JPanel {

  /**
   * Creates new form LocomotiveTablePanel
   */
  public DispatcherTablePanel() {

    initComponents();

    this.dispatcherTable.setDefaultRenderer(Image.class, new LocIconRenderer());
    this.dispatcherTable.getRowSorter().addRowSorterListener((RowSorterEvent e) -> {
      //Logger.trace(e.getType() + "," + e.getSource().getSortKeys());// Sorting changed
    });

    initModel();
  }

  private void initModel() {
    if (AutoPilot.getInstance().isRunning()) {
      List<LocomotiveDispatcher> dispatchers = AutoPilot.getInstance().getLocomotiveDispatchers();
      Logger.trace("Found " + dispatchers.size() + " Dispatchers");
      this.locomotiveDispatcherTableModel.setBeans(dispatchers);
    }
  }

  private class LocIconRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      Image img = (Image) value;
      int size = 40;
      float aspect = (float) img.getHeight(null) / (float) img.getWidth(null);
      img = img.getScaledInstance(size, (int) (size * aspect), Image.SCALE_SMOOTH);
      BufferedImage bi = ImageUtil.toBuffered(img);
      setIcon(new ImageIcon(bi));
      setHorizontalAlignment(JLabel.CENTER);
      setText("");
      return this;
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    locomotiveDispatcherTableModel = new jcs.ui.table.model.LocomotiveDispatcherTableModel();
    dispatcherSP = new javax.swing.JScrollPane();
    dispatcherTable = new javax.swing.JTable();

    setPreferredSize(new java.awt.Dimension(300, 765));
    setLayout(new java.awt.BorderLayout());

    dispatcherSP.setViewportView(dispatcherTable);

    dispatcherTable.setModel(locomotiveDispatcherTableModel);
    dispatcherTable.setDoubleBuffered(true);
    dispatcherTable.setDragEnabled(true);
    dispatcherTable.setRowSorter(new TableRowSorter<>(locomotiveDispatcherTableModel));
    dispatcherTable.setShowGrid(true);
    dispatcherTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        dispatcherTableMouseReleased(evt);
      }
    });
    dispatcherSP.setViewportView(dispatcherTable);

    add(dispatcherSP, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  private void dispatcherTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dispatcherTableMouseReleased
    int row = this.dispatcherTable.getSelectedRow();

    LocomotiveDispatcher dispatcher = locomotiveDispatcherTableModel.getBeanAt(row);
    Logger.trace("Selected " + dispatcher.getName() + " " + evt.getClickCount());
    if (evt.getClickCount() == 2) {
      showDriverCabDialog(dispatcher.getLocomotiveBean());
    }
  }//GEN-LAST:event_dispatcherTableMouseReleased

  private java.awt.Frame getParentFrame() {
    JFrame frame = (JFrame) SwingUtilities.getRoot(this);
    return frame;
  }

  private void showDriverCabDialog(LocomotiveBean locomotiveBean) {
    DriverCabDialog driverDialog = new DriverCabDialog(getParentFrame(), locomotiveBean, false);
    driverDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    driverDialog.pack();
    driverDialog.setLocationRelativeTo(null);
    driverDialog.setVisible(true);
    driverDialog.setResizable(false);
    driverDialog.toFront();
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane dispatcherSP;
  private javax.swing.JTable dispatcherTable;
  private jcs.ui.table.model.LocomotiveDispatcherTableModel locomotiveDispatcherTableModel;
  // End of variables declaration//GEN-END:variables

  public static void main(String args[]) {

    long now = System.currentTimeMillis();
    long maxtime = now + 5000L;
    AutoPilot.getInstance().startAutoPilot();
    int dispcnt = AutoPilot.getInstance().getLocomotiveDispatchers().size();
    while (dispcnt == 0 && now < maxtime) {
      now = System.currentTimeMillis();
      dispcnt = AutoPilot.getInstance().getLocomotiveDispatchers().size();
    }

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

    java.awt.EventQueue.invokeLater(() -> {

      DispatcherTablePanel testPanel = new DispatcherTablePanel();
      JFrame testFrame = new JFrame("LocomotiveTablePanel Tester");

      testFrame.add(testPanel);

      testFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
          System.exit(0);
        }
      });
      testFrame.pack();
      testFrame.setLocationRelativeTo(null);

      //testPanel.loadLocomotives();
      //testPanel.locomotiveDispatcherTableModel.refresh();
      testFrame.setVisible(true);
    });
  }
}
