package edu.brown.cs.student.datasources.games.gamelist;

import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.games.Games;

import java.util.Collections;
import java.util.List;

public class BlackJack implements Games {

  // List of up to the 10 most recent scores
  private List<Double> riskScores;

  /**
   * Public constructor.
   *
   * @param riskScores the risk propensity scores of the individual
   */
  public BlackJack(List<Double> riskScores) {
    this.riskScores = riskScores;
  }

  /**
   * Adds the newest score to the list of risk scores.
   *
   * @param riskScore the newest risk score
   */
  public void addScore(double riskScore) {
    if (this.riskScores.size() == 10) {
      this.riskScores.remove(0);
    }
    this.riskScores.add(riskScore);
  }

  /**
   * Provides a final score from the riskScores of an
   * individual's blackjack results.
   *
   * @return a double score
   */
  public double score(List<Object> listedValues) {
    double sum = 0;
    for (Object datum : listedValues) {
      sum += (double) datum;
    }
    return (sum / listedValues.size());
  }

  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distances are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  public double difference(Source otherSource) {
    return score(this.valuesToList())
            - score(otherSource.valuesToList());
  }

  /**
   * Converts the attached dataset into a list of objects (usually doubles or strings or a mixture)
   *
   * @return a list of objects
   */
  @Override
  public List<Object> valuesToList() {
    return Collections.singletonList(this.riskScores);
  }
}
