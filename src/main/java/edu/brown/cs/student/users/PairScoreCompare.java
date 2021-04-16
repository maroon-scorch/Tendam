package edu.brown.cs.student.users;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for PairScores.
 */
public class PairScoreCompare implements Comparator<PairScore>, Serializable {

  private static final long serialVersionUID = 100L;

  /**
   * Public constructor.
   */
  public PairScoreCompare() {
  }

  /**
   * Compares two PairScores.
   *
   * @param t1 The first PairScore
   * @param t2 The second PairScore
   * @return -1, 0, or 1 if t1 is less than, equal to, or greater than t2
   */
  @Override
  public int compare(PairScore t1, PairScore t2) {
    return Double.compare(t1.getDifference(), t2.getDifference());
  }
}
