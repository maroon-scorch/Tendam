package edu.brown.cs.student.KdTree;

import java.util.List;
import java.util.zip.DataFormatException;

/**
 * An interface that indicates an object can be used by a KDTree (had dimensions).
 */
public interface Dimensionable {

  /**
   * Returns the value associated with the given dimension.
   *
   * @param dim - the current dimension
   * @return the value associated with the dimension
   */
  Double getDimension(int dim);

  /**
   * Returns the distance of the object from the given location.
   *
   * @param location - to find the distance from
   * @return the distance of the object from the location
   * @throws DataFormatException if unable to parse location data
   */
  Double getDist(List<Double> location) throws DataFormatException;
}
