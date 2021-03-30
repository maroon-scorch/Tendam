import edu.brown.cs.student.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
}
