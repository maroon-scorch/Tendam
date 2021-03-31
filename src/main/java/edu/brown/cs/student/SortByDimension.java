package edu.brown.cs.student;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Class that can be sorted by dimension.
 * @param <T> Object type that extends CoordinateGettable and implements comparator and serializable
 */
public class SortByDimension<T extends HasCoordinate> implements Comparator<T>, Serializable {
  private int dimension;
  //private int totalDimension;
  private static final long serialVersionUID = 1234L;

//  /**
//   * Creates a new SortByDimension object.
//   * @param dimension Integer representing current dimension
//   * @param totalDimension Integer representing the total dimension
//   */
  public SortByDimension(int dimension) {
    this.dimension = dimension;
    //this.totalDimension = totalDimension;
  }

  /**
   * Compares two objects based on their dimension.
   * @param obj1 First object
   * @param obj2 Second object
   * @return Positive number of first object is greater, 0 if same, negative if smaller
   */
  public int compare(T obj1, T obj2) {
    return Double.compare(
            obj1.getCoordinate()[this.dimension],
            obj2.getCoordinate()[this.dimension]);
  }
}
