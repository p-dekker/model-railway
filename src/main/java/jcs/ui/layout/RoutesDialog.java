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
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import jcs.JCS;
import jcs.entities.AccessoryBean;
import jcs.entities.RouteBean;
import jcs.entities.RouteElementBean;
import jcs.entities.TileBean;
import jcs.entities.TileBean.Orientation;
import jcs.persistence.PersistenceFactory;
import jcs.ui.layout.events.TileEvent;
import jcs.ui.layout.tiles.Switch;
import jcs.ui.layout.tiles.Tile;
import jcs.ui.layout.tiles.TileFactory;
import org.tinylog.Logger;

/**
 *
 * @author Frans Jacobs
 */
public class RoutesDialog extends javax.swing.JDialog {

  private final boolean readonly;
  private final List<RouteBean> routes;
  private RouteBean selectedRoute;

  private final LayoutCanvas layoutCanvas;

  private Color defaultRouteColor;

  private final ExecutorService executor;

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
    this.executor = Executors.newSingleThreadExecutor();

    initComponents();

    URL iconUrl = JCS.class.getResource("/media/jcs-train-64.png");
    if (iconUrl != null) {
      this.setIconImage(new ImageIcon(iconUrl).getImage());
    }

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

  void loadRoutes() {
    this.routes.clear();
    this.routes.addAll(PersistenceFactory.getService().getRoutes());
    String[] listData = new String[routes.size()];

    for (int i = 0; i < listData.length; i++) {
      listData[i] = routes.get(i).getId() + "";
    }
    this.routeList.setListData(listData);
  }

  private void routeLayout() {
    this.layoutCanvas.routeLayout();
    loadRoutes();
  }

  private void setSelectedRoute(RouteBean route) {
    if(selectedRoute != null) {
      resetRoute(selectedRoute.getRouteElements());
    }
    selectedRoute = route;
    
    if(selectedRoute != null) {
      
      Logger.trace("Setting Selected " + selectedRoute.toLogString());
      showRoute(selectedRoute.getRouteElements());
    }
    
    //this.layoutCanvas.setSelectRoute(route);
  }

  //private final Map<String, RouteElementBean> selectedRouteElements = new HashMap<>();

  private void resetRoute(List<RouteElementBean> routeElements) {
    for (RouteElementBean re : routeElements) {
      String tileId = re.getTileId();
      Orientation incomingSide = null;
      Color trackRouteColor = Tile.DEFAULT_TRACK_COLOR;

      TileEvent tileEvent;
      if (re.isTurnout()) {
        AccessoryBean.AccessoryValue routeState = AccessoryBean.AccessoryValue.OFF;
        tileEvent = new TileEvent(tileId, trackRouteColor, incomingSide, routeState, Tile.DEFAULT_TRACK_COLOR);
        Logger.trace("Tile: " + tileId + " Value: " + routeState + "; " + re);
      } else {
        tileEvent = new TileEvent(tileId, trackRouteColor, incomingSide);
      }
      TileFactory.fireTileEventListener(tileEvent);
    }
  }

  private void showRoute(List<RouteElementBean> routeElements) {
    for (RouteElementBean re : routeElements) {
      String tileId = re.getTileId();
      Orientation incomingSide = re.getIncomingOrientation();
      Color trackRouteColor = Color.black;

      TileEvent tileEvent;
      if (re.isTurnout()) {
        AccessoryBean.AccessoryValue routeState = re.getAccessoryValue();
        tileEvent = new TileEvent(tileId, trackRouteColor, incomingSide, routeState, Color.black);
        Logger.trace("Tile: " + tileId + " Value: " + routeState + "; " + re);
      } else {
        tileEvent = new TileEvent(tileId, trackRouteColor, incomingSide);
      }
      TileFactory.fireTileEventListener(tileEvent);
    }
  }

//    Map<String, RouteElementBean> routeSnapshot;
//    routeSnapshot = new HashMap<>(this.selectedRouteElements);
//
//    if (routeSnapshot.containsKey(tile.getId())) {
//      RouteElementBean re = routeSnapshot.get(tile.getId());
//      tile.setTrackRouteColor(Color.black, re.getIncomingOrientation());
//
//      if (tile.isJunction()) {
//        AccessoryBean.AccessoryValue av = re.getAccessoryValue();
//        ((Switch) tile).setRouteValue(av, Color.darkGray);
//        Logger.trace("Tile: " + tile.getId() + " Value: " + av + "; " + re);
//      } else {
//        tile.setTrackColor(Color.darkGray);
//      }
//    } else {
//      tile.setTrackRouteColor(null, null);
//
//      if (tile.isJunction()) {
//        ((Switch) tile).setRouteValue(AccessoryBean.AccessoryValue.OFF, Tile.DEFAULT_TRACK_COLOR);
//      } else {
//        tile.setTrackColor(Tile.DEFAULT_TRACK_COLOR);
//      }
//    }
//
//    tile.drawTile(g2, drawGrid);
//  }
//  public void showSelectRoute(RouteBean route) {
//    selectedRouteElements.clear();
//    if (route != null) {
//      List<RouteElementBean> rel = route.getRouteElements();
//      for (RouteElementBean re : rel) {
//        String id = re.getTileId();
//        TileBean.Orientation incomingSide = re.getIncomingOrientation();
//
//        //String nodeId = re.getNodeId();
//        selectedRouteElements.put(id, re);
//      }
//    }
//
//    //this.executor.execute(() -> repaint());
//  }

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
    setPreferredSize(new java.awt.Dimension(200, 790));

    toolbarPanel.setPreferredSize(new java.awt.Dimension(150, 40));
    java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
    flowLayout1.setAlignOnBaseline(true);
    toolbarPanel.setLayout(flowLayout1);

    routeToolBar.setRollover(true);
    routeToolBar.setPreferredSize(new java.awt.Dimension(150, 40));

    routeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/river-black.png"))); // NOI18N
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

    routeListSP.setAutoscrolls(true);
    routeListSP.setDoubleBuffered(true);
    routeListSP.setPreferredSize(new java.awt.Dimension(200, 700));
    routeListSP.setViewportView(routeList);

    routeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    routeList.setDoubleBuffered(true);
    routeList.setPreferredSize(new java.awt.Dimension(130, 600));
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
        //Logger.trace("Setting Selected " + selected.toLogString());
        //this.layoutCanvas.setSelectRoute(selected);

        setSelectedRoute(selected);
      }
    }//GEN-LAST:event_routeListValueChanged

    private void routeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_routeBtnActionPerformed
      if (this.layoutCanvas != null) {
        //this.layoutCanvas.routeLayout();
        this.executor.execute(() -> routeLayout());
        //loadRoutes();
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
      //PersistenceFactory.getService().removeAllBlocks();

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
