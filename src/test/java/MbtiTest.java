import edu.brown.cs.student.datasources.surveys.surveylist.MbtiSurvey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the methods within the MbtiSurvey class.
 */
public class MbtiTest {
  MbtiSurvey survey1;
  MbtiSurvey survey2;
  MbtiSurvey survey3;
  MbtiSurvey survey4;
  Map<String, Object> ans1 = new HashMap<>();
  Map<String, Object> ans2 = new HashMap<>();
  Map<String, Object> ans3 = new HashMap<>();
  Map<String, Object> ans4 = new HashMap<>();

  @Before
  public void setUp() {
    ans1.put("What is your favorite fruit?", "Apple");
    ans1.put("What is your favorite vegetable?", "Carrot");
    survey1 = new MbtiSurvey(ans1);
    ans2.put("What is your favorite fruit?", "Banana");
    ans2.put("What is your favorite vegetable?", "Carrot");
    survey2 = new MbtiSurvey(ans2);
    ans3.put("What is your favorite fruit?", "Apple");
    ans3.put("What is your favorite vegetable?", "Potato");
    survey3 = new MbtiSurvey(ans3);
    ans4.put("What is your favorite fruit?", "Banana");
    ans4.put("What is your favorite vegetable?", "Potato");
    survey4 = new MbtiSurvey(ans4);
  }

  /**
   * Clears the surveys and answers.
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
   * Tests the getAnswers() method.
   */
  @Test
  public void testGetAnswers() {
    setUp();
    assertEquals(ans1, survey1.getAnswers());
    assertEquals(ans2, survey2.getAnswers());
    assertEquals(ans3, survey3.getAnswers());
    assertEquals(ans4, survey4.getAnswers());
    tearDown();
  }

  /**
   * Tests the difference method.
   */
  @Test
  public void testDifference() {
    setUp();
    assertTrue(Math.abs(survey1.difference(survey2) - 50) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey1) - 0) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey3) - 50) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey4) - 100) < 0.001);
    tearDown();
  }
}


