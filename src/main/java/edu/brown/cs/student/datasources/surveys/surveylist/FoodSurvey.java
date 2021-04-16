package edu.brown.cs.student.datasources.surveys.surveylist;

import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.Surveys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The food survey.
 */
public class FoodSurvey implements Surveys {

  private static final double MAGIC100 = 100.0;

  private Map<String, Object> answers;

  /**
   * Public constructor for the food survey. Represents a set of survey responses.
   *
   * @param answers the user's questions and answers to the questions
   */
  public FoodSurvey(Map<String, Object> answers) {
    this.answers = answers;
  }

  /**
   * Empty constructor.
   */
  public FoodSurvey() {

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
//    List<Object> otherValues = otherSource.valuesToList();
    double difference = MAGIC100;
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i) == otherValues.get(i)) {
        difference = difference - MAGIC100 / (values.size());
      }
    }
    return difference;
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
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a list of objects
   */
  public Source convert(Object data) {
    List<Map<String, Object>> converted = (List<Map<String, Object>>) data;
    Map<String, Object> returnedMap = new HashMap<>();
    for (Map<String, Object> map : converted) {
      returnedMap.put(String.valueOf(map.get("question")), map.get("answer"));
    }
    return new FoodSurvey(returnedMap);
  }
}
