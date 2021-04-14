import edu.brown.cs.student.users.PairScore;
import edu.brown.cs.student.users.PairScoreCompare;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairScoreCompareTest {
  PairScore score1;
  PairScore score2;
  PairScoreCompare psc = new PairScoreCompare();

  @Before
  public void setUp1() {
    score1 = new PairScore("1", "2", 0);
    score2 = new PairScore("2", "1", 0);
  }

  @Before
  public void setUp2() {
    score1 = new PairScore("1", "2", 5);
    score2 = new PairScore("2", "1", 6);
  }

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

  @Test
  public void testPairScoreCompareEqualDifference() {
    setUp1();
    assertEquals(psc.compare(score1, score2), 0);
    tearDown();
  }

  @Test
  public void testPairScoreComparePositive() {
    setUp2();
    assertEquals(psc.compare(score1, score2), -1);
    tearDown();
  }
  @Test
  public void testPairScoreCompareNegative() {
    setUp3();
    assertEquals(psc.compare(score1, score2), 1);
    tearDown();
  }
}
