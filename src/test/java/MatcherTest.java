import edu.brown.cs.student.miscenllaneous.CustomException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.brown.cs.student.users.Matcher.createAllPreferences;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class MatcherTest {

  @Before
  public void setUp() {

  }
  @After
  public void tearDown() {

  }
  @Test
  public void testNullUserList() {
    assertThrows(CustomException.NoUsersException.class,
            () -> createAllPreferences(null));
  }
}
