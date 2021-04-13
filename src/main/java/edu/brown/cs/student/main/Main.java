package edu.brown.cs.student.main;

import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.main.commands.ClearField;
import edu.brown.cs.student.main.commands.UpdateMatches;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
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

  private static final int DEFAULT_PORT = 4567;

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
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    REPL repl = new REPL();
    repl.registerCommand("clear", new ClearField());
    repl.registerCommand("update", new UpdateMatches());

    // Sets up the FireBaseDatabase class
    // and connects to the FireBase storage.
    try {
      database = new FireBaseDatabase();
      System.out.println("DATABASE LOADED!");
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < 3; i++) {
      System.out.println("--------------------------------");
    }

    // Activates the GUI
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // Sets up and runs the interval code on a schedule
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 5);
    today.set(Calendar.MINUTE, 52);
    today.set(Calendar.SECOND, 15);

    Timer timer = new Timer();
    try {
      TimerTask intervalTask = new TimerTask() {
        @Override
        public void run() {
          try {
            new UpdateMatches().execute("print");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };

      timer.schedule(intervalTask, today.getTime(),
              TimeUnit.MILLISECONDS.convert(1,
                      TimeUnit.DAYS));
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Reads the data into the console and runs it in the REPL.
    // Not currently used, but there for backup testing
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      String input;
      while ((input = br.readLine()) != null) {
        repl.run(input);
      }
    } catch (RuntimeException e) {
      System.out.println("ERROR: Runtime exception");
    } catch (Exception e) {
      System.out.println("ERROR: Invalid input for REPL");
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

  /**
   * Builds a FreeMarkerEngine engine.
   *
   * @return a FreeMarkerEngine
   */
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_25);
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
              templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Runs the built spark GUI server.
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}




