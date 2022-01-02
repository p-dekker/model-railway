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
package jcs.ui.layout2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.tinylog.Logger;

/**
 * This Panel is used to display the layout
 *
 * @author frans
 */
public class DisplayLayoutPanel extends JPanel {

    public static final int GRID_SIZE = Tile.GRID;

    //private final ExecutorService executor;
    /**
     * Creates new form GridsCanvas
     */
    public DisplayLayoutPanel() {
        //this.executor = Executors.newSingleThreadExecutor();
        initComponents();
        postInit();
    }

    private void postInit() {
        this.canvas.setDrawGrid(false);
    }

    public void loadLayout() {
        this.canvas.loadLayout();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvasScrollPane = new JScrollPane();
        canvas = new LayoutCanvas();

        setMinimumSize(new Dimension(1000, 160));
        setOpaque(false);
        setPreferredSize(new Dimension(1000, 775));
        setSize(new Dimension(1125, 775));
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new BorderLayout());

        canvasScrollPane.setDoubleBuffered(true);
        canvasScrollPane.setMinimumSize(new Dimension(110, 110));
        canvasScrollPane.setPreferredSize(new Dimension(1000, 700));
        canvasScrollPane.setViewportView(canvas);

        canvas.setMinimumSize(new Dimension(100, 100));
        canvas.setPreferredSize(new Dimension(895, 695));
        canvasScrollPane.setViewportView(canvas);

        add(canvasScrollPane, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    private void formComponentResized(ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        Logger.debug(evt.getComponent().getSize());// TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    public static void main(String args[]) {
        System.setProperty("trackServiceAlwaysUseDemo", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.error(ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("DisplayLayout Tester");
            DisplayLayoutPanel displayLayoutPanel = new DisplayLayoutPanel();
            displayLayoutPanel.loadLayout();
            f.add(displayLayoutPanel);

            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            f.pack();

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);

            f.setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LayoutCanvas canvas;
    private JScrollPane canvasScrollPane;
    // End of variables declaration//GEN-END:variables
}
