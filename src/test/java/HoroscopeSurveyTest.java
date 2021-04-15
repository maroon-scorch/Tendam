import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the methods in the HoroscopeSurvey.
 */
public class HoroscopeSurveyTest {
  Object data1;
  Object data2;
  HoroscopeSurvey survey1;
  HoroscopeSurvey survey2;

  /**
   * Builds a set of survey responses.
   */
  @Before
  public void setUp() {
    Map<String, Object> q1 = new HashMap<>();
    q1.put("What is your horoscope?", "Cancer");
    data1 = Collections.singletonList(q1);
    survey1 = new HoroscopeSurvey(q1);
    Map<String, Object> q2 = new HashMap<>();
    q2.put("What is your horoscope?", "Virgo");
    data2 = Collections.singletonList(q2);
    survey2 = new HoroscopeSurvey(q2);
  }

  /**
   * Clears the data and survey variables.
   */
  @After
  public void tearDown() {
    data1 = null;
    data2 = null;
    survey1 = null;
    survey2 = null;
  }

  /**
   * Returns the answers in a survey
   */
  @Test
  public void testGetAnswers() {
    setUp();
    Map<String, Object> q1 = new HashMap<>();
    q1.put("What is your horoscope?", "Cancer");
    assertEquals(q1, survey1.getAnswers());
    Map<String, Object> q2 = new HashMap<>();
    q2.put("What is your horoscope?", "Virgo");
    assertEquals(q2, survey2.getAnswers());
    tearDown();
  }

  /**
   * Tests the difference method for this survey.
   */
  @Test
  public void testDifference() {
    setUp();
    assertTrue(Math.abs(survey1.difference(survey2) - 100) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey1) - 0) < 0.001);
    tearDown();
  }
}
