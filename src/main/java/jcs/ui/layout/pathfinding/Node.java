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
package jcs.ui.layout.pathfinding;

import java.util.LinkedList;
import java.util.List;
import jcs.entities.enums.AccessoryValue;
import jcs.entities.enums.Orientation;
import jcs.entities.enums.TileType;
import jcs.ui.layout.Tile;
import org.tinylog.Logger;

/**
 * A Node is the representation in the Graph of a tile.
 *
 * Every tile each tile represents a rail. The rail are drawn in the middle of the tile, so in case of a horizontal straight they connect in the middle of the west and east sides. So the edge
 * connection points should match on every connected tile/node
 *
 */
public class Node {

  private final Tile tile;
  private final TileType tileType;
  private final Orientation orientation;
  private AccessoryValue accessoryState;

  private final String suffix;

  private Node parent;
  private final List<Edge> edges;

  public Node(Tile tile) {
    this(tile, null);
  }

  public Node(Tile tile, String suffix) {
    this.tile = tile;
    this.tileType = tile.getTileType();
    this.orientation = tile.getOrientation();
    this.suffix = suffix;
    this.edges = new LinkedList<>();
  }

  public int getX() {
    return tile.getCenterX();
  }

  public int getGridX() {
    return tile.getGridX();
  }

  public int getY() {
    return tile.getCenterY();
  }

  public int getGridY() {
    return tile.getGridY();
  }

  public boolean isBlock() {
    return TileType.BLOCK == this.tileType;
  }

  public String getSuffix() {
    return this.suffix;
  }

//  public boolean isHorizontal() {
//    return this.tile.isHorizontal();
//  }
//  public boolean isVertical() {
//    return this.tile.isVertical();
//  }
//  public boolean isDiagonal() {
//    return this.tile.isDiagonal();
//  }
  public TileType getTileType() {
    return tileType;
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public AccessoryValue getAccessoryState() {
    return accessoryState;
  }

  public void setAccessoryState(AccessoryValue accessoryState) {
    this.accessoryState = accessoryState;
  }

  private boolean isSwitch(Node other) {
    //Is other on the switch of the Turnout
    //There should alway be a parent!
    int px = this.parent.getGridX();
    int py = this.parent.getGridY();

    int tx = this.getGridX();
    int ty = this.getGridY();

    Orientation oo = this.tile.getNeighborOrientations().get(other.tile.getCenter());

    //Determine the traverse direction based on the parent
    if (px == tx && py != ty) {
      //vertical, then the other must also be in line so must be on the N or S side
      return (Orientation.NORTH == oo || Orientation.SOUTH == oo) && this.orientation == oo;
    } else if (px != tx && py == ty) {
      //horizontal
      return (Orientation.WEST == oo || Orientation.EAST == oo) && this.orientation == oo;
    } else {
      return false;
    }
  }

  private boolean isDiverging(Node other) {
    //The is other on the diverging side of the turnout
    //There should alway be a parent!
    int px = this.parent.getGridX();
    int py = this.parent.getGridY();

    int tx = this.getGridX();
    int ty = this.getGridY();

    Orientation oo = this.tile.getNeighborOrientations().get(other.tile.getCenter());

    //Determine the traverse direction based on the parent
    if (px == tx && py != ty) {
      //vertical, then the other must not be in line so must be on the E or W side
      return (Orientation.EAST == oo || Orientation.WEST == oo);
    } else if (px != tx && py == ty) {
      //horizontal
      return (Orientation.NORTH == oo || Orientation.SOUTH == oo);
    } else {
      return false;
    }
  }

  private boolean isStraight(Node other) {
    //Is Other on the straight side of the turnout
    //There should alway be a parent!
    int px = this.parent.getGridX();
    int py = this.parent.getGridY();

    int tx = this.getGridX();
    int ty = this.getGridY();

    Orientation oo = this.tile.getNeighborOrientations().get(other.tile.getCenter());

    if (px == tx && py != ty) {
      //vertical
      return (Orientation.NORTH == oo || Orientation.SOUTH == oo) && this.orientation != oo;
    } else if (px != tx && py == ty) {
      //horizontal
      return (Orientation.WEST == oo || Orientation.EAST == oo) && this.orientation != oo;
    } else {
      return false;
    }
  }
  
  
  private AccessoryValue getPathDirection(String fromId, String toId) {
    for (Edge e : this.edges) {
      Logger.trace(e);
    }


    for (Edge e : this.edges) {
      if (fromId.equals(e.getFromId()) && toId.equals(e.getToId())) {
        return e.getPathDirection();
      }
    }
    return null;
  }

  public boolean canTravelTo(Node other) {
    if (other == null) {
      return false;
    }
    if (this.tile.isJunction()) {
      AccessoryValue pathDir = getPathDirection(getId(), other.getId());
      Logger.trace("Path " + (this.parent != null ? " From: " + parent.getId() + " via " + getId() : "") + " O: " + this.orientation + " " + pathDir + " to " + other.getId());
      //Check is the full path is possible

      boolean isParentConnectedToSwitch = this.isSwitch(this.parent);
      boolean isParentConnectedToStraight = this.isStraight(this.parent);
      //boolean isParentDiverging = this.isDiverging(this.parent);

      //Logger.trace("Parent (from) " + this.parent.getId() + " connected to switch: " + isParentConnectedToSwitch + " connected to straight: " + isParentConnectedToStraight + " diverging: " + isParentDiverging);
      boolean isOtherConnectedToSwitch = this.isSwitch(other);
      boolean isOtherConnectedToStraight = this.isStraight(other);
      boolean isOtherDiverging = this.isDiverging(other);

      //Logger.trace("Other (To) " + other.getId() + " connected to switch: " + isOtherConnectedToSwitch + " connected to straight: " + isOtherConnectedToStraight + " diverging: " + isOtherDiverging);
      if (isParentConnectedToSwitch && (isOtherDiverging || isOtherConnectedToStraight)) {
        //       Logger.trace("Route from " + parent.getId()+ " via " + getId()+ " to " + other.getId()+" is possible using "+(isOtherDiverging?AccessoryValue.RED:AccessoryValue.GREEN)+" from edge: "+pathDir );

        return this.tile.isAdjacent(other.tile);
      } else if (isParentConnectedToStraight && isOtherConnectedToSwitch) {
        //       Logger.trace("Route from " + parent.getId()+ " via " + getId()+ " to " + other.getId()+" is possible using "+(isOtherDiverging?AccessoryValue.RED:AccessoryValue.GREEN)+" from edge: "+pathDir );
        return this.tile.isAdjacent(other.tile);
      } else {
        //       Logger.trace("Route from " + parent.getId()+ " via " + getId()+ " to " + other.getId()+" is NOT possible");
        return false;
      }
    } else {
      return this.tile.isAdjacent(other.tile);
    }
  }

  public String getId() {
    return this.tile.getId() + (this.suffix != null ? this.suffix : "");
  }

  public String getTileId() {
    return this.tile.getId();
  }

  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public boolean isJunction() {
    return this.tile.isJunction();
  }

  public boolean contains(Edge edge) {
    List<Edge> snaphot = new LinkedList<>(this.edges);

    for (Edge e : snaphot) {
      if (e.getFromId().equals(edge.getFromId()) && e.getToId().equals(edge.getToId())) {
        return true;
      }
    }
    return false;
  }

  public Tile getTile() {
    return tile;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Node: ");
    sb.append(getId());
    for (Edge edge : this.edges) {
      sb.append("; ");
      sb.append(edge.toString());
    }
    return sb.toString();
    //return "Node{" + "id=" + tile.getId() + '}';
  }

}
