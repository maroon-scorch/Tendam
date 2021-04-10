package edu.brown.cs.student.users;

import edu.brown.cs.student.datasources.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
  //id is shared between a user and a person
  private final Integer id;
  //not sure if the id is the same as the username
  private String userName;
  private Map<String, Source> userData;
  private List<Integer> friends;
  private String password;
  private String email;

  /**
   * Public constructor for users
   *
   * @param id       unique id
   * @param userName username, does not have to be unique
   * @param password string password
   * @param email    string email
   * @param friends  list of friend IDs
   */
  public User(Integer id, String userName, String password, String email, List<Integer> friends) {
    this.id = id;
    this.userName = userName;
    this.userData = new HashMap<>();
    this.friends = friends;
    this.password = password;
    this.email = email;
  }

  //method to add new surveys or games when one is created
  //this should be done for every user
  public void addNewSurvey(String surveyName) {
    userData.put(surveyName, null);
  }

  public void addSurveyData(String surveyName, Source surveyData) {
    this.userData.replace(surveyName, surveyData);
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return userName;
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
   * Updates an individual's username
   *
   * @param newName a string username
   */
  public void updateName(String newName) {
    this.userName = newName;
  }

  /**
   * Updates an individual's password
   *
   * @param newPassword a string password
   */
  public void updatePassword(String newPassword) {
    this.password = newPassword;
  }

  /**
   * Updates an individual's email
   *
   * @param newEmail a string email
   */
  public void updateEmail(String newEmail) {
    this.email = newEmail;
  }

  /**
   * Updates the individual's friends list with the id of a friend.
   *
   * @param friendID an integer id
   */
  public void addFriend(Integer friendID) {
    this.friends.add(friendID);
  }

  /**
   * Updates the individual's friends list by removing the id of a friend.
   *
   * @param friendID an integer id
   */
  public void removeFriend(Integer friendID) {
    this.friends.remove(friendID);
  }


}
