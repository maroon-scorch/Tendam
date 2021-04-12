package edu.brown.cs.student.users;

import edu.brown.cs.student.datasources.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a user and some of their most significant data.
 * Used primarily to run the algorithmic pipeline.
 */
public class User {
  //id is shared between a user and a person
  private String id;
  private String name;
  private Map<String, Source> userData;
  private List<String> matches;

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
    this.userData = new HashMap<>();
    this.matches = matches;
  }

  /**
   * No argument constructor used for FireBase serialization.
   */
  public User() {
  }

  //method to add new surveys or games when one is created
  //this should be done for every user
  public void addNewSurvey(String surveyName) {
    userData.put(surveyName, null);
  }

  public void addSurveyData(String surveyName, Source surveyData) {
    this.userData.replace(surveyName, surveyData);
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
   * Acceses the source data of a user.
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
   * Sets the userData field.
   *
   * @param data a map of string to source
   */
  public void updateUserData(Map<String, Source> data) {
    if (this.userData == null) {
      this.userData = data;
    } else {
      for (String key : data.keySet()) {
        this.userData.put(key, data.get(key));
      }
    }
  }

  /**
   * Accesses the existing matches of the character.
   *
   * @return a list of integers
   */
  public List<String> getMatches() {
    return matches;
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
    Map<String, Source> mapOfSources = this.userData;
    Map<String, Source> otherSources = otherUser.userData;
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
   * Updates an individual's username.
   *
   * @param newName a string username
   */
  public void updateName(String newName) {
    this.name = newName;
  }

//  /**
//   * Updates an individual's password
//   *
//   * @param newPassword a string password
//   */
//  public void updatePassword(String newPassword) {
//    this.password = newPassword;
//  }

//  /**
//   * Updates an individual's email
//   *
//   * @param newEmail a string email
//   */
//  public void updateEmail(String newEmail) {
//    this.email = newEmail;
//  }

  /**
   * Updates the individual's friends list with the id of a friend.
   *
   * @param friendID an integer id
   */
  public void addFriend(String friendID) {
    this.matches.add(friendID);
  }

  /**
   * Updates the individual's friends list by removing the id of a friend.
   *
   * @param friendID an integer id
   */
  public void removeFriend(Integer friendID) {
    this.matches.remove(friendID);
  }


}
