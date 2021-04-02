import edu.brown.cs.student.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GaleShapleyTest {
  List<Person> males;
  List<Person> females;

  /**
   * Sets up the trie using a few sample words.
   */
  @Before
  public void setUp() {
    Person m1 = new Person("A", List.of("O", "M", "N", "L", "P"));
    Person m2 = new Person("B", List.of("P", "N", "M", "L", "O"));
    Person m3 = new Person("C", List.of("M", "P", "L", "O", "N"));
    Person m4 = new Person("D", List.of("P", "M", "O", "N", "L"));
    Person m5 = new Person("E", List.of("O", "L", "M", "N", "P"));

    Person w1 = new Person("L", List.of("D", "B", "E", "C", "A"));
    Person w2 = new Person("M", List.of("B", "A", "D", "C", "E"));
    Person w3 = new Person("N", List.of("A", "C", "E", "D", "B"));
    Person w4 = new Person("O", List.of("D", "A", "C", "B", "E"));
    Person w5 = new Person("P", List.of("B", "E", "A", "C", "D"));
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
    Person m1 = new Person("A", List.of("O", "M", "N", "L", "P"));
    Person m2 = new Person("B", List.of("P", "N", "M", "L", "O"));
    Person m3 = new Person("C", List.of("M", "P", "L", "O", "N"));
    Person m4 = new Person("D", List.of("P", "M", "O", "N", "L"));
    Person m5 = new Person("E", List.of("O", "L", "M", "N", "P"));

    Person w1 = new Person("L", List.of("D", "B", "E", "C", "A"));
    Person w2 = new Person("M", List.of("B", "A", "D", "C", "E"));
    Person w3 = new Person("N", List.of("A", "C", "E", "D", "B"));
    Person w4 = new Person("O", List.of("D", "A", "C", "B", "E"));
    Person w5 = new Person("P", List.of("B", "E", "A", "C", "D"));
    Map<Person, Person> pairings = Map.of(m1, w4, m2, w5, m3, w3, m4, w2, m5, w1);
    // Map<Person, Person> pairings = Map.of("A", "O", "B", "P", "C", "N", "D", "M", "E", "L");
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(males, females);

    assertEquals(pairings, testPairings);
  }

  @Test
  public void testAlgoSelf() {
    Person p1 = new Person("A", List.of("B", "E", "D", "C", "A"));
    Person p2 = new Person("B", List.of("A", "C", "D", "E", "B"));
    Person p3 = new Person("C", List.of("B", "E", "A", "D", "C"));
    Person p4 = new Person("D", List.of("E", "A", "C", "B", "D"));
    Person p5 = new Person("E", List.of("C", "B", "A", "D", "E"));
    List<Person> people = List.of(p1, p2, p3, p4, p5);

    Map<Person, Person> pairings = Map.of(p1, p2, p2, p1, p3, p5, p4, p4, p5, p3);
    Map<Person, Person> testPairings = GaleShapley.galeShapleyAlgo(people, people);
    assertEquals(pairings, testPairings);
  }

  // PBT

  /**
   * Generate a list of preferences for ids numbered 0 (inclusive) to n (exclusive).
   *
   * All items will be included in this preference list.
   *
   * @param n an upper bound for person ids
   * @return a list of preferences
   */
  public static List<Integer> prefsList(int n) {
    Random r = new Random();
    int nElements = r.nextInt(n + 1);
    List<Integer> preferences = IntStream.range(0, n).boxed().collect(Collectors.toList());
    Collections.shuffle(preferences);
    return preferences;
    // If the preference list can be shortened:
    // return preferences.subList(0, nElements);
  }

  /**
   * Generates n people with preferences for each other.
   * @param n the number of people to generate
   * @return a map of id -> Person with n elements
   */
  public Map<String, Person> generatePeople(int n) {
    Map<String, Person> personList = new HashMap<>();
    for (int i = 0; i < n; i++) {
      List<String> prefs = prefsList(n).stream().map(String::valueOf).collect(Collectors.toList());
      personList.put(String.valueOf(i), new Person(String.valueOf(i), prefs));
    }
    return personList;
  }

  /**
   * Assert that all choosing and chosen objects are paired.
   *
   * @param choosing a list of objects which choose elements
   * @param chosen a list of objects which can be chosen
   * @param pairings the map of choosing -> chosen pairings
   * @param <T> the type of hasRanking paired
   */
  public <T extends hasRanking<T>> void assertAllPaired(Collection<T> choosing, Collection<T> chosen,
                                                        Map<T, T> pairings) {
    // Check that all choosers and chosen are paired up as expected
    Set<String> choosingIds = choosing.stream().map(hasRanking::getID).collect(Collectors.toSet());
    Set<String> chosenIds = chosen.stream().map(hasRanking::getID).collect(Collectors.toSet());
    Set<String> actualChoosingIds =
        pairings.keySet().stream().map(hasRanking::getID).collect(Collectors.toSet());
    Set<String> actualChosenIds =
        pairings.values().stream().map(hasRanking::getID).collect(Collectors.toSet());
    assertEquals(choosingIds, actualChoosingIds);
    assertEquals(chosenIds, actualChosenIds);
  }

  /**
   * Asserts whether all pairings are stable.
   *
   * @param chosens an id -> hasRanking map of choosable elements
   * @param pairings the chooser -> chosen map of pairings
   * @param <T> the type of hasRanking paired
   */
  public <T extends hasRanking<T>> void assertStable(Map<String, T> chosens,
                                                     Map<T, T> pairings) {
    // First construct the reverse mapping
    Map<T, T> reversePairings = new HashMap<>();
    for (Map.Entry<T, T> entry : pairings.entrySet()) {
      reversePairings.put(entry.getValue(), entry.getKey());
    }
    // Check every pairing
    for (Map.Entry<T, T> entry : pairings.entrySet()) {
      T choosing = entry.getKey();
      T chosen = entry.getValue();
      int rankingLimit = choosing.getRanking(chosen.getID());
      // Check all with a better preference to see if they match
      List<String> betterChoiceIDs = choosing.getRankings().subList(0, rankingLimit);
      for (String betterID : betterChoiceIDs) {
        T betterChoice = chosens.get(betterID);
        T currentChooser = reversePairings.get(betterChoice);
        // Assert that betterChoice's currentChooser is as preferred than the current
        int currentRanking = betterChoice.getRanking(currentChooser.getID());
        int testRanking = betterChoice.getRanking(choosing.getID());
        assertTrue(currentRanking <= testRanking);
      }
    }
  }

  @Test
  public void testProperties() {
    final Random r = new Random();
    final int ITERATIONS = 1000;
    final int SIZE_BOUND = 15;
    for (int i = 0; i < ITERATIONS; i++) {
      int size = r.nextInt(SIZE_BOUND);
      Map<String, Person> choosing = generatePeople(size);
      Map<String, Person> chosen = generatePeople(size);
      Map<Person, Person> pairs = GaleShapley
          .galeShapleyAlgo(new ArrayList<>(choosing.values()), new ArrayList<>(chosen.values()));
      assertAllPaired(choosing.values(), chosen.values(), pairs);
      assertStable(chosen, pairs);
    }
  }
}
