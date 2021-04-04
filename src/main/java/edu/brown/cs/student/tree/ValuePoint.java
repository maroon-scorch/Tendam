package edu.brown.cs.student.tree;

import edu.brown.cs.student.miscenllaneous.CustomException;

import java.util.List;

/**
 * Super Interface from which Stars and Nodes both inherit.
 */
public interface ValuePoint {

  /**
   * Returns the name of the valuePoint.
   *
   * @return a string name
   */
  Integer getName();

  /**
   * Given a valuePoint and a distance, returns a neighbor based on those attributes.
   *
   * @param currentDistance the given distance
   * @return a neighbor
   */
  Neighbor createNeighbor(double currentDistance);

  /**
   * Given a root valuePoint, returns an array of floats representing its dimensional points.
   *
   * @return an array of floats
   */
  float[] rootToArray();

  /**
   * Given a list of valuePoint data as well as an input Name,
   * returns the valuePoint where the name matches.
   *
   * @param inputData the given list of valuePoints
   * @param inputName the given name
   * @return the discovered valuePoint
   * @throws CustomException.ValuePointNotFoundException if the valuePoint cannot be found.
   */
  ValuePoint findWithNodeName(List<ValuePoint> inputData, String inputName)
          throws CustomException.ValuePointNotFoundException;

  /**
   * Given a valuePoint and a list, returns the index at which the valuePoint is present.
   * If it is not, returns -1.
   *
   * @param finalList the input list
   * @return an index
   */
  int whereIndex(List<Neighbor> finalList);

}

