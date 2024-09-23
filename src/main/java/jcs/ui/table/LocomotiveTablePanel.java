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
import java.util.ArrayList;
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
import jcs.entities.LocomotiveBean;
import jcs.persistence.PersistenceFactory;
import jcs.ui.DriverCabDialog;
import jcs.ui.table.model.LocomotiveBeanTableModel;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class LocomotiveTablePanel extends javax.swing.JPanel {

  /**
   * Creates new form LocomotiveTablePanel
   */
  public LocomotiveTablePanel() {
    locomotiveBeanTableModel = new LocomotiveBeanTableModel();

    initComponents();

    this.locomotiveTable.setDefaultRenderer(Image.class, new LocIconRenderer());

    this.locomotiveTable.getRowSorter().addRowSorterListener((RowSorterEvent e) -> {
      //Logger.trace(e.getType() + "," + e.getSource().getSortKeys());// Sorting changed
    });

    initModel();
  }

  private void initModel() {
    if (PersistenceFactory.getService() != null) {
      List<LocomotiveBean> activeLocos = new ArrayList<>();
      List<LocomotiveBean> allLocos = PersistenceFactory.getService().getLocomotives();
      for (LocomotiveBean loco : allLocos) {
        if (loco.isShow()) {
          activeLocos.add(loco);
        }
      }

      Logger.trace("In total there are " + allLocos.size() + " Locomotives of which there are " + activeLocos.size() + " shown");
      locomotiveBeanTableModel.setBeans(activeLocos);
    }
  }

  private class LocIconRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (value != null) {
        Image img = (Image) value;
        int size = 40;
        float aspect = (float) img.getHeight(null) / (float) img.getWidth(null);
        img = img.getScaledInstance(size, (int) (size * aspect), Image.SCALE_SMOOTH);
        BufferedImage bi = ImageUtil.toBuffered(img);
        setIcon(new ImageIcon(bi));
        setHorizontalAlignment(JLabel.CENTER);
        setText("");
      }
      return this;
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    locomotiveBeanTableModel = new jcs.ui.table.model.LocomotiveBeanTableModel();
    locomotiveSP = new javax.swing.JScrollPane();
    locomotiveTable = new javax.swing.JTable();

    setPreferredSize(new java.awt.Dimension(300, 765));
    setLayout(new java.awt.BorderLayout());

    locomotiveSP.setViewportView(locomotiveTable);

    locomotiveTable.setModel(locomotiveBeanTableModel);
    locomotiveTable.setDoubleBuffered(true);
    locomotiveTable.setDragEnabled(true);
    locomotiveTable.setRowSorter(new TableRowSorter<>(locomotiveBeanTableModel));
    locomotiveTable.setShowGrid(true);
    locomotiveTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        locomotiveTableMouseReleased(evt);
      }
    });
    locomotiveSP.setViewportView(locomotiveTable);

    add(locomotiveSP, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  private void locomotiveTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_locomotiveTableMouseReleased
    int row = this.locomotiveTable.getSelectedRow();

    LocomotiveBean loc = locomotiveBeanTableModel.getBeanAt(row);
    Logger.trace("Selected " + loc.getName() + " " + evt.getClickCount());
    if (evt.getClickCount() == 2) {
      showDriverCabDialog(loc);
    }
  }//GEN-LAST:event_locomotiveTableMouseReleased

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
  private jcs.ui.table.model.LocomotiveBeanTableModel locomotiveBeanTableModel;
  private javax.swing.JScrollPane locomotiveSP;
  private javax.swing.JTable locomotiveTable;
  // End of variables declaration//GEN-END:variables

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

    java.awt.EventQueue.invokeLater(() -> {

      LocomotiveTablePanel testPanel = new LocomotiveTablePanel();
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
      testPanel.locomotiveBeanTableModel.refresh();
      testFrame.setVisible(true);
    });
  }
}
