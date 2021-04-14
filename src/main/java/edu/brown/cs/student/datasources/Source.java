package edu.brown.cs.student.datasources;

/**
 * Interface for the Sources of data that the
 * matching algorithm uses to produce preference lists for users.
 */
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
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a Source object
   */
  Source convert(Object data);
}
