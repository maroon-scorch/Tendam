import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the validity of the FoodSurvey methods.
 */
public class FoodSurveyTest {
  FoodSurvey survey1;
  FoodSurvey survey2;
  FoodSurvey survey3;
  FoodSurvey survey4;
  Map<String, Object> ans1 = new HashMap<>();
  Map<String, Object> ans2 = new HashMap<>();
  Map<String, Object> ans3 = new HashMap<>();
  Map<String, Object> ans4 = new HashMap<>();

  /**
   * Builds a set of survey responses.
   */
  @Before
  public void setUp() {
    ans1.put("What is your favorite fruit?", "Apple");
    ans1.put("What is your favorite vegetable?", "Carrot");
    survey1 = new FoodSurvey(ans1);
    ans2.put("What is your favorite fruit?", "Banana");
    ans2.put("What is your favorite vegetable?", "Carrot");
    survey2 = new FoodSurvey(ans2);
    ans3.put("What is your favorite fruit?", "Apple");
    ans3.put("What is your favorite vegetable?", "Potato");
    survey3 = new FoodSurvey(ans3);
    ans4.put("What is your favorite fruit?", "Banana");
    ans4.put("What is your favorite vegetable?", "Potato");
    survey4 = new FoodSurvey(ans4);

  }

  /**
   * Clears the survey responses.
   */
  @After
  public void tearDown() {
    survey1 = null;
    survey2 = null;
    survey3 = null;
    survey4 = null;
    ans1 = null;
    ans2 = null;
    ans3 = null;
    ans4 = null;
  }

  /**
   * Tests the getAnswers method.
   */
  @Test
  public void testGetAnswers() {
    assertEquals(ans1, survey1.getAnswers());
    assertEquals(ans2, survey2.getAnswers());
    assertEquals(ans3, survey3.getAnswers());
    assertEquals(ans4, survey4.getAnswers());
  }

  /**
   * Tests the difference method that calculates
   * the difference between two survey responses.
   */
  @Test
  public void testDifference() {
    assertTrue(Math.abs(survey1.difference(survey2) - 50) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey1) - 0) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey3) - 50) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey4) - 100) < 0.001);
  }
}

