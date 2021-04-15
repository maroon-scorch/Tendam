import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the methods within the User class.
 */
public class UserTest {
  User user1;
  User user2;
  User user3;
  double threshold = 0.001;

  /**
   * Sets up a number of mock users.
   */
  @Before
  public void setUp() {
    user1 = new User("1", "name1", Arrays.asList("2", "3"));
    user2 = new User("2", "name2", Arrays.asList("1", "3"));
    user3 = new User("3", "name3", new ArrayList<>());
  }

  /**
   * Clears the data in the mock users.
   */
  @After
  public void tearDown() {
    user1 = null;
    user2 = null;
    user3 = null;
  }

  /**
   * Tests whether updateUserData works.
   */
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

  /**
   * Tests whether getMatches works.
   */
  @Test
  public void testGetMatches() {
    setUp();
    assertEquals(Arrays.asList("2", "3"), user1.getMatches());
    assertEquals(Arrays.asList("1", "3"), user2.getMatches());
    assertEquals(new ArrayList<>(), user3.getMatches());
    tearDown();
  }

  /**
   * Tests whether setMatches works.
   */
  @Test
  public void testSetMatches() {
    setUp();
    user3.setMatches(Collections.singletonList("1"));
    assertEquals(Collections.singletonList("1"), user3.getMatches());
    user1.setMatches(new ArrayList<>());
    assertEquals(new ArrayList<>(), user1.getMatches());
    tearDown();
  }

  /**
   * Tests whether calculateDistance works.
   */
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

  /**
   * Tests whether getName works.
   */
  @Test
  public void testGetName() {
    setUp();
    assertEquals("name1", user1.getName());
    assertEquals("name2", user2.getName());
    assertEquals("name3", user3.getName());
    tearDown();
  }

  /**
   * Tests whether setUserData works.
   */
  @Test
  public void testSetUserData() {
    setUp();
    Source survey = new FoodSurvey();
    Map<String, Source> data2 = new HashMap<>();
    data2.put("survey", survey);
    Source survey2 = new HoroscopeSurvey();
    data2.put("survey2", survey2);
    user1.setUserData(data2);
    assertEquals(data2, user1.getUserData());
    tearDown();
  }

  @Test
  public void testSetPreferences() {
    setUp();
    user1.setPreferences(Arrays.asList("2", "3"));
    assertEquals(Arrays.asList("2", "3"), user1.getPreferences());
    tearDown();
  }

  @Test
  public void testGetPreferences() {
    assertNull(user2.getPreferences());
    user2.setPreferences(Arrays.asList("1", "3"));
    assertEquals(Arrays.asList("1", "3"), user2.getPreferences());
  }

  /**
   * Tests user equality
   */
  @Test
  public void testEquals() {
    setUp();
    assertEquals(user1, user1);
    assertNotEquals(user1, user2);
    tearDown();
  }
}
