package edu.brown.cs.student;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

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
    // these two lines tell the database to enforce foreign keys during operations,
    // and should be present
    Statement stat = conn.createStatement();
    stat.close();
  }

  public void insertUser(Person user) throws SQLException {
    //todo i think that this shouldn't be the person class because it doesn't
    // include all the fields
    //that would need to be stored in the database
    //perhaps we need to create a user class instead?
//    PreparedStatement prep;
//    prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS 'users' ("
//            + "id INTEGER, "
//            + "name TEXT, "
//            + "ts DOUBLE, "
//            + "lat DOUBLE, "
//            + "lon DOUBLE);");
//    prep.executeUpdate();
  }

  public void insertNewSurvey(String surveyName) {
    //todo this should add a new column to the table
  }
}
