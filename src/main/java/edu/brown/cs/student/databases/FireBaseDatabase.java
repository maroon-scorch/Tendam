package edu.brown.cs.student.databases;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class for requesting and adding data to the Firebase database.
 */
public class FireBaseDatabase {

  /**
   * Public Constructor
   */
  public FireBaseDatabase() throws IOException {
    setupConnection();
  }

  /**
   * Returns a list of users from the database.
   *
   * @return a list of users
   * @throws CustomException.FutureBreakException if something goes wrong with the future
   */
  public List<User> retrieveUsers() throws CustomException.FutureBreakException {
    System.out.println("starting retrieval");
    List<User> cumulativeUsers = new ArrayList<>();

    Firestore db = FirestoreClient.getFirestore();
    CollectionReference docRef = db.collection("users");
    ApiFuture<QuerySnapshot> future = docRef.get();

    QuerySnapshot userCollection = null;
    try {
      userCollection = future.get();
    } catch (InterruptedException | ExecutionException e) {
      future.cancel(true);
      throw new CustomException.FutureBreakException();
    }
    if (userCollection != null) {
      userCollection.forEach(doc -> {
        User storedUser = doc.toObject(User.class);
        cumulativeUsers.add(storedUser);
      });
    }
    return cumulativeUsers;
  }

  /**
   * Sets up and initializes the FireBase connection
   * with admin privileges.
   *
   * @throws IOException if the connection attempt fails
   */
  public void setupConnection() throws IOException {
    FileInputStream serviceAccount =
            new FileInputStream("tendam-cs0320-2021-firebase-adminsdk-3qrxb-fdf62de72b.json");

    FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://tendam-cs0320-2021-default-rtdb.firebaseio.com")
            .build();

    FirebaseApp.initializeApp(options);
  }
}
