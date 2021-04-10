package edu.brown.cs.student.datasources;

import java.util.List;

public interface Source {

  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distance are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  double difference(Source otherSource);

  /**
   * Converts the attached Source into a list of objects (usually doubles or strings or a mixture)
   * @return a list of objects
   */
  List<Object> valuesToList();
}
