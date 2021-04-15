import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FireBaseDatabaseTest {
  FireBaseDatabase fb;


  @Before
  public void setUp() {
    try {
      fb = new FireBaseDatabase();
    } catch (CustomException.NoDatabaseLoadedException e) {
      e.printStackTrace();
      fail();
    }
  }

  @After
  public void tearDown() {
    fb = null;
  }

  @Test
  public void testGetUsers() {
    Object newUser = new User("newUser1", "newUser1", null);
    List<User> userList = new ArrayList<>();
    userList.add((User) newUser);
    try {
      List<User> userList1 = fb.retrieveUsers("onlyNewUsers");
      assertEquals(userList, userList1);
    } catch (CustomException.FutureBreakException e) {
      e.printStackTrace();
      fail();
    }
  }

}
