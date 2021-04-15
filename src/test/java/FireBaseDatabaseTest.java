import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    setUp();
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
    tearDown();
  }

}
