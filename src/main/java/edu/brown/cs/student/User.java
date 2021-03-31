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
  private final String userName;
  private Map<String, Double> surveys;
  private List<String> friends;

  public User(String id, String userName) {
    this.id = id;
    this.userName = userName;
    surveys = new HashMap<>();
    friends = new LinkedList<>();
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
