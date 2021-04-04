package edu.brown.cs.student.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person implements HasRanking<Person> {
  private final Integer id;
  private List<Integer> preferences;

  public Person(Integer id, List<Integer> preferences) {
    this.id = id;
    this.preferences = preferences;
  }

  public void setPreferences(List<Integer> prefs) {
    this.preferences = prefs;
  }

  public List<Integer> getPreferences() {
    return preferences;
  }

  // TODO: is this necessary when the overrided getID exists?
  public Integer getId() {
    return id;
  }

  @Override
  public Integer getID() {
    return id;
  }

  @Override
  public List<Integer> getRankings() {
    return new ArrayList<Integer>(preferences);
  }

  @Override
  public int getRanking(Integer userID) {
    return preferences.indexOf(userID);
  }

  private Object[] getSigFields() {
    return new Object[]{id, preferences};
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Person)) {
      return false;
    }
    Person second = (Person) obj;
    for (int i = 0; i < this.getSigFields().length; i++) {
      if (!Objects.equals(this.getSigFields()[i], second.getSigFields()[i])) {
        return false;
      }
    }
    return true;
    // return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

//  @Override
//  public String toString() {
//    return id;
//  }
}
