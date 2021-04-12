package edu.brown.cs.student.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.algorithm.GaleShapley;
import edu.brown.cs.student.algorithm.Person;
import edu.brown.cs.student.databases.Database;
import edu.brown.cs.student.miscenllaneous.CustomException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This class runs the overall algorithmic pipeline
 * Specifically, it creates preferences for every user,
 * runs gale shapely, then insert matches back into database.
 */
public class Matcher {


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
   * @param users the list of users to create preferences for
   * @return a list of Person
   * @throws CustomException.NoUsersException if there are no users in the database
   */
  public static List<Person> createAllPreferences(List<User> users)
          throws CustomException.NoUsersException {
    List<Person> people = new ArrayList<>();

    if (users == null || users.isEmpty()) {
      throw new CustomException.NoUsersException();
    }

    for (User datum : users) {

      // preferences is a list of string ID keys
      List<String> preferences = new ArrayList<>();
      List<PairScore> prePreferences = new ArrayList<>();

      for (User otherUser : users) {
        PairScore newPair = new PairScore(datum.getID(),
                otherUser.getID(), datum.calcDist(otherUser));
        prePreferences.add(newPair);
      }

      prePreferences.sort(new PairScoreCompare());

      for (PairScore ps : prePreferences) {
        if ((!ps.getOptionID().equals(datum.getID()))
                && (!datum.getMatches().contains(ps.getOptionID()))) {
          preferences.add(ps.getOptionID());
        }
      }

      Person human = new Person(datum.getID(), preferences);
      people.add(human);
    }

    return people;
  }

  /**
   * Runs the entire pipeline - building a set of preferences
   * for each user and then producing a single match each for them.
   *
   * @param users the input list of users
   * @throws CustomException.NoUsersException if there are no users in the database
   */
  public static void run(List<User> users) throws CustomException.NoUsersException {
    List<Person> people = createAllPreferences(users);

    Map<Person, Person> results = GaleShapley.galeShapleyAlgo(people, people);
    Collection<Person> keys = results.keySet();

    // the database instance
    Firestore db = FirestoreClient.getFirestore();

    // Creates a reference to the users collection
    CollectionReference docRef = db.collection("users");

    people.forEach(person -> System.out.println("Person ID: "
            + person.getID() + ", Preferences: " + person.getRankings()));

    System.out.println("...............................");
    System.out.println("...............................");
    System.out.println("...............................");
    System.out.println(results.toString());

    for (Person key : keys) {
      DocumentReference userRef = docRef.document(key.getID());
      ApiFuture<WriteResult> future = userRef.update("matches",
              FieldValue.arrayUnion(results.get(key).getID()));
      try {
        WriteResult result = future.get();
        System.out.println("Write result: " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
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
