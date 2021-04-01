package edu.brown.cs.student;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class Matcher {

  /*todo this class will contain method that gets all the
   users, run the kdtree, run gale shapely, then insert matches back into database
   */

  private final String databaseFile;
  private Database db;

  public Matcher(String file) throws FileNotFoundException, SQLException {
    this.databaseFile = file;
    this.db = new Database(databaseFile);
  }

  private List<User> getUsers()  {
    try {
      List<User> users = db.getAllUsers();
      return users;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }
}
