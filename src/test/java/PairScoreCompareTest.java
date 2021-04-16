import edu.brown.cs.student.users.PairScore;
import edu.brown.cs.student.users.PairScoreCompare;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing pair score equality and inequality.
 */
public class PairScoreCompareTest {
  PairScore score1;
  PairScore score2;
  PairScoreCompare psc = new PairScoreCompare();

  /**
   * set up where the two scores are equal
   */
  @Before
  public void setUp1() {
    score1 = new PairScore("1", "2", 0);
    score2 = new PairScore("2", "1", 0);
  }

  /**
   * set up where the first score is less than the second score
   */
  @Before
  public void setUp2() {
    score1 = new PairScore("1", "2", 5);
    score2 = new PairScore("2", "1", 6);
  }

  /**
   * set up where the first score is greater than the second score
   */
  @Before
  public void setUp3() {
    score1 = new PairScore("1", "2", 6);
    score2 = new PairScore("2", "1", 5);
  }

  @After
  public void tearDown() {
    score1 = null;
    score2 = null;
  }

  /**
   * tests the output of the compare method when the two scores are equal
   */
  @Test
  public void testPairScoreCompareEqualDifference() {
    setUp1();
    assertEquals(0, psc.compare(score1, score2));
    tearDown();
  }

  /**
   * tests the output of the compare method when
   * the first score is less than the second score
   */
  @Test
  public void testPairScoreComparePositive() {
    setUp2();
    assertEquals(-1, psc.compare(score1, score2));
    tearDown();
  }

  /**
   * tests the output of the compare method when
   * the first score is greater than the second score
   */
  @Test
  public void testPairScoreCompareNegative() {
    setUp3();
    assertEquals(1, psc.compare(score1, score2));
    tearDown();
  }
}
