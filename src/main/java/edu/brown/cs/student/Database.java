package edu.brown.cs.student;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Database {

  private static Connection conn = null;
  private final String filepath;

  public Database(String filepath) throws FileNotFoundException, SQLException {
    File file = new File(filepath);
    if (!file.exists() || !file.isFile()) {
      throw new FileNotFoundException("ERROR: invalid filepath");
    }
    this.filepath = filepath;
    String urlToDB = "jdbc:sqlite:" + filepath;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.close();
  }

  //inserts a new user into the user table
  public void insertUser(String id, String userName, String password, String email) throws SQLException {
    //todo i think that this shouldn't be the person class because it doesn't
    // include all the fields
    //that would need to be stored in the database
    //perhaps we need to create a user class instead?
    PreparedStatement prepTable;
    prepTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS 'users' ("
            + "id TEXT, "
            + "username TEXT, "
            + "password TEXT, "
            + "email TEXT);");
    prepTable.executeUpdate();
    PreparedStatement prepUser;
    prepUser = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?);");
    prepUser.setString(1, id);
    prepUser.setString(2, userName);
    prepUser.setString(3, password);
    prepUser.setString(4, email);
    prepUser.addBatch();
    prepUser.executeBatch();
  }

  //inserts a new table into the database when a new survey is created
  public void insertNewSurvey(String surveyName) throws SQLException {
    PreparedStatement prep;
    prep = conn.prepareStatement("ALTER TABLE users ADD (?) DOUBLE;");
    prep.setString(1, surveyName);
    prep.executeUpdate();
  }

  //updates the field for a user in the table when they complete a survey
  public void updateSurveyData(String userId, String surveyName, Double surveyScore) throws SQLException {
    PreparedStatement prep;
    prep = conn.prepareStatement("UPDATE users "
    + "SET ? = ?  WHERE id = ?;");
    prep.setString(1, surveyName);
    prep.setDouble(2, surveyScore);
    prep.setString(3, userId);
    prep.executeUpdate();
  }

  //adds friend relations to a new table
  /* if A is friends with B, need to call addFriends(a.id, b.id) and
  addFriends(b.id, a.id)
   */
  public void addFriends(String userId, String friendId) throws SQLException {
    PreparedStatement prepTable;
    prepTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS 'friends' "
            + " user TEXT, friend TEXT;");
    prepTable.executeUpdate();
    PreparedStatement prep;
    prep = conn.prepareStatement("INSERT INTO friends VALUES (?, ?);");
    prep.setString(1, userId);
    prep.setString(2, friendId);
    prep.addBatch();
    prep.executeBatch();
  }

  public List<String> findFriends(String userId) throws SQLException {
    PreparedStatement prep;
    prep = conn.prepareStatement("SELECT friends.friend FROM friends WHERE friends.user = ?;");
    List<String> friends = new LinkedList<>();
    ResultSet rs = prep.executeQuery();
    while (rs.next()) {
      friends.add(rs.getString(1));
    }
    return friends;
  }

  public List<User> getAllUsers() throws SQLException {
    PreparedStatement prep = conn.prepareStatement("SELECT * FROM users;");
    ResultSet rs = prep.executeQuery();
    ResultSetMetaData rd = rs.getMetaData();
    //sets all the available surveys into a hashmap
    int c = rd.getColumnCount();
    //starts at 5 because first 4 columns include other user info
    int i = 5;
    Map<String, Double> surveys= new HashMap<>();
    while (i <= c) {
      surveys.put(rd.getColumnName(i), null);
      i++;
    }

    List<User> users = new LinkedList<>();

    while (rs.next()) {
      String id = rs.getString(1);
      String username = rs.getString(2);
      String password = rs.getString(3);
      String email = rs.getString(4);
      int k = 5;
      while (k <= c) {
        surveys.put(rd.getColumnName(k), rs.getDouble(k));
        k++;
      }
      List<String> friends = findFriends(id);
      User user = new User(id, username, password, email, friends);
      users.add(user);
    }
    return users;
  }
}
