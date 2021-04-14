package edu.brown.cs.student.algorithm;

import java.util.List;

/**
 * An interface for anything that represents
 * a set of preferences or rankings.
 *
 * @param <T> generic parameter which has a set of preferences
 */
public interface HasRanking<T extends HasRanking<T>> {

  /**
   * Returns the HasRanking's rankings.
   *
   * @return a list of strings
   */
  List<String> getRankings();

  /**
   * Returns the string id.
   *
   * @return a string id
   */
  String getID();

  /**
   * Returns an index that a specified string appears at.
   *
   * @param obj a string item
   * @return an int index
   */
  int getRanking(String obj);
}
