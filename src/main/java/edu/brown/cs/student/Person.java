package edu.brown.cs.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person implements hasRanking<Person> {
  private final String id;
  private List<String> preferences;

  public Person(String id, List<String> preferences) {
    this.id = id;
    this.preferences = preferences;
  }

  public void setPreferences(List<String> prefs) {
    this.preferences = prefs;
  }

  public List<String> getPreferences() {
    return preferences;
  }

  public String getId() {
    return id;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public List<String> getRankings() {
    return new ArrayList<>(preferences);
  }

  @Override
  public int getRanking(String userID) {
    return preferences.indexOf(userID);
  }

  private Object[] getSigFields() {
    Object[] result = {id, preferences};
    return result;
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

  @Override
  public String toString() {
    return id;
  }
}
