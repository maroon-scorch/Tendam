package edu.brown.cs.student.tree;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class that exists for the neighbor comparator offered.
 */
public class NeighborCompare implements Comparator<Neighbor>, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Empty constructor.
   */
  public NeighborCompare() {
  }

  /**
   * Comparator method for Neighbors.
   *
   * @param n1 neighbor 1
   * @param n2 neighbor 2
   * @return -1, 0, or 1 if n1 is less than, equal to, or greater than 1
   */
  @Override
  public int compare(Neighbor n1, Neighbor n2) {
    return Double.compare(n1.getDist(), n2.getDist());
  }
}

