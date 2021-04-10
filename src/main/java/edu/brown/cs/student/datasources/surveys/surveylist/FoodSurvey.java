package edu.brown.cs.student.datasources.surveys.surveylist;

import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.Surveys;

import java.util.ArrayList;
import java.util.List;

/**
 * The food survey.
 */
public class FoodSurvey implements Surveys {

  /*
  Survey that asks these questions, with the following options:
  1. What is your favorite fruit among these options?
   (Banana, Apple, Pear, Grapes, Watermelon, Strawberries, Blueberries)
   2. What is your least favorite fruit among this same list?
   3. What do you most enjoy having for breakfast?
   (Cereal, Oatmeal, PB&J, Eggs, Noodles)
   */

  private final String favoriteFruit;
  private final String leastFavoriteFruit;
  private final String favoriteBreakfast;

  /**
   * Public constructor for the food survey. Represents a set of survey responses.
   *
   * @param favoriteFruit      the chosen fruit for favoriteFruit
   * @param leastFavoriteFruit the chosen fruit for leastFavoriteFruit
   * @param favoriteBreakfast  the chosen breakfast for favorite breakfast
   */
  public FoodSurvey(String favoriteFruit, String leastFavoriteFruit, String favoriteBreakfast) {
    this.favoriteFruit = favoriteFruit;
    this.leastFavoriteFruit = leastFavoriteFruit;
    this.favoriteBreakfast = favoriteBreakfast;
  }

  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distance are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  public double difference(Source otherSource) {
    List<Object> values = this.valuesToList();
    List<Object> otherValues = otherSource.valuesToList();
    double difference = 100;
    for (int i=0; i < values.size(); i++) {
      if (values.get(i) == otherValues.get(i)) {
        difference = difference -  100.0 / (values.size());
      }
    }
    return difference;
  }

  /**
   * Converts a FoodSurvey instance into a list of its values.
   *
   * @return a list of objects
   */
  public List<Object> valuesToList() {
    List<Object> values = new ArrayList<>();
    values.add(this.favoriteFruit);
    values.add(this.leastFavoriteFruit);
    values.add(this.favoriteBreakfast);
    return values;
  }

}
