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
package lan.wervel.jcs.ui.layout2.tiles2;

import java.awt.BasicStroke;
import lan.wervel.jcs.ui.layout.tiles.enums.Direction;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import lan.wervel.jcs.entities.TileBean;
import lan.wervel.jcs.entities.enums.Orientation;

/**
 * Draw a OccupancyDetector
 *
 * @author frans
 */
public class Block extends AbstractTile2 {

    private static int idSeq;

    public static final int BLOCK_WIDTH = DEFAULT_WIDTH * 3;
    public static final int BLOCK_HEIGHT = DEFAULT_HEIGHT * 3;

    public Block(TileBean tileBean) {
        super(tileBean);
        if (Orientation.EAST.equals(orientation) || Orientation.WEST.equals(orientation)) {
            this.width = BLOCK_WIDTH;
            this.height = DEFAULT_HEIGHT;
        } else {
            this.width = DEFAULT_WIDTH;
            this.height = BLOCK_HEIGHT;
        }
    }

    public Block(int x, int y) {
        this(Orientation.EAST, x, y);
    }

    public Block(Orientation orientation, int x, int y) {
        this(orientation, new Point(x, y));
    }

    public Block(Orientation orientation, Point center) {
        super(orientation, Direction.CENTER, center);
        if (Orientation.EAST.equals(orientation) || Orientation.WEST.equals(orientation)) {
            this.width = DEFAULT_WIDTH * 3;
            this.height = DEFAULT_HEIGHT;
        } else {
            this.width = DEFAULT_WIDTH;
            this.height = DEFAULT_HEIGHT * 3;
        }
    }

    @Override
    protected final String getNewId() {
        idSeq++;
        return "bk-" + idSeq;
    }

    @Override
    public Set<Point> getAltPoints() {
        int x = this.center.x;
        int y = this.center.y;
        Set<Point> alternatives = new HashSet<>();

        if (Orientation.EAST.equals(orientation) || Orientation.WEST.equals(orientation)) {
            //West
            Point wp = new Point((x - DEFAULT_WIDTH), y);
            Point ep = new Point((x + DEFAULT_WIDTH), y);
            alternatives.add(wp);
            alternatives.add(ep);
        } else {
            Point np = new Point(x, (y - DEFAULT_HEIGHT));
            Point sp = new Point(x, (y + DEFAULT_HEIGHT));
            alternatives.add(np);
            alternatives.add(sp);
        }

        return alternatives;
    }

    @Override
    public void rotate() {
        super.rotate();
        if (Orientation.EAST.equals(orientation) || Orientation.WEST.equals(orientation)) {
            this.width = DEFAULT_WIDTH * 3;
            this.height = DEFAULT_HEIGHT;
        } else {
            this.width = DEFAULT_WIDTH;
            this.height = DEFAULT_HEIGHT * 3;
        }

    }

    @Override
    public void renderTile(Graphics2D g2, Color trackColor, Color backgroundColor) {
        int x, y, w, h;

        x = 2;
        y = 10;
        w = DEFAULT_WIDTH * 3 - 4;
        h = 20;

        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2.setPaint(Color.darkGray);
        g2.drawRect(x, y, w, h);

        switch (this.orientation) {
            case EAST:
                drawRotate(g2, x + 3, GRID + 4, 0, "a");
                drawRotate(g2, w - 7, GRID + 4, 0, "b");
                break;
            case WEST:
                drawRotate(g2, x + 10, GRID - 4, 180, "a");
                drawRotate(g2, w - 1, GRID - 4, 180, "b");
                break;
            case NORTH:
                drawRotate(g2, x + 3, GRID - 4, 90, "a");
                drawRotate(g2, w - 10, GRID - 4, 90, "b");
                break;
            case SOUTH:
                drawRotate(g2, x + 10, GRID + 4, 270, "a");
                drawRotate(g2, w - 1, GRID + 4, 270, "b");
                break;
        }

        drawName(g2);
    }

    @Override
    public void drawName(Graphics2D g2d) {

        //int x, y;
        //x = 2;
        //y = 10;

        switch (this.orientation) {
            case EAST:
                drawRotate(g2d, 15, GRID + 4, 0, getId());
                break;
            case WEST:
                drawRotate(g2d, 105, GRID - 4, 180, getId());
                break;
            case NORTH:
                drawRotate(g2d, 20, GRID + 4, 0, getId());
                break;
            case SOUTH:
                drawRotate(g2d, 100, GRID - 4, 180, getId());
                break;
        }
    }

    @Override
    protected void setIdSeq(int id) {
        idSeq = id;
    }
}
