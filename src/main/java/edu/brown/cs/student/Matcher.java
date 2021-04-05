package edu.brown.cs.student;

import edu.brown.cs.student.algorithm.GaleShapley;
import edu.brown.cs.student.algorithm.Person;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.PairScore;
import edu.brown.cs.student.users.PairScoreCompare;
import edu.brown.cs.student.users.User;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Matcher {

  /*
   TODO: this class will contain method that gets all the
   users, run the kdtree, run gale shapely, then insert matches back into database
   */

  private final String databaseFile;
  private Database db;

  /**
   * Public constructor for Matchers.
   *
   * @param file the string file path
   * @throws FileNotFoundException if the file cannot be found at that path
   * @throws SQLException          if something goes wrong with the SQL command
   */
  public Matcher(String file) throws FileNotFoundException, SQLException {
    this.databaseFile = file;
    this.db = new Database(databaseFile);
  }

  /**
   * Gets all the users from the database and creates a list of Persons from it.
   *
   * @return a list of Person
   * @throws CustomException.NoUsersException if there are no users in the database
   */
  public List<Person> createAllPreferences() throws CustomException.NoUsersException {
    List<Person> people = new ArrayList<>();
    List<User> users = this.getUsers();

    if (users == null || users.isEmpty()) {
      throw new CustomException.NoUsersException();
    }

    for (User datum : users) {

      // preferences is a list of IDs
      List<Integer> preferences = new ArrayList<>();
      List<PairScore> prePreferences = new ArrayList<>();

      for (User otherUser : users) {
        PairScore newPair = new PairScore(datum.getId(),
                otherUser.getId(), datum.calcDist(otherUser));
        prePreferences.add(newPair);
      }

      prePreferences.sort(new PairScoreCompare());

      for (PairScore ps : prePreferences) {
        preferences.add(ps.getMainID());
      }

      Person human = new Person(datum.getId(), preferences);
      people.add(human);
    }
    return people;
  }

  /**
   * Runs the entire pipeline - building a set of preferences
   * for each user and then producing a single match each for them.
   *
   * @throws CustomException.NoUsersException if there are no users in the database
   * @throws SQLException if something goes wrong with the SQL command
   */
  public void run() throws CustomException.NoUsersException, SQLException {
    List<Person> people = this.createAllPreferences();

    Map<Person, Person> results = GaleShapley.galeShapleyAlgo(people, people);
    Collection<Person> keys = results.keySet();

    for (Person key : keys) {
      db.addFriends(key.getID(), results.get(key).getID());
    }

  }

  private List<User> getUsers() {
    try {
      return this.db.getAllUsers();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return null;
    }
  }
}
