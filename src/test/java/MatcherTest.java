import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.Matcher;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static edu.brown.cs.student.users.Matcher.createAllPreferences;
import static org.junit.Assert.*;

public class MatcherTest {
  List users = new ArrayList();
  User user1;
  User user2;
  User user3;

  @Before
  public void setUp() {
    Map<String, Object> q1 = new HashMap<>();
    q1.put("What is your horoscope?", "Cancer");
    HoroscopeSurvey survey1 = new HoroscopeSurvey(q1);
    Map<String, Object> q2 = new HashMap<>();
    q2.put("What is your horoscope?", "Virgo");
    HoroscopeSurvey survey2 = new HoroscopeSurvey(q2);
    Map<String, Source> horoscopeSurvey1 = new HashMap<>();
    horoscopeSurvey1.put("horoscope", survey1);
    Map<String, Source> horoscopeSurvey2 = new HashMap<>();
    horoscopeSurvey1.put("horoscope", survey2);
    user1 = new User("1", "name1", Arrays.asList("2"));
    user1.setUserData(horoscopeSurvey1);
    user2 = new User("2", "name2", Arrays.asList("1"));
    user2.setUserData(horoscopeSurvey2);
    user3 = new User("3", "name3", null);
    user3.setUserData(horoscopeSurvey1);
    users.add(user1);
    users.add(user2);
    users.add(user3);
  }

  @After
  public void tearDown() {
    users = null;
  }

  @Test
  public void testNullUserList() {
    setUp();
    assertThrows(CustomException.NoUsersException.class,
            () -> createAllPreferences(null));
    tearDown();
  }

  @Test
  public void testCreateAllPreferences() {
    List newUserList = new ArrayList();
    user1.setPreferences(Arrays.asList("3", "2"));
    user2.setPreferences(Arrays.asList("3", "1"));
    user3.setPreferences(Arrays.asList("1", "2"));
    newUserList.add(user1);
    newUserList.add(user2);
    newUserList.add(user3);
    try {
      assertEquals(Matcher.createAllPreferences(users), newUserList);
    } catch (CustomException.NoUsersException e) {
      e.printStackTrace();
      fail();
    }
    tearDown();
  }
}
