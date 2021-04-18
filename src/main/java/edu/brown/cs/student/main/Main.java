package edu.brown.cs.student.main;

import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.main.commands.ClearField;
import edu.brown.cs.student.main.commands.UpdateMatches;
import edu.brown.cs.student.miscenllaneous.CustomException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static FireBaseDatabase database = null;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private final String[] args;

  /**
   * Constructor for Main.
   *
   * @param args the string arguments passed to Main
   */
  private Main(String[] args) {
    this.args = args;
  }


  /**
   * The method that runs when inputting ./run in console.
   * Activates the matching process that runs on a set interval.
   */
  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    OptionSet options = parser.parse(args);

    REPL repl = new REPL();
    repl.registerCommand("clear", new ClearField());
    repl.registerCommand("update", new UpdateMatches());

    // Sets up the FireBaseDatabase class
    // and connects to the FireBase storage.
    try {
      database = new FireBaseDatabase();
      System.out.println("DATABASE LOADED!");
    } catch (CustomException e) {
      System.out.println(e.getResponse());
    }

    for (int i = 0; i < 3; i++) {
      System.out.println("--------------------------------");
    }

    // Sets up and runs the interval code on a schedule
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 06);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);

    Timer timer = new Timer();
    TimerTask intervalTask = new TimerTask() {
      @Override
      public void run() {
        try {
          new UpdateMatches().execute("users");
        } catch (CustomException e) {
          System.out.println(e.getResponse());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    timer.schedule(intervalTask, today.getTime(),
            TimeUnit.MILLISECONDS.convert(1,
                    TimeUnit.DAYS));

    // Reads the data into the console and runs it in the REPL.
    // Not currently used, but there for backup testing
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      String input;
      while ((input = br.readLine()) != null) {
        repl.run(input);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: You ran into an unaccounted-for error "
              + "produced from a REPL command!. System is forced to exit.");
    }
  }

  /**
   * Accesses the database.
   *
   * @return a FireBaseDatabase instance
   */
  public static FireBaseDatabase getDatabase() {
    return database;
  }
}
