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
package jcs.ui.layout.tiles;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import jcs.entities.AccessoryBean;
import jcs.entities.SensorBean;
import jcs.entities.TileBean;
import jcs.entities.enums.TileType;
import jcs.ui.layout.tiles.enums.Direction;
import jcs.entities.enums.Orientation;
import static jcs.entities.enums.TileType.BLOCK;
import static jcs.entities.enums.TileType.CROSS;
import static jcs.entities.enums.TileType.CURVED;
import static jcs.entities.enums.TileType.END;
import static jcs.entities.enums.TileType.SENSOR;
import static jcs.entities.enums.TileType.SIGNAL;
import org.tinylog.Logger;
import static jcs.entities.enums.TileType.STRAIGHT;
import static jcs.entities.enums.TileType.STRAIGHT_DIR;
import static jcs.entities.enums.TileType.SWITCH;

/**
 * Factory object to create Tiles
 *
 * @author frans
 */
public class TileFactory {

  private TileFactory() {}

  // Keep the records of the used id sequence number
  private static int straightIdSeq;
  private static int curvedIdSeq;
  private static int switchIdSeq;
  private static int crossIdSeq;
  private static int signalIdSeq;
  private static int sensorIdSeq;
  private static int blockIdSeq;
  private static int straightDirectionIdSeq;
  private static int endIdSeq;

  private static int getIdSeq(String id) {
    String idnr = id.substring(3);
    int idSeq = Integer.parseInt(idnr);
    return idSeq;
  }

  private static String getTileId(TileType tileType) {
    switch (tileType) {
      case STRAIGHT -> {
        straightIdSeq++;
        return "st-" + straightIdSeq;
      }
      case CURVED -> {
        curvedIdSeq++;
        return "ct-" + curvedIdSeq;
      }
      case SWITCH -> {
        switchIdSeq++;
        return "sw-" + switchIdSeq;
      }
      case CROSS -> {
        crossIdSeq++;
        return "cs-" + crossIdSeq;
      }
      case SIGNAL -> {
        signalIdSeq++;
        return "si-" + signalIdSeq;
      }
      case SENSOR -> {
        sensorIdSeq++;
        return "se-" + sensorIdSeq;
      }
      case BLOCK -> {
        blockIdSeq++;
        return "bk-" + blockIdSeq;
      }
      case STRAIGHT_DIR -> {
        straightDirectionIdSeq++;
        return "sd-" + straightDirectionIdSeq;
      }
      case END -> {
        endIdSeq++;
        return "et-" + endIdSeq;
      }
      default -> {
        Logger.warn("Unknown Tile Type " + tileType);
        return null;
      }
    }
  }

  private static int getHeighestIdSeq(int currentId, int newId) {
    if (currentId < newId) {
      return newId;
    } else {
      return currentId;
    }
  }

  public static Tile createTile(TileBean tileBean, boolean drawOutline, boolean showValues) {
    if (tileBean == null) {
      return null;
    }

    TileType tileType = tileBean.getTileType();
    AbstractTile tile = null;
    switch (tileType) {
      case STRAIGHT -> {
        tile = new Straight(tileBean);
        straightIdSeq = getHeighestIdSeq(straightIdSeq, getIdSeq(tileBean.getId()));
      }
      case CURVED -> {
        tile = new Curved(tileBean);
        curvedIdSeq = getHeighestIdSeq(curvedIdSeq, getIdSeq(tileBean.getId()));
      }
      case SWITCH -> {
        tile = new Switch(tileBean);
        switchIdSeq = getHeighestIdSeq(switchIdSeq, getIdSeq(tileBean.getId()));
        if (showValues && tileBean.getAccessoryBean() != null) {
          ((Switch) tile).setValue((tileBean.getAccessoryBean()).getAccessoryValue());
        }
      }
      case CROSS -> {
        tile = new Cross(tileBean);
        crossIdSeq = getHeighestIdSeq(crossIdSeq, getIdSeq(tileBean.getId()));
      }
      case SIGNAL -> {
        tile = new Signal(tileBean);
        signalIdSeq = getHeighestIdSeq(signalIdSeq, getIdSeq(tileBean.getId()));
        if (showValues && tileBean.getAccessoryBean() != null) {
          ((Signal) tile)
              .setSignalValue(((AccessoryBean) tileBean.getAccessoryBean()).getSignalValue());
        }
      }
      case SENSOR -> {
        tile = new Sensor(tileBean);
        sensorIdSeq = getHeighestIdSeq(sensorIdSeq, getIdSeq(tileBean.getId()));
        if (showValues && tileBean.getSensorBean() != null) {
          ((Sensor) tile).setActive(((SensorBean) tileBean.getSensorBean()).isActive());
        }
      }
      case BLOCK -> {
        tile = new Block(tileBean);
        blockIdSeq = getHeighestIdSeq(blockIdSeq, getIdSeq(tileBean.getId()));
      }
      case STRAIGHT_DIR -> {
        tile = new StraightDirection(tileBean);
        straightDirectionIdSeq =
            getHeighestIdSeq(straightDirectionIdSeq, getIdSeq(tileBean.getId()));
      }
      case END -> {
        tile = new End(tileBean);
        endIdSeq = getHeighestIdSeq(endIdSeq, getIdSeq(tileBean.getId()));
      }
      default -> Logger.warn("Unknown Tile Type " + tileType);
    }

    if (tile != null) {
      tile.setDrawOutline(drawOutline);
    }
    return (Tile) tile;
  }

  /**
   * @param tileType type of type to create
   * @param orientation whether the orientation of the Tile is EAST, WEST, NORTH or SOUTH
   * @param x the tile center X
   * @param y the tile center Y
   * @param drawOutline whether the outline of the tile must be rendered
   * @return a Tile object
   */
  public static Tile createTile(
      TileType tileType, Orientation orientation, int x, int y, boolean drawOutline) {
    return createTile(tileType, orientation, Direction.CENTER, x, y, drawOutline);
  }

  /**
   * @param tileType type of type to create
   * @param orientation whether the orientation of the Tile is EAST, WEST, NORTH or SOUTH
   * @param direction direction plays a role with Turnout tiles whether it goes to the Left or Right
   * @param x the tile center X
   * @param y the tile center Y
   * @param drawOutline whether the outline of the tile must be rendered
   * @return a Tile object
   */
  public static Tile createTile(
      TileType tileType,
      Orientation orientation,
      Direction direction,
      int x,
      int y,
      boolean drawOutline) {
    return createTile(tileType, orientation, direction, new Point(x, y), drawOutline);
  }

  public static Tile createTile(
      TileType tileType,
      Orientation orientation,
      Direction direction,
      Point center,
      boolean drawOutline) {
    Tile tile = null;
    switch (tileType) {
      case STRAIGHT -> {
        tile = new Straight(orientation, center);
      }
      case CURVED -> tile = new Curved(orientation, center);
      case SWITCH -> tile = new Switch(orientation, direction, center);
      case CROSS -> tile = new Cross(orientation, direction, center);
      case SIGNAL -> tile = new Signal(orientation, center);
      case SENSOR -> tile = new Sensor(orientation, center);
      case BLOCK -> tile = new Block(orientation, center);
      case STRAIGHT_DIR -> tile = new StraightDirection(orientation, center);
      case END -> tile = new End(orientation, center);
      default -> Logger.warn("Unknown Tile Type " + tileType);
    }

    if (tile != null) {
      tile.setDrawOutline(drawOutline);
      tile.setId(getTileId(tileType));
    }

    return (Tile) tile;
  }

  public static List<Tile> convert(
      List<TileBean> tileBeans, boolean drawOutline, boolean showValues) {
    List<Tile> tiles = new LinkedList<>();

    for (TileBean tileBean : tileBeans) {
      Tile tile = createTile(tileBean, drawOutline, showValues);
      tiles.add(tile);
    }
    return tiles;
  }
}
