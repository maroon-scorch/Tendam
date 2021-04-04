package edu.brown.cs.student.tree;

import java.util.ArrayList;
import java.util.List;

public class Neighbor {

  private final String name;
  private final int id;
  private final double dist;


  /**
   * Constructor for the central Neighbor data type with stars.
   *
   * @param name name of the star
   * @param id   id of the star
   * @param dist distance between the reference star and this star
   */
  public Neighbor(String name, int id, double dist) {
    this.name = name;
    this.id = id;
    this.dist = dist;
  }

  /**
   * Converts an ArrayList of neighbors into an String of their IDs.
   *
   * @param listOfNeighbors list of neighbors procured through listNeighbors
   * @return String of neighbor IDs
   */
  public static String arrayToID(List<Neighbor> listOfNeighbors) {
    ArrayList<String> listOfID = new ArrayList<>();
    for (Neighbor datum : listOfNeighbors) {
      listOfID.add(Integer.toString(datum.getId()));
    }
    return String.join("\n", listOfID);
  }

  /**
   * Converts an ArrayList of neighbors into an String of their Names.
   *
   * @param listOfNeighbors list of neighbors procured through listNeighbors
   * @return String of neighbor names
   */
  public static String arrayToName(List<Neighbor> listOfNeighbors) {
    ArrayList<String> listOfNames = new ArrayList<>();
    for (Neighbor datum : listOfNeighbors) {
      listOfNames.add(datum.getName());
    }
    return String.join("\n", listOfNames);
  }

  /**
   * Accesses the ID of a Neighbor.
   *
   * @return an integer ID
   */
  public int getId() {
    return id;
  }

  /**
   * Accesses the Distance of the Neighbor.
   *
   * @return a double distance value
   */
  public double getDist() {
    return dist;
  }

  /**
   * Accesses the Name of the Neighbor.
   *
   * @return a string name
   */
  public String getName() {
    return name;
  }

}
