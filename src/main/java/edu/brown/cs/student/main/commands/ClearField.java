package edu.brown.cs.student.main.commands;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.miscenllaneous.ProgressBar;
import edu.brown.cs.student.replsupport.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Command class for clearing user fields.
 */
public class ClearField implements Command {

  /**
   * Clears the specified field in the user collection.
   *
   * @param field the field that should be cleared
   * @throws CustomException.FutureBreakException if the future breaks down
   */
  public void execute(String field) throws CustomException.FutureBreakException {
    Firestore db = FirestoreClient.getFirestore();
    CollectionReference colRef = db.collection("users");
    ApiFuture<QuerySnapshot> future = colRef.get();
    List<String> keys = new ArrayList<>();

    QuerySnapshot keyCollection;
    try {
      keyCollection = future.get();
    } catch (InterruptedException | ExecutionException e) {

      // Interrupts the future to maintain its broken state.
      future.cancel(true);
      throw new CustomException.FutureBreakException();
    }

    if (keyCollection != null) {
      keyCollection.forEach(doc -> {
        // Adds the id of each doc to the list of keys
        String key = doc.getId();
        keys.add(key);
      });
    }

    ProgressBar bar = new ProgressBar("Clearing " + field, keys.size());
    for (String key : keys) {
      DocumentReference docRef = colRef.document(key);
      docRef.update(field, null);
      bar.update();
    }
    System.out.println(field + " cleared!");
  }
}
