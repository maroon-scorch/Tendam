package edu.brown.cs.student.users;

import edu.brown.cs.student.algorithm.HasRanking;
import edu.brown.cs.student.datasources.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class representing a user and some of their most significant data.
 * Used primarily to run the algorithmic pipeline.
 */
public class User implements HasRanking<User> {

  private String id;
  private String name;
  private Map<String, Source> userData;
  private List<String> matches;
  private List<String> preferences;

  /**
   * Public constructor for users.
   *
   * @param id      unique id
   * @param name    username, does not have to be unique
   * @param matches list of friend IDs
   */
  public User(String id, String name, List<String> matches) {
    this.id = id;
    this.name = name;
    this.userData = null;
    this.matches = matches;
    this.preferences = null;
  }

  /**
   * Public no-arguments constructor.
   * Used for FireBase data to User.class conversion.
   */
  public User() {
  }

  /**
   * Sets the userData field.
   *
   * @param data a map of string to source
   */
  public void updateUserData(Map<String, Source> data) {
    if (this.userData == null || this.userData.isEmpty()) {
      this.userData = data;
    } else {
      for (Map.Entry<String, Source> m : data.entrySet()) {
        this.userData.put(m.getKey(), m.getValue());
      }
    }
  }

  /**
   * Accesses the existing matches of the character.
   *
   * @return a list of strings
   */
  public List<String> getMatches() {
    return matches;
  }

  /**
   * Sets the matches field of a user to the contents of toSet.
   *
   * @param toSet the list of strings to set matches to
   */
  public void setMatches(List<String> toSet) {
    this.matches = toSet;
  }

  /**
   * Calculates the distance, or how close each a user
   * is to another in terms of their data results.
   * Will always be between 0 and 100.
   *
   * @param otherUser the other User
   * @return a double distance
   */
  public double calcDist(User otherUser) {
    if (this.getUserData() == null) {
      this.setUserData(new HashMap<>());
    }
    if (otherUser.getUserData() == null) {
      otherUser.setUserData(new HashMap<>());
    }
    Map<String, Source> mapOfSources = this.getUserData();
    Map<String, Source> otherSources = otherUser.getUserData();
    List<String> keys = new ArrayList<>(mapOfSources.keySet());
    double totalDistance = 0;
    int numSurveysBothTaken = 0;
    for (String key : keys) {
      if (otherSources.containsKey(key)) {
        numSurveysBothTaken += 1;
        double thisDifference = mapOfSources.get(key).difference(
                otherSources.get(key));
        totalDistance += (thisDifference / numSurveysBothTaken);
      }
    }
    return totalDistance;
  }

  /**
   * Accesses the id of a user.
   *
   * @return integer id
   */
  public String getID() {
    return id;
  }

  /**
   * Accesses the name of a user.
   *
   * @return a string name
   */
  public String getName() {
    return name;
  }

  /**
   * Accesses the source data of a user.
   *
   * @return a map of string to source
   */
  public Map<String, Source> getUserData() {
    return userData;
  }

  /**
   * Sets the userData field.
   *
   * @param userData a map of string to source
   */
  public void setUserData(Map<String, Source> userData) {
    this.userData = userData;
  }

  /**
   * Sets the preferences for a Person.
   *
   * @param prefs a list of strings
   */
  public void setPreferences(List<String> prefs) {
    this.preferences = prefs;
  }

  /**
   * Returns the preferences for a Person.
   *
   * @return a list of strings
   */
  public List<String> getPreferences() {
    return preferences;
  }

  /**
   * Accesses the rankings of the Person.
   *
   * @return a list of strings
   */
  public List<String> getRankings() {
    return new ArrayList<>(preferences);
  }

  /**
   * Returns the index that a particular userID appears at.
   *
   * @param userID a string user id
   * @return an integer
   */
  public int getRanking(String userID) {
    return preferences.indexOf(userID);
  }

  /**
   * Returns the person's id and preferences. Used for setting equality.
   *
   * @return an array of objects
   */
  private Object[] getSigFields() {
    return new Object[]{id, preferences};
  }

  /**
   * Overrides equality for this class.
   *
   * @param obj a given object
   * @return a bool value
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof User)) {
      return false;
    }
    User second = (User) obj;
    return (this.getID().equals(second.getID()));
  }

  /**
   * Overrides hashCode equality for this class.
   *
   * @return an integer based on equality
   */
  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
