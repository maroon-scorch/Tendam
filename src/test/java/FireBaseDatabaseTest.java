import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.games.gamelist.BlackJack;
import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.MbtiSurvey;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the validity of the FireBase connection.
 */
public class FireBaseDatabaseTest {
  FireBaseDatabase fb;


  /**
   * Establishes a FireBase connection.
   */
  @Before
  public void setUp() {
    try {
      fb = new FireBaseDatabase();
    } catch (CustomException.NoDatabaseLoadedException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * Disconnects the FireBase instance.
   */

  @After
  public void tearDown() {
    fb = null;
  }

  /**
   * Tests whether basic access to the "users" collection
   * is possible from the FireBase connection.
   */
  @Test
  public void testGetUsers() {
    User newUser = new User("newUser1", "newUser1", null);
    List<User> userList = new ArrayList<>();
    userList.add(newUser);
    try {
      List<User> userList1 = fb.retrieveUsers("onlyNewUsers");
      assertEquals(userList, userList1);
    } catch (CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * tests the retrieveSourceData method
   */
  @Test
  public void testRetrieveSourceData() {
    Map<String, String> horoscope = new HashMap<>();
    horoscope.put("answer", "Virgo");
    horoscope.put("question", "What is your horoscope?");
    horoscope.put("key", "horoscope");
    Map<String, Object> q1 = new HashMap<>();
    q1.put("horoscope", Collections.singletonList(horoscope));
    Map<String, Map<String, Object>> expectedData = new HashMap<>();
    expectedData.put("PersonA", q1);
    try {
      Map<String, Map<String, Object>> sourceData = fb.retrieveSourceData("surveysCopy");
      assertEquals(expectedData, sourceData);
    } catch (CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
    try {
      Map<String, Map<String, Object>> sourceData = fb.retrieveSourceData("thisDoesntExist");
      assertEquals(new HashMap<>(), sourceData);
    } catch (CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * tests the merge method using data from horoscope survey only
   * @throws NoSuchMethodException when method cannot be found
   */
  @Test
  public void testMergeHoroscopeData() throws NoSuchMethodException {
    User userA = new User("PersonA", "PersonA", Collections.singletonList("PersonB"));
    User userB = new User("PersonB", "PersonB", Collections.singletonList("PersonA"));
    List<User> userList = new ArrayList<>();
    Map<String, String> horoscope = new HashMap<>();
    horoscope.put("answer", "Virgo");
    horoscope.put("question", "What is your horoscope?");
    horoscope.put("key", "horoscope");
    Map<String, Source> expectedData = new HashMap<>();
    HoroscopeSurvey hc = new HoroscopeSurvey();
    expectedData.put("PersonA", hc.convert(Collections.singletonList(horoscope)));
    userA.updateUserData(expectedData);
    userList.add(userA);
    userList.add(userB);
    try {
      List<User> sourceData = fb.merge(fb.retrieveUsers("userCopy"),
              fb.retrieveSourceData("surveyCopy"), "Survey");
      assertEquals(userList, sourceData);
    } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * tests merge method when there is no data in the provided paths
   */
  @Test
  public void testMergeNoData() {
    try {
      assertEquals(new ArrayList<>(), fb.merge(fb.retrieveUsers("path"),
              fb.retrieveSourceData("path"), "Games"));
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * tests the merge method when there are multiple different surveys
   */
  @Test
  public void testMergeMultipleSurveys() {
    User userA = new User("PersonA", "PersonA", Collections.singletonList("PersonB"));
    User userB = new User("PersonB", "PersonB", Collections.singletonList("PersonA"));
    List<User> userList = new ArrayList<>();
    Map<String, String> horoscope = new HashMap<>();
    horoscope.put("answer", "Virgo");
    horoscope.put("question", "What is your horoscope?");
    horoscope.put("key", "horoscope");
    Map<String, Source> expectedData1 = new HashMap<>();
    HoroscopeSurvey hc = new HoroscopeSurvey();
    expectedData1.put("PersonA", hc.convert(Collections.singletonList(horoscope)));
    Map<String, String> q1 = new HashMap<>();
    q1.put("answer", "Apple");
    q1.put("key", "Q1");
    q1.put("question", "What is your favorite fruit?");
    Map<String, String> q2 = new HashMap<>();
    q2.put("answer", "Carrot");
    q2.put("key", "Q2");
    q2.put("question", "What is your favorite vegetable?");
    FoodSurvey fs = new FoodSurvey();
    Map<String, Source> expectedData2 = new HashMap<>();
    expectedData2.put("PersonA", fs.convert(Arrays.asList(q1, q2)));
    Map<String, String> mbti = new HashMap<>();
    mbti.put("answer", "Blue");
    horoscope.put("question", "What is your favorite color?");
    horoscope.put("key", "color");
    Map<String, Source> expectedData3 = new HashMap<>();
    MbtiSurvey ms = new MbtiSurvey();
    expectedData3.put("PersonA", ms.convert(Collections.singletonList(mbti)));
    userA.updateUserData(expectedData1);
    userA.updateUserData(expectedData2);
    userA.updateUserData(expectedData3);
    userList.add(userA);
    userList.add(userB);
    try {
      List<User> sourceData = fb.merge(fb.retrieveUsers("userCopy"),
              fb.retrieveSourceData("surveyCopy2"), "Survey");
      assertEquals(userList, sourceData);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

//  @Test
//  public void testMergeGames() {
//    User userA = new User("PersonA", "PersonA", Collections.singletonList("PersonB"));
//    User userB = new User("PersonB", "PersonB", Collections.singletonList("PersonA"));
//    List<User> userList = new ArrayList<>();
//    BlackJack bg = new BlackJack();
//    Map<String, Object> blackjack1 = new HashMap<>();
//    blackjack1.put("blackjack-games-played", 0);
//    blackjack1.put("blackjack-score", -1);
//    blackjack1.put("id", "PersonA");
//    Map<String, Source> expectedData1 = new HashMap<>();
//    expectedData1.put("PersonB", bg.convert(blackjack1));
//    Map<String, Object> blackjack2 = new HashMap<>();
//    blackjack2.put("blackjack-games-played", 2);
//    blackjack2.put("blackjack-score", 58.8316);
//    blackjack2.put("id", "PersonB");
//    Map<String, Source> expectedData2 = new HashMap<>();
//    expectedData2.put("PersonB", bg.convert(blackjack2));
//    userA.updateUserData(expectedData1);
//    userB.updateUserData(expectedData2);
//    userList.add(userA);
//    userList.add(userB);
//    try {
//      List<User> sourceData = fb.merge(fb.retrieveUsers("userCopy"),
//              fb.retrieveSourceData("gameCopyForTest"), "Game");
//      assertEquals(userList, sourceData);
//    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | CustomException.FutureBreakException e) {
//      e.printStackTrace();
//      fail();
//    }
//  }

}
