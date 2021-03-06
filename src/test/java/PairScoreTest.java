import edu.brown.cs.student.users.PairScore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the PairScore constructor.
 */
public class PairScoreTest {

  /**
   * Determines if all the accessor methods work.
   */
  @Test
  public void testPairScoreConstructor() {
    PairScore ps = new PairScore("1234", "4321", 0);
    assertEquals(ps.getMainID(), "1234");
    assertEquals(ps.getOptionID(), "4321");
    assertTrue(Math.abs(ps.getDifference() - 0) < 0.0001);
  }
}
