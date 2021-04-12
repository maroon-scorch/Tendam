package edu.brown.cs.student.datasources.games;

import edu.brown.cs.student.datasources.Source;

public interface Games extends Source {

  /**
   * Accesses the score of a Game.
   *
   * @return a double score
   */
  Double getScore();
}
