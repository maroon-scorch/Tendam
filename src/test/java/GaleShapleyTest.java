import edu.brown.cs.student.algorithm.GaleShapley;
import edu.brown.cs.student.algorithm.Person;
import edu.brown.cs.student.algorithm.PropertyBasedTesting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GaleShapleyTest {
  List<Person> males;
  List<Person> females;

  /**
   * Sets up the trie using a few sample words.
   */
  @Before
  public void setUp() {
    Person m1 = new Person("1", List.of("15", "13", "14", "12", "16"));
    Person m2 = new Person("2", List.of("16", "14", "13", "12", "15"));
    Person m3 = new Person("3", List.of("13", "16", "12", "15", "14"));
    Person m4 = new Person("4", List.of("16", "13", "15", "14", "12"));
    Person m5 = new Person("5", List.of("15", "12", "13", "14", "16"));

    Person w1 = new Person("12", List.of("4", "2", "5", "3", "1"));
    Person w2 = new Person("13", List.of("2", "1", "4", "3", "5"));
    Person w3 = new Person("14", List.of("1", "3", "5", "4", "2"));
    Person w4 = new Person("15", List.of("4", "1", "3", "2", "5"));
    Person w5 = new Person("16", List.of("2", "5", "1", "3", "4"));
    males = List.of(m1, m2, m3, m4, m5);
    females = List.of(w1, w2, w3, w4, w5);
  }

  /**
   * Resets the Trie.
   */
  @After
  public void tearDown() {
    males = null;
    females = null;
  }

  @Test
  public void testGaleShapleyAlgo() {
    Person m1 = new Person("1", List.of("15", "13", "14", "12", "16"));
    Person m2 = new Person("2", List.of("16", "14", "13", "12", "15"));
    Person m3 = new Person("3", List.of("13", "16", "12", "15", "14"));
    Person m4 = new Person("4", List.of("16", "13", "15", "14", "12"));
    Person m5 = new Person("5", List.of("15", "12", "13", "14", "16"));

    Person w1 = new Person("12", List.of("4", "2", "5", "3", "1"));
    Person w2 = new Person("13", List.of("2", "1", "4", "3", "5"));
    Person w3 = new Person("14", List.of("1", "3", "5", "4", "2"));
    Person w4 = new Person("15", List.of("4", "1", "3", "2", "5"));
    Person w5 = new Person("16", List.of("2", "5", "1", "3", "4"));
    Map<Person, Person> pairings = Map.of(m1, w4, m2, w5, m3, w3, m4, w2, m5, w1);
    // Map<Person, Person> pairings = Map.of("A", "O", "B", "P", "C", "N", "D", "M", "E", "L");
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(males, females);

    assertEquals(pairings, testPairings);
  }

  @Test
  public void testAlgoSelf() {
    Person p1 = new Person("1", List.of("2", "5", "4", "3", "1"));
    Person p2 = new Person("2", List.of("1", "3", "4", "5", "2"));
    Person p3 = new Person("3", List.of("2", "5", "1", "4", "3"));
    Person p4 = new Person("4", List.of("5", "1", "3", "2", "4"));
    Person p5 = new Person("5", List.of("3", "2", "1", "4", "5"));
    List<Person> people = List.of(p1, p2, p3, p4, p5);

    Map<Person, Person> pairings = Map.of(p1, p2, p2, p1,
            p3, p5, p4, p4, p5, p3);
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  @Test
  public void testAlgoSelfLimitedPreference() {
    Person p1 = new Person("1", List.of("2", "5"));
    Person p2 = new Person("2", List.of("4", "1", "3"));
    Person p3 = new Person("3", List.of("4", "5", "2"));
    Person p4 = new Person("4", List.of("5", "3", "2"));
    Person p5 = new Person("5", List.of("1", "4", "3"));

    List<Person> people = List.of(p1, p2, p3, p4, p5);

    Map<Person, Person> pairings = Map.of(p1, p2, p4, p5, p5, p4, p2, p1);
//    Map<Integer, Integer> pairings = Map.of(p1.getID(), p2.getID(), p4.getID(), p5.getID(),
//            p5.getID(), p4.getID(), p2.getID(), p1.getID());
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  @Test
  public void edgeCase() {
    Person p1 = new Person("1", new ArrayList<>());
    Person p2 = new Person("2", new ArrayList<>());
    Person p3 = new Person("3", new ArrayList<>());
    Person p4 = new Person("4", new ArrayList<>());
    Person p5 = new Person("5", new ArrayList<>());

    List<Person> people = List.of(p1, p2, p3, p4, p5);

    Map<Person, Person> pairings = new HashMap<>();
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  @Test
  public void galeShapleyPBT() {
    PropertyBasedTesting pbt = new PropertyBasedTesting();
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
//   * @param n an upper bound for person ids
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
//   * @return a map of id -> Person with n elements
//   */
//  public Map<String, Person> generatePeople(int n) {
//    Map<String, Person> personList = new HashMap<>();
//    for (int i = 0; i < n; i++) {
//      List<Integer> prefs = prefsList(n).stream().map(Integer::valueOf).collect(Collectors.toList());
//      personList.put(String.valueOf(i), new Person(i, prefs));
//    }
//    return personList;
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
//      Map<String, Person> choosing = generatePeople(size);
//      Map<String, Person> chosen = generatePeople(size);
//      Map<Person, Person> pairs = GaleShapley
//          .galeShapleyAlgo(new ArrayList<>(choosing.values()), new ArrayList<>(chosen.values()));
//      assertAllPaired(choosing.values(), chosen.values(), pairs);
//      assertStable(chosen, pairs);
//    }
//  }
}
