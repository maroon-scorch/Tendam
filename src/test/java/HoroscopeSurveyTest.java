import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HoroscopeSurveyTest {
  Object data1;
  Object data2;
  HoroscopeSurvey survey1;
  HoroscopeSurvey survey2;

  @Before
  public void setUp(){
    Map<String, Object> q1 = new HashMap<>();
    q1.put("What is your horoscope?", "Cancer");
    data1 = Arrays.asList(q1);
    survey1 = new HoroscopeSurvey(q1);
    Map<String, Object> q2 = new HashMap<>();
    q2.put("What is your horoscope?", "Virgo");
    data2 = Arrays.asList(q2);
    survey2 = new HoroscopeSurvey(q2);
  }

  @Test
  public void testGetAnswers() {
    Map<String, Object> q1 = new HashMap<>();
    q1.put("What is your horoscope?", "Cancer");
    assertEquals(survey1.getAnswers(), q1);
    Map<String, Object> q2 = new HashMap<>();
    q2.put("What is your horoscope?", "Virgo");
    assertEquals(survey2.getAnswers(), q2);
  }

  @Test
  public void testDifference() {
    assertTrue(Math.abs(survey1.difference(survey2) - 100) < 0.001);
    assertTrue(Math.abs(survey1.difference(survey1) - 0) < 0.001);
  }
}
