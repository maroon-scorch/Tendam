import edu.brown.cs.student.algorithm.GaleShapley;
import edu.brown.cs.student.algorithm.PropertyBasedTesting;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the gale shapley algorithm.
 */
public class GaleShapleyTest {
  List<User> group1;
  List<User> group2;

  /**
   * Sets up the trie using a few sample words.
   */
  @Before
  public void setUp() {
    User m1 = new User("1", "1", new ArrayList<>());
    m1.setPreferences(List.of("15", "13", "14", "12", "16"));
    User m2 = new User("2", "2", new ArrayList<>());
    m2.setPreferences(List.of("16", "14", "13", "12", "15"));
    User m3 = new User("3", "3", new ArrayList<>());
    m3.setPreferences(List.of("13", "16", "12", "15", "14"));
    User m4 = new User("4", "4", new ArrayList<>());
    m4.setPreferences(List.of("16", "13", "15", "14", "12"));
    User m5 = new User("5", "5", new ArrayList<>());
    m5.setPreferences(List.of("15", "12", "13", "14", "16"));

    User w1 = new User("12", "12", new ArrayList<>());
    w1.setPreferences(List.of("4", "2", "5", "3", "1"));
    User w2 = new User("13", "13", new ArrayList<>());
    w2.setPreferences(List.of("2", "1", "4", "3", "5"));
    User w3 = new User("14", "14", new ArrayList<>());
    w3.setPreferences(List.of("1", "3", "5", "4", "2"));
    User w4 = new User("15", "15", new ArrayList<>());
    w4.setPreferences(List.of("4", "1", "3", "2", "5"));
    User w5 = new User("16", "16", new ArrayList<>());
    w5.setPreferences(List.of("2", "5", "1", "3", "4"));

    group1 = List.of(m1, m2, m3, m4, m5);
    group2 = List.of(w1, w2, w3, w4, w5);
  }

  /**
   * Resets the Trie.
   */
  @After
  public void tearDown() {
    group1 = null;
    group2 = null;
  }

  /**
   * General hard-written test between two groups of people
   */
  @Test
  public void testGaleShapleyAlgo() {
    User m1 = new User("1", "1", new ArrayList<>());
    m1.setPreferences(List.of("15", "13", "14", "12", "16"));
    User m2 = new User("2", "2", new ArrayList<>());
    m2.setPreferences(List.of("16", "14", "13", "12", "15"));
    User m3 = new User("3", "3", new ArrayList<>());
    m3.setPreferences(List.of("13", "16", "12", "15", "14"));
    User m4 = new User("4", "4", new ArrayList<>());
    m4.setPreferences(List.of("16", "13", "15", "14", "12"));
    User m5 = new User("5", "5", new ArrayList<>());
    m5.setPreferences(List.of("15", "12", "13", "14", "16"));

    User w1 = new User("12", "12", new ArrayList<>());
    w1.setPreferences(List.of("4", "2", "5", "3", "1"));
    User w2 = new User("13", "13", new ArrayList<>());
    w2.setPreferences(List.of("2", "1", "4", "3", "5"));
    User w3 = new User("14", "14", new ArrayList<>());
    w3.setPreferences(List.of("1", "3", "5", "4", "2"));
    User w4 = new User("15", "15", new ArrayList<>());
    w4.setPreferences(List.of("4", "1", "3", "2", "5"));
    User w5 = new User("16", "16", new ArrayList<>());
    w5.setPreferences(List.of("2", "5", "1", "3", "4"));
    Map<User, User> pairings = Map.of(m1, w4, m2, w5, m3,
            w3, m4, w2, m5, w1);
    // Map<User, User> pairings = Map.of("A", "O", "B", "P", "C", "N", "D", "M", "E", "L");
    Map<User, User> testPairings = GaleShapley.galeShapleyAlgo(group1, group2);

    assertEquals(pairings, testPairings);
  }

  /**
   * Tests a list of users paired against themselves.
   */
  @Test
  public void testAlgoSelf() {
    User p1 = new User("1", "1", new ArrayList<>());
    p1.setPreferences(List.of("2", "5", "4", "3", "1"));
    User p2 = new User("2", "2", new ArrayList<>());
    p2.setPreferences(List.of("1", "3", "4", "5", "2"));
    User p3 = new User("3", "3", new ArrayList<>());
    p3.setPreferences(List.of("2", "5", "1", "4", "3"));
    User p4 = new User("4", "4", new ArrayList<>());
    p4.setPreferences(List.of("5", "1", "3", "2", "4"));
    User p5 = new User("5", "5", new ArrayList<>());
    p5.setPreferences(List.of("3", "2", "1", "4", "5"));
    List<User> people = List.of(p1, p2, p3, p4, p5);

    Map<User, User> pairings = Map.of(p1, p2, p2, p1,
            p3, p5, p4, p4, p5, p3);
    Map<User, User> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  /**
   * Scenario where one user has a shorter
   * list of preferences for whatever reason.
   */
  @Test
  public void testAlgoSelfLimitedPreference() {
    User p1 = new User("1", "1", new ArrayList<>());
    p1.setPreferences(List.of("2", "5"));
    User p2 = new User("2", "2", new ArrayList<>());
    p2.setPreferences(List.of("4", "1", "3"));
    User p3 = new User("3", "3", new ArrayList<>());
    p3.setPreferences(List.of("4", "5", "2"));
    User p4 = new User("4", "4", new ArrayList<>());
    p4.setPreferences(List.of("5", "3", "2"));
    User p5 = new User("5", "5", new ArrayList<>());
    p5.setPreferences(List.of("1", "4", "3"));

    List<User> people = List.of(p1, p2, p3, p4, p5);

    Map<User, User> pairings = Map.of(p1, p2, p4,
            p5, p5, p4, p2, p1);
//    Map<Integer, Integer> pairings = Map.of(p1.getID(), p2.getID(), p4.getID(), p5.getID(),
//            p5.getID(), p4.getID(), p2.getID(), p1.getID());
    Map<User, User> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  /**
   * Edge cases where users have no preferences
   */
  @Test
  public void edgeCase() {
    User p1 = new User("1", "1", new ArrayList<>());
    User p2 = new User("2", "2", new ArrayList<>());
    User p3 = new User("3", "3", new ArrayList<>());
    User p4 = new User("4", "4", new ArrayList<>());
    User p5 = new User("5", "5", new ArrayList<>());

    List<User> people = List.of(p1, p2, p3, p4, p5);

    Map<User, User> pairings = new HashMap<>();
    Map<User, User> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  /**
   * Runs property based tests.
   */
  @Test
  public void galeShapleyPBT() {
    PropertyBasedTesting pbt = null;
    try {
      pbt = new PropertyBasedTesting();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    assertTrue(pbt.nTrials(50, 100));
  }

//
//  // PBT
//
//  /**
//   * Generate a list of preferences for ids numbered 0 (inclusive) to n (exclusive).
//   *
//   * All items will be included in this preference list.
//   *
//   * @param n an upper bound for User ids
//   * @return a list of preferences
//   */
//  public static List<Integer> prefsList(int n) {
//    Random r = new Random();
//    int nElements = r.nextInt(n + 1);
//    List<Integer> preferences = IntStream.range(0, n).boxed().collect(Collectors.toList());
//    Collections.shuffle(preferences);
//    return preferences;
//    // If the preference list can be shortened:
//    // return preferences.subList(0, nElements);
//  }
//
//  /**
//   * Generates n people with preferences for each other.
//   * @param n the number of people to generate
//   * @return a map of id -> User with n elements
//   */
//  public Map<String, User> generatePeople(int n) {
//    Map<String, User> UserList = new HashMap<>();
//    for (int i = 0; i < n; i++) {
//      List<Integer> prefs = prefsList(n).stream().map(Integer::valueOf).collect(Collectors.toList());
//      UserList.put(String.valueOf(i), new User(i, prefs));
//    }
//    return UserList;
//  }
//
//  /**
//   * Assert that all choosing and chosen objects are paired.
//   *
//   * @param choosing a list of objects which choose elements
//   * @param chosen a list of objects which can be chosen
//   * @param pairings the map of choosing -> chosen pairings
//   * @param <T> the type of hasRanking paired
//   */
//  public <T extends HasRanking<T>> void assertAllPaired(Collection<T> choosing, Collection<T> chosen,
//                                                        Map<T, T> pairings) {
//    // Check that all choosers and chosen are paired up as expected
//    Set<Integer> choosingIds = choosing.stream().map(HasRanking::getID).collect(Collectors.toSet());
//    Set<Integer> chosenIds = chosen.stream().map(HasRanking::getID).collect(Collectors.toSet());
//    Set<Integer> actualChoosingIds =
//        pairings.keySet().stream().map(HasRanking::getID).collect(Collectors.toSet());
//    Set<Integer> actualChosenIds =
//        pairings.values().stream().map(HasRanking::getID).collect(Collectors.toSet());
//    assertEquals(choosingIds, actualChoosingIds);
//    assertEquals(chosenIds, actualChosenIds);
//  }
//
//  /**
//   * Asserts whether all pairings are stable.
//   *
//   * @param chosens an id -> hasRanking map of choosable elements
//   * @param pairings the chooser -> chosen map of pairings
//   * @param <T> the type of hasRanking paired
//   */
//  public <T extends HasRanking<T>> void assertStable(Map<String, T> chosens,
//                                                     Map<T, T> pairings) {
//    // First construct the reverse mapping
//    Map<T, T> reversePairings = new HashMap<>();
//    for (Map.Entry<T, T> entry : pairings.entrySet()) {
//      reversePairings.put(entry.getValue(), entry.getKey());
//    }
//    // Check every pairing
//    for (Map.Entry<T, T> entry : pairings.entrySet()) {
//      T choosing = entry.getKey();
//      T chosen = entry.getValue();
//      int rankingLimit = choosing.getRanking(chosen.getID());
//      // Check all with a better preference to see if they match
//      List<Integer> betterChoiceIDs = choosing.getRankings().subList(0, rankingLimit);
//      for (Integer betterID : betterChoiceIDs) {
//        T betterChoice = chosens.get(betterID);
//        T currentChooser = reversePairings.get(betterChoice);
//        // Assert that betterChoice's currentChooser is as preferred than the current
//        int currentRanking = betterChoice.getRanking(currentChooser.getID());
//        int testRanking = betterChoice.getRanking(choosing.getID());
//        assertTrue(currentRanking <= testRanking);
//      }
//    }
//  }
//
//  @Test
//  public void testProperties() {
//    final Random r = new Random();
//    final int ITERATIONS = 1000;
//    final int SIZE_BOUND = 15;
//    for (int i = 0; i < ITERATIONS; i++) {
//      int size = r.nextInt(SIZE_BOUND);
//      Map<String, User> choosing = generatePeople(size);
//      Map<String, User> chosen = generatePeople(size);
//      Map<User, User> pairs = GaleShapley
//          .galeShapleyAlgo(new ArrayList<>(choosing.values()), new ArrayList<>(chosen.values()));
//      assertAllPaired(choosing.values(), chosen.values(), pairs);
//      assertStable(chosen, pairs);
//    }
//  }
}
