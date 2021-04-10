package edu.brown.cs.student.users;

public class PairScore {

  private final int mainID;
  private final int optionID;
  private final double difference;

  public PairScore(int mainID, int optionID, double difference) {
    this.mainID = mainID;
    this.optionID = optionID;
    this.difference = difference;
  }

  /**
   * Accesses the mainID field in a PairScore.
   *
   * @return the id of the main user
   */
  public int getMainID() {
    return mainID;
  }

  /**
   * Accesses the optionID field in a PairScore.
   *
   * @return the id of the other user
   */
  public int getOptionID() {
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
