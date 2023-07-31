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
package jcs.ui.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import jcs.entities.RouteBean;
import jcs.persistence.PersistenceFactory;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs
 */
public class RoutesDialog extends javax.swing.JDialog {

  private final boolean readonly;
  private final List<RouteBean> routes;

  private final LayoutCanvas layoutCanvas;

  private Color defaultRouteColor;

  /**
   * Creates new form RoutesDialog
   *
   * @param parent
   * @param modal
   * @param layoutCanvas
   * @param readonly
   */
  public RoutesDialog(java.awt.Frame parent, boolean modal, LayoutCanvas layoutCanvas, boolean readonly) {
    super(parent, modal);
    this.readonly = readonly;
    this.routes = new LinkedList<>();
    this.layoutCanvas = layoutCanvas;

    initComponents();

    if (this.readonly) {
      this.routeBtn.setEnabled(!readonly);
      this.routeBtn.setVisible(!readonly);

      this.deleteRoutesBtn.setEnabled(!readonly);
      this.deleteRoutesBtn.setVisible(!readonly);
    }

    if (this.defaultRouteColor == null) {
      this.defaultRouteColor = Color.darkGray;
    }
  }

  private void loadRoutes() {
    this.routes.clear();
    this.routes.addAll(PersistenceFactory.getService().getRoutes());
    String[] listData = new String[routes.size()];

    for (int i = 0; i < listData.length; i++) {
      //TODO
      listData[i] = routes.get(i).getId() + "";
    }
    this.routeList.setListData(listData);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolbarPanel = new javax.swing.JPanel();
        routeToolBar = new javax.swing.JToolBar();
        routeBtn = new javax.swing.JButton();
        deleteRoutesBtn = new javax.swing.JButton();
        routeListSP = new javax.swing.JScrollPane();
        routeList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Routes");

        toolbarPanel.setPreferredSize(new java.awt.Dimension(150, 40));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        toolbarPanel.setLayout(flowLayout1);

        routeToolBar.setRollover(true);
        routeToolBar.setPreferredSize(new java.awt.Dimension(150, 40));

        routeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/route-24.png"))); // NOI18N
        routeBtn.setToolTipText("Route the Layout");
        routeBtn.setFocusable(false);
        routeBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        routeBtn.setMaximumSize(new java.awt.Dimension(36, 36));
        routeBtn.setMinimumSize(new java.awt.Dimension(36, 36));
        routeBtn.setPreferredSize(new java.awt.Dimension(36, 36));
        routeBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        routeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                routeBtnActionPerformed(evt);
            }
        });
        routeToolBar.add(routeBtn);

        deleteRoutesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/delete-24.png"))); // NOI18N
        deleteRoutesBtn.setToolTipText("Delete all routes");
        deleteRoutesBtn.setFocusable(false);
        deleteRoutesBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteRoutesBtn.setMaximumSize(new java.awt.Dimension(36, 36));
        deleteRoutesBtn.setMinimumSize(new java.awt.Dimension(36, 36));
        deleteRoutesBtn.setPreferredSize(new java.awt.Dimension(36, 36));
        deleteRoutesBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteRoutesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRoutesBtnActionPerformed(evt);
            }
        });
        routeToolBar.add(deleteRoutesBtn);

        toolbarPanel.add(routeToolBar);

        getContentPane().add(toolbarPanel, java.awt.BorderLayout.NORTH);

        routeListSP.setDoubleBuffered(true);
        routeListSP.setPreferredSize(new java.awt.Dimension(145, 275));
        routeListSP.setViewportView(routeList);

        routeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        routeList.setDoubleBuffered(true);
        routeList.setPreferredSize(new java.awt.Dimension(130, 220));
        routeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                routeListValueChanged(evt);
            }
        });
        routeListSP.setViewportView(routeList);

        getContentPane().add(routeListSP, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void routeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_routeListValueChanged
      if (!evt.getValueIsAdjusting() && !this.routes.isEmpty() && this.routeList.getSelectedIndex() >= 0) {
        RouteBean selected = this.routes.get(this.routeList.getSelectedIndex());
        Logger.trace("Setting Selected " + selected.toLogString());
        this.layoutCanvas.setSelectRoute(selected);
      }
    }//GEN-LAST:event_routeListValueChanged

    private void routeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_routeBtnActionPerformed
      if (this.layoutCanvas != null) {
        this.layoutCanvas.routeLayout();

        loadRoutes();
      } else {
        Logger.warn("Can not perform routing as the LayoutPanel is null " + evt.paramString());
      }
    }//GEN-LAST:event_routeBtnActionPerformed

    private void deleteRoutesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRoutesBtnActionPerformed
      removeAllRoutes();
      loadRoutes();
      this.layoutCanvas.setSelectRoute(null);
    }//GEN-LAST:event_deleteRoutesBtnActionPerformed

  public static Point getLocationOnCurrentScreen(final Component c) {
    if (c != null) {
      final Rectangle currentScreenBounds = c
              .getGraphicsConfiguration().getBounds();
      return new Point(currentScreenBounds.x, currentScreenBounds.y);
    } else {/*  www.jav  a  2  s . c o m*/
      return new Point(0, 0);
    }
  }

  private void removeAllRoutes() {
    if (PersistenceFactory.getService() != null) {
      List<RouteBean> rl = PersistenceFactory.getService().getRoutes();
      for (RouteBean r : rl) {
        PersistenceFactory.getService().remove(r);
      }
    }
  }

  @Override
  public void setVisible(boolean b) {
    if (b) {
      loadRoutes();

      pack();
      Point p = this.layoutCanvas.getLocationOnScreen();

      Logger.trace("Canvas P  x: " + p.x + " y: " + p.y);
      Logger.trace("Canvas S  w: " + this.layoutCanvas.getWidth() + " h: " + this.layoutCanvas.getHeight());

      Point d = new Point(p.x + this.layoutCanvas.getWidth() + 30, p.y);
      this.setLocation(d);
    } else {
      this.layoutCanvas.setSelectRoute(null);

    }

    super.setVisible(b);
  }

  @Override
  public void dispose() {
    this.layoutCanvas.setSelectRoute(null);
    super.dispose();
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteRoutesBtn;
    private javax.swing.JButton routeBtn;
    private javax.swing.JList<String> routeList;
    private javax.swing.JScrollPane routeListSP;
    private javax.swing.JToolBar routeToolBar;
    private javax.swing.JPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables
}
