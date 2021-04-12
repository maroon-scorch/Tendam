package edu.brown.cs.student.datasources.surveys.surveylist;

import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.Surveys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoroscopeSurvey implements Surveys {

  private final Map<String, Object> answers;

  public HoroscopeSurvey(Map<String, Object> answers) {
    this.answers = answers;
  }


  /**
   * Accesses the answers of a Source.
   *
   * @return a map of string to objects
   */
  public Map<String, Object> getAnswers() {
    return answers;
  }

  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distance are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  public double difference(Source otherSource) {
    List<Object> values = new ArrayList<>(this.getAnswers().values());
    Surveys castedSurvey = (Surveys) otherSource;
    List<Object> otherValues = new ArrayList<>(castedSurvey.getAnswers().values());
    double difference = 100;
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i) == otherValues.get(i)) {
        difference = difference - 100.0 / (values.size());
      }
    }
    return difference;
  }

  /**
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a list of objects
   */
  public Source convert(Object data) {
    Map<String, Object>[] converted = (Map<String, Object>[]) data;
    Map<String, Object> returnedMap = new HashMap<>();
    for (Map<String, Object> map : converted) {
      returnedMap.put(String.valueOf(map.get("question")), map.get("answer"));
    }
    return new FoodSurvey(returnedMap);
  }
}
