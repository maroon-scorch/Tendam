import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserTest {
  User user1;
  User user2;
  User user3;
  double threshold = 0.001;

  @Before
  public void setUp() {
    user1 = new User("1", "name1", Arrays.asList("2", "3"));
    user2 = new User("2", "name2", Arrays.asList("1", "3"));
    user3 = new User("3", "name3", new ArrayList<>());
  }

  @After
  public void tearDown() {
    user1 = null;
    user2 = null;
    user3 = null;
  }

  @Test
  public void testUpdateUserData() {
    setUp();
    Map<String, Source> data1 = new HashMap<>();
    user1.updateUserData(data1);
    assertEquals(user1.getUserData(), data1);
    Source survey = new FoodSurvey();
    Map<String, Source> data2 = new HashMap<>();
    data2.put("survey", survey);
    user1.updateUserData(data2);
    assertEquals(user1.getUserData(), data2);
    Source survey2 = new HoroscopeSurvey();
    data2.put("survey2", survey2);
    user1.updateUserData(data2);
    assertEquals(user1.getUserData(), data2);
    tearDown();
  }

  @Test
  public void testGetMatches() {
    setUp();
    assertEquals(user1.getMatches(), Arrays.asList("2", "3"));
    assertEquals(user2.getMatches(), Arrays.asList("1", "3"));
    assertEquals(user3.getMatches(), new ArrayList<>());
    tearDown();
  }

  @Test
  public void testSetMatches() {
    setUp();
    user3.setMatches(Arrays.asList("1"));
    assertEquals(user3.getMatches(), Arrays.asList("1"));
    user1.setMatches(new ArrayList<>());
    assertEquals(user1.getMatches(), new ArrayList<>());
    tearDown();
  }

  @Test
  public void testCalculateDistance() {
    setUp();
    assertTrue(Math.abs(user1.calcDist(user3) - 0) < threshold);
    Source survey = new FoodSurvey();
    Map<String, Source> data2 = new HashMap<>();
    data2.put("survey", survey);
    Source survey2 = new HoroscopeSurvey();
    data2.put("survey2", survey2);
    user1.updateUserData(data2);
    assertTrue(Math.abs(user1.calcDist(user3) - 0) < threshold);
    tearDown();
  }

  @Test
  public void testGetName() {
    setUp();
    assertEquals(user1.getName(), "name1");
    assertEquals(user2.getName(), "name2");
    assertEquals(user3.getName(), "name3");
  }

  @Test
  public void testSetUserData() {
    setUp();
    Source survey = new FoodSurvey();
    Map<String, Source> data2 = new HashMap<>();
    data2.put("survey", survey);
    Source survey2 = new HoroscopeSurvey();
    data2.put("survey2", survey2);
    user1.setUserData(data2);
    assertEquals(user1.getUserData(), data2);
  }

  @Test
  public void testSetPreferences() {
    user1.setPreferences(Arrays.asList("2", "3"));
    assertEquals(user1.getPreferences(), Arrays.asList("2", "3"));
  }

  @Test
  public void testGetPreferences() {
    assertEquals(user2.getPreferences(), new ArrayList<>());
    user2.setPreferences(Arrays.asList("1", "3"));
    assertEquals(user2.getPreferences(), Arrays.asList("1", "3"));
  }

  @Test
  public void testEquals() {
    assertTrue(user1.equals(user1));
    assertFalse(user1.equals("banana"));
    assertFalse(user1.equals(user2));
  }
}
