package edu.brown.cs.student.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class for dealing with the People class instances
 * that are plugged into the gale shapley algorithm.
 */
public class Person {
//  private final String id;
//  private List<String> preferences;
//
//  public Person(String id, List<String> preferences) {
//    this.id = id;
//    this.preferences = preferences;
//  }
//
//  /**
//   * Sets the preferences for a Person.
//   *
//   * @param prefs a list of strings
//   */
//  public void setPreferences(List<String> prefs) {
//    this.preferences = prefs;
//  }
//
//  /**
//   * Returns the preferences for a Person.
//   *
//   * @return a list of strings
//   */
//  public List<String> getPreferences() {
//    return preferences;
//  }
//
//  /**
//   * Returns the ID of a Person.
//   *
//   * @return a string id
//   */
//  @Override
//  public String getID() {
//    return id;
//  }
//
//  /**
//   * Accesses the rankings of the Person.
//   *
//   * @return a list of strings
//   */
//  @Override
//  public List<String> getRankings() {
//    return new ArrayList<>(preferences);
//  }
//
//  /**
//   * Returns the index that a particular userID appears at.
//   *
//   * @param userID a string user id
//   * @return an integer
//   */
//  @Override
//  public int getRanking(String userID) {
//    return preferences.indexOf(userID);
//  }
//
//
//  // TODO: bad practice.
//
//  /**
//   * returns the person's id and preferences.
//   *
//   * @return an array of objects
//   */
//  private Object[] getSigFields() {
//    return new Object[]{id, preferences};
//  }
//
//  /**
//   * Overrides equality for this class.
//   *
//   * @param obj a given object
//   * @return a bool value
//   */
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) {
//      return true;
//    }
//    if (!(obj instanceof Person)) {
//      return false;
//    }
//    Person second = (Person) obj;
//    for (int i = 0; i < this.getSigFields().length; i++) {
//      if (!Objects.equals(this.getSigFields()[i], second.getSigFields()[i])) {
//        return false;
//      }
//    }
//    return true;
//  }
//
//  /**
//   * Overrides hashCode equality for this class.
//   *
//   * @return an integer based on equality
//   */
//  @Override
//  public int hashCode() {
//    return id.hashCode();
//  }
}
