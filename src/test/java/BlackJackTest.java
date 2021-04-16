import edu.brown.cs.student.datasources.games.gamelist.BlackJack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BlackJackTest {
  BlackJack p1;
  BlackJack p2;

  @Before
  public void setUp() {
    p1 = new BlackJack(58.8315);
    p2 = new BlackJack(65.2173);
  }

  @After
  public void tearDown() {
    p1 = null;
    p2 = null;
  }

  @Test
  public void testGetScore() {
    assertTrue(Math.abs(p1.getScore() - 58.8315) < 0.0001);
    assertTrue(Math.abs(p2.getScore() - 65.2173) < 0.0001);
  }

  @Test
  public void testDifference() {
    double difference = Math.abs(58.8315 - 65.2173);
    assertTrue(Math.abs(p1.difference(p2) - difference) < 0.0001);
    assertTrue(Math.abs(p2.difference(p1) - difference) < 0.0001);
  }
}
