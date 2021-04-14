package edu.brown.cs.student.users;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.algorithm.GaleShapley;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.miscenllaneous.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This class runs the overall algorithmic pipeline
 * Specifically, it creates preferences for every user,
 * runs gale shapely, then insert matches back into database.
 */
public final class Matcher {

  /**
   * Public constructor for Matchers.
   */
  private Matcher() {
  }

  /**
   * Gets all the users from the database and creates a list of Persons from it.
   *
   * @param users the list of users to create preferences for
   * @return a list of Person
   * @throws CustomException.NoUsersException if there are no users in the database
   */
  public static List<User> createAllPreferences(List<User> users)
          throws CustomException.NoUsersException {
    List<User> people = new ArrayList<>();

    if (users == null || users.isEmpty()) {
      throw new CustomException.NoUsersException();
    }

    for (User datum : users) {

      if (datum.getMatches() == null) {
        datum.setMatches(new ArrayList<>());
      }

      // Preferences is a list of string ID keys
      List<String> preferences = new ArrayList<>();
      List<PairScore> prePreferences = new ArrayList<>();

      // Builds a list of PairScores representing the
      // difference between this user and every other user.
      for (User otherUser : users) {
        PairScore newPair = new PairScore(datum.getID(),
                otherUser.getID(), datum.calcDist(otherUser));
        prePreferences.add(newPair);
      }

      prePreferences.sort(new PairScoreCompare());

      // Adds the IDs of the other users "this" user is compared
      // against to the preferences list so long as they aren't
      // themselves and aren't already present in their matches
      for (PairScore ps : prePreferences) {
        if ((!ps.getOptionID().equals(datum.getID()))
                && (!datum.getMatches().contains(ps.getOptionID()))) {
          preferences.add(ps.getOptionID());
        }
      }
      datum.setPreferences(preferences);
      people.add(datum);
    }

    return people;
  }

  /**
   * Runs the entire pipeline - building a set of preferences
   * for each user and then producing a single match each for them.
   *
   * @param users the input list of users
   * @throws CustomException.NoUsersException if there are no users in the database
   * @throws CustomException.NoMatchException if no matches could be made
   */
  public static void run(List<User> users) throws CustomException.NoUsersException,
          CustomException.NoMatchException {
    List<User> people = createAllPreferences(users);
    people.forEach(person -> System.out.println(person.getName() + ", " + person.getPreferences()));

    // the database instance
    Firestore db = FirestoreClient.getFirestore();

    // Creates a reference to the users collection
    CollectionReference docRef = db.collection("users");

    // The results of running the Gale Shapley algorithm on the inputs
    Map<User, User> results = GaleShapley.galeShapleyAlgo(people, people);

    for (int i = 0; i < 3; i++) {
      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
    people.forEach(person -> System.out.println("User Name: "
            + person.getName() + ", Preferences: " + person.getRankings()));
    for (int i = 0; i < 3; i++) {
      System.out.println("...............................");
    }

    ProgressBar bar = new ProgressBar("Writing New Matches to FireBase", results.size());
    // Loops through the entries in the map of gale shapley results
    // and stores them in FireBase.
    for (Map.Entry<User, User> keyPair : results.entrySet()) {
      DocumentReference userRef = docRef.document(keyPair.getKey().getID());
      ApiFuture<WriteResult> future = userRef.update("matches",
              FieldValue.arrayUnion(keyPair.getValue().getID()));
      try {
        WriteResult result = future.get();
        System.out.println("Wrote result: " + result.toString());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
      bar.update();
    }
  }
}
