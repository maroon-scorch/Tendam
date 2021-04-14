package edu.brown.cs.student.main.commands;

import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.main.Main;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.replsupport.Command;
import edu.brown.cs.student.users.Matcher;
import edu.brown.cs.student.users.User;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Updates matches in the FireBase server.
 */
public class UpdateMatches implements Command {

  /**
   * The code to run on a set interval. Updates matches in the firebase.
   *
   * @param print if set to "print", prints relevant information to the terminal
   * @throws CustomException.FutureBreakException If the requested future doesn't work
   * @throws ClassNotFoundException               if the class cannot be found
   * @throws NoSuchMethodException                if the method cannot be found
   * @throws InvocationTargetException            if the .newInstance method fails
   * @throws InstantiationException               if a class cannot be instantiated
   * @throws IllegalAccessException               if not granted access to a database
   * @throws CustomException.NoUsersException     if there are no users in the database
   */
  public void execute(String print) throws CustomException.FutureBreakException,
          ClassNotFoundException, NoSuchMethodException,
          InvocationTargetException, InstantiationException,
          IllegalAccessException, CustomException.NoUsersException {

    FireBaseDatabase database = Main.getDatabase();

    // Merges retrieved survey data into the list of users
    List<User> addedSurveys = Objects.requireNonNull(
            database).merge(database.retrieveUsers("users"),
            database.retrieveSourceData("surveys"), "Survey");

    // Takes the list of users with merged survey
    // data and merges game data into it as well
    List<User> addedGames = database.merge(addedSurveys,
            database.retrieveSourceData("gamesCopy"), "");

    List<User> filteredNew = new ArrayList<>();
    addedGames.forEach(user -> {
      if (!user.getUserData().isEmpty()) {
        filteredNew.add(user);
      }
    });

    if (print.equals("print")) {
      for (int i = 0; i < 3; i++) {
        System.out.println("+++++++++++++++++++++++++++++++");
      }
      filteredNew.forEach(user -> System.out.println("User Name: "
              + user.getName() + " userData: " + user.getUserData()));
    }

    // Runs the matching pipeline on the list of
    // users and sends the results back to FireBase
    Matcher.run(filteredNew);


    // Prints out date and time in REPL to verify that it is running at the correct time
    SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, 'at' HH:mm:ss z");
    Date date = new Date(System.currentTimeMillis());
    String printDate = formatter.format(date);
    System.out.println("Updated matches at: " + printDate);
  }
}
