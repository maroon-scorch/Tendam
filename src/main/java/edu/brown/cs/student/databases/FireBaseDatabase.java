package edu.brown.cs.student.databases;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.datasources.Source;
import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Class for requesting and adding data to the Firebase database.
 */
public class FireBaseDatabase {

  /**
   * Public Constructor.
   */
  public FireBaseDatabase() throws IOException {
    setupConnection();
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

  /**
   * Returns a list of users from the database.
   *
   * @return a list of users
   * @throws CustomException.FutureBreakException if something goes wrong with the future
   */
  public List<User> retrieveUsers() throws CustomException.FutureBreakException {
    System.out.println("starting retrieval for users");
    List<User> cumulativeUsers = new ArrayList<>();

    Firestore db = FirestoreClient.getFirestore();
    CollectionReference docRef = db.collection("users");
    ApiFuture<QuerySnapshot> future = docRef.get();

    QuerySnapshot userCollection;
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
   * Returns a nested map of surveys from the database.
   *
   * @param collectionPath the path of the collection in the database
   * @return a map of users and their survey scores
   * @throws CustomException.FutureBreakException if something goes wrong with the future
   */
  public Map<String, Map<String, Object>> retrieveSourceData(String collectionPath)
          throws CustomException.FutureBreakException {
    System.out.println("starting retrieval for " + collectionPath + " data");
    Map<String, Map<String, Object>> cumulativeSources = new HashMap<>();

    Firestore db = FirestoreClient.getFirestore();
    CollectionReference docRef = db.collection(collectionPath);
    ApiFuture<QuerySnapshot> future = docRef.get();

    QuerySnapshot surveyCollection;
    try {
      surveyCollection = future.get();
    } catch (InterruptedException | ExecutionException e) {
      future.cancel(true);
      throw new CustomException.FutureBreakException();
    }
    if (surveyCollection != null) {
      surveyCollection.forEach(doc -> cumulativeSources.put(doc.getId(), doc.getData()));
    }
    return cumulativeSources;
  }

  // First map is ID, second map is survey name,
  // third map is hidden within object array but represents
  // each answer
  public List<User> merge(List<User> usersToMerge, Map<String, Map<String, Object>> sourceToMerge)
          throws ClassNotFoundException, NoSuchMethodException,
          IllegalAccessException, InvocationTargetException,
          InstantiationException {
    for (User u : usersToMerge) {
      Map<String, Object> mapData = sourceToMerge.get(u.getID());
      for (Map.Entry<String, Object> entry : mapData.entrySet()) {
        // TODO: fix this line
        Class<?> classType = Class.forName(StringUtils.capitalize(entry.getKey()) + "Survey");

        for (Class<?> c : Source.GLOBAL_SOURCES) {
          if (classType == c) {
            Source instance = (Source) c.getDeclaredConstructor().newInstance();
            instance.convert(entry.getValue());

          }
        }


        if (classType == FoodSurvey.class) {

        }
      }

    }
  }
}













































