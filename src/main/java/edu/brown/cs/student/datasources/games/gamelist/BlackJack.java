package edu.brown.cs.student.datasources.games.gamelist;

import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.games.Games;

import java.util.Map;

/**
 * Class for implementing the processing of blackjack game data.
 */
public class BlackJack implements Games {

  // List of up to the 10 most recent scores
  private Double riskScore;

  /**
   * Public constructor.
   *
   * @param riskScore the risk propensity scores of the individual
   */
  public BlackJack(Double riskScore) {
    this.riskScore = riskScore;
  }

  /**
   * Empty Constructor.
   */
  public BlackJack() {
  }

  /**
   * Accesses the riskScore.
   *
   * @return a double risk score
   */
  public Double getScore() {
    return riskScore;
  }

  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distances are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  public double difference(Source otherSource) {
    Games castedScore = (Games) otherSource;
    return Math.abs(this.getScore()
            - (castedScore.getScore()));
  }

  /**
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a Source
   */
  @Override
  public Source convert(Object data) {
    Map<String, Object> preConvert = (Map<String, Object>) data;
    Double num = ((Number) preConvert.get("blackjack-score")).doubleValue();
    return new BlackJack(num);
  }

//  /**
//   * Converts the attached dataset into a list of objects
//   * (usually doubles or strings or a mixture).
//   *
//   * @return a list of objects
//   */
//  @Override
//  public List<Object> valuesToList() {
//    return Collections.singletonList(this.riskScores);
//  }
}
