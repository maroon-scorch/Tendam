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
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.miscenllaneous.ProgressBar;
import edu.brown.cs.student.users.User;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Class for requesting and adding data to the Firebase database.
 */
public class FireBaseDatabase {

  private static final Reflections SOURCE_REFLECTIONS = new
          Reflections("edu.brown.cs.student.datasources");

  private static final Set<Class<? extends Source>> GLOBAL_SOURCES
          = SOURCE_REFLECTIONS.getSubTypesOf(Source.class);

  /**
   * Public Constructor.
   * Sets up the connection to the FireBase on instantiation.
   * @throws IOException if connection attempt fails
   */
  public FireBaseDatabase() throws IOException {
    setupConnection();
  }

  /**
   * Sets up and initializes the FireBase connection
   * with admin privileges.
   * Must be run with the json security file in the code.
   * Otherwise there is no security key to the database
   * and the connection is forbidden
   *
   * @throws IOException if the connection attempt fails
   */
  public void setupConnection() throws IOException {

    // Security credentials that grants the system
    // admin privileges over the database.
    FileInputStream serviceAccount =
            new FileInputStream("tendam-cs0320-2021"
                    + "-firebase-adminsdk-3qrxb-fdf62de72b.json");

    // The options to pass into the initialization.
    // Contains the credentials from above
    FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://tendam-cs0320-2021-default-rtdb.firebaseio.com")
            .build();

    FirebaseApp.initializeApp(options);
  }

  /**
   * Returns a list of users from the database
   * with only the public constructor
   * parameters filled (id, name, matches).
   *
   * @param userPath the path to the user collection
   * @return a list of users
   * @throws CustomException.FutureBreakException if something goes wrong with the future
   */
  public List<User> retrieveUsers(String userPath) throws CustomException.FutureBreakException {
    List<User> cumulativeUsers = new ArrayList<>();

    Firestore db = FirestoreClient.getFirestore();
    CollectionReference docRef = db.collection(userPath);
    ApiFuture<QuerySnapshot> future = docRef.get();

    QuerySnapshot userCollection;
    try {
      userCollection = future.get();
    } catch (InterruptedException | ExecutionException e) {

      // Interrupts the future to maintain its broken state.
      future.cancel(true);
      throw new CustomException.FutureBreakException();
    }

    if (userCollection != null) {
      ProgressBar bar = new ProgressBar("Retrieving Users",
              userCollection.size());
      userCollection.forEach(doc -> {

        // Converts the obtained user data into an instance of User.
        User storedUser = doc.toObject(User.class);
        cumulativeUsers.add(storedUser);
        bar.update();
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

    Map<String, Map<String, Object>> cumulativeSources = new HashMap<>();
    Firestore db = FirestoreClient.getFirestore();
    CollectionReference docRef = db.collection(collectionPath);
    ApiFuture<QuerySnapshot> future = docRef.get();

    QuerySnapshot sourceCollection;
    try {
      sourceCollection = future.get();
    } catch (InterruptedException | ExecutionException e) {

      // Interrupts the future to maintain its broken state.
      future.cancel(true);
      throw new CustomException.FutureBreakException();
    }
    if (sourceCollection != null) {

      // Iterates through the FireBase-formatted data
      // and puts each entry pair into the returned map
      ProgressBar bar = new ProgressBar("Retrieving " + collectionPath
              + " data from FireBase", sourceCollection.size());
      sourceCollection.forEach(doc -> {
        cumulativeSources.put(doc.getId(), doc.getData());
        bar.update();
      });
    }
    return cumulativeSources;
  }

  /**
   * Adds the given details in the Map to the
   * list of users' respective userData fields.
   *
   * @param usersToMerge  the list of users to merge with the map
   * @param sourceToMerge the map to merge into the users
   * @param tail          a string to be appended to produce the
   *                      correct FireBase to Java class namespace
   * @return an updated list of users
   * @throws ClassNotFoundException    if the class cannot be found
   * @throws NoSuchMethodException     if the method cannot be found
   * @throws IllegalAccessException    if not given access to the database
   * @throws InvocationTargetException if the .newInstance method fails
   * @throws InstantiationException    if a class cannot be instantiated
   */
  public List<User> merge(List<User> usersToMerge, Map<String,
          Map<String, Object>> sourceToMerge, String tail)
          throws ClassNotFoundException, NoSuchMethodException,
          IllegalAccessException, InvocationTargetException,
          InstantiationException {

    // Changes the value of the package prefix
    // string based on the tail string

    String prefix;
    if (tail.equals("Survey")) {
      prefix = ".surveys.surveylist.";
    } else {
      prefix = ".games.gamelist.";
    }

    List<User> finalList = new ArrayList<>();

    // Iterates through each user
    for (User u : usersToMerge) {
      Map<String, Object> mapData = sourceToMerge.get(u.getID());

      // Skips if there is no mapData to
      // add for this particular user
      if (mapData == null || mapData.isEmpty()) {
        u.setUserData(new HashMap<>());
        finalList.add(u);
        continue;
      }
      Map<String, Source> sourceParam = new HashMap<>();
      System.out.println(sourceParam);
      System.out.println("done1");
      // Iterates through the mapData for a particular user
      for (Map.Entry<String, Object> entry : mapData.entrySet()) {
        System.out.println(sourceParam);
        System.out.println("doneX");
        String sourceName = StringUtils.capitalize(entry.getKey()) + tail;
        Class<?> classType = Class.forName("edu.brown.cs.student.datasources"
                + prefix + sourceName);

        // Iterates through a pre-defined list of classes to
        // find a matching one and convert the map data into
        // an instance of that class.
        for (Class<?> c : GLOBAL_SOURCES) {
          if (classType == c) {
            Source instance = (Source) c.getConstructor().newInstance();
            Source wrapped = instance.convert(entry.getValue());
            sourceParam.put(sourceName, wrapped);
          }
        }
      }
      u.updateUserData(sourceParam);
      finalList.add(u);
    }
    return finalList;
  }
}













































