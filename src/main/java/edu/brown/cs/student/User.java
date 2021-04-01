package edu.brown.cs.student;

import edu.brown.cs.student.KdTree.Dimensionable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

public class User implements Dimensionable {
  //id is shared between a user and a person
  private final String id;
  //not sure if the id is the same as the username
  private  String userName;
  private Map<String, Double> surveys;
  private List<String> friends;
  private  String password;
  private String email;

  public User(String id, String userName, String password, String email, List<String> friends) {
    this.id = id;
    this.userName = userName;
    this.surveys = new HashMap<>();
    this.friends = friends;
    this.password = password;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  //method to add new surveys or games when one is created
  //this should be done for every user
  public void addNewSurvey(String surveyName) {
    surveys.put(surveyName, null);
  }

  public void addSurveyData(String surveyName, double surveyData) {
    surveys.replace(surveyName, surveyData);
  }

  public void addFriends(String friendID) {
    friends.add(friendID);
  }

  //todo this needs to be changed, if we end up using dimensionable interface
  //it is there as filler for now
  //theoretically this class should go into the kdtree and
  //generate a person with list of preferences
  @Override
  public Double getDimension(int dim) {
    return null;
  }

  @Override
  public Double getDist(List<Double> location) throws DataFormatException {
    return null;
  }
}
