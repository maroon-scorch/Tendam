package edu.brown.cs.student.users;

/**
 * Supporting class for sorting amongst algorithm results.
 */
public class PairScore {

  private final String mainID;
  private final String optionID;
  private final double difference;

  /**
   * Public constructor for PairScore.
   *
   * @param mainID     the id of the main user
   * @param optionID   the id of the user they are tested
   *                   against for level of difference
   * @param difference the level of difference
   */
  public PairScore(String mainID, String optionID, double difference) {
    this.mainID = mainID;
    this.optionID = optionID;
    this.difference = difference;
  }

  /**
   * Accesses the mainID field in a PairScore.
   *
   * @return the id of the main user
   */
  public String getMainID() {
    return mainID;
  }

  /**
   * Accesses the optionID field in a PairScore.
   *
   * @return the id of the other user
   */
  public String getOptionID() {
    return optionID;
  }

  /**
   * Accesses the difference field in a PairScore.
   *
   * @return the difference between the two users
   */
  public double getDifference() {
    return difference;
  }
}
