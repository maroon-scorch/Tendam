package edu.brown.cs.student.main;

import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.Matcher;
import edu.brown.cs.student.users.User;
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
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private FireBaseDatabase database = null;

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

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
            .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    try {
      database = new FireBaseDatabase();
    } catch (IOException e) {
      e.printStackTrace();
    }

    REPL repl = new REPL();
    try {
      database.retrieveUsers();
      System.out.println("DATABASE LOADED!");
    } catch (CustomException e) {
      e.getResponse();
    }

    System.out.println("------------------------------------");
    System.out.println("------------------------------------");
    System.out.println("------------------------------------");
//
//    try {
//      Map<String, Map<String, Object>> surveyMap = database.retrieveSourceData("surveys");
//      for (String key : surveyMap.keySet()) {
//        System.out.println(key);
//        System.out.println(surveyMap.get(key).entrySet());
//      }
//    } catch (CustomException e) {
//      e.printStackTrace();
//    }
//
//    System.out.println("------------------------------------");
//    System.out.println("------------------------------------");
//    System.out.println("------------------------------------");
//
//    try {
//      Map<String, Map<String, Object>> gamesMap = database.retrieveSourceData("gamesCopy");
//      System.out.println("done");
//      for (String key : gamesMap.keySet()) {
//        System.out.println(key);
//        System.out.println(gamesMap.get(key).entrySet());
//      }
//    } catch (CustomException e) {
//      e.printStackTrace();
//    }
//
//    System.out.println("------------------------------------");
//    System.out.println("------------------------------------");
//    System.out.println("------------------------------------");
//
//    try {
//      System.out.println("starting merge");
////      System.out.println(database.retrieveSourceData("games").toString());
//      List<User> newUsers = database.merge(database.retrieveUsers(),
//              database.retrieveSourceData("surveys"), "Survey");
//      newUsers.forEach(user ->
//              System.out.println("BEFORE: " + user.getUserData().toString()));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }


    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    Calendar today = Calendar.getInstance();

    today.set(Calendar.HOUR_OF_DAY, 1);
    today.set(Calendar.MINUTE, 37);
    today.set(Calendar.SECOND, 15);

// every night at 2am you run your task
    Timer timer = new Timer();
    try {
      TimerTask intervalTask = new TimerTask() {
        @Override
        public void run() {
          try {
            runInterval();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };

      timer.schedule(intervalTask, today.getTime(),
              TimeUnit.MILLISECONDS.convert(1,
                      TimeUnit.DAYS)); // period: 1 day
    } catch (Exception e) {
      e.printStackTrace();
    }

    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      String input;
      while ((input = br.readLine()) != null) {
        repl.run(input);
      }
    } catch (RuntimeException e) {
      System.err.println("ERROR: Runtime exception");
    } catch (Exception e) {
      System.err.println("ERROR: Invalid input for REPL");
    }

  }

  public void runInterval() throws CustomException.FutureBreakException,
          ClassNotFoundException, NoSuchMethodException,
          InvocationTargetException, InstantiationException,
          IllegalAccessException, CustomException.NoUsersException {

    List<User> addedSurveys = Objects.requireNonNull(
            database).merge(database.retrieveUsers(),
            database.retrieveSourceData("surveys"), "Survey");

    List<User> addedGames = database.merge(addedSurveys,
            database.retrieveSourceData("gamesCopy"), "");

    System.out.println("+++++++++++++++++++++++++++++++");
    System.out.println("+++++++++++++++++++++++++++++++");
    System.out.println("+++++++++++++++++++++++++++++++");
    addedGames.forEach(user -> System.out.println("User ID: "
            + user.getID() + " userData: " + user.getUserData()));

    Matcher.run(addedGames);
    System.out.println("ran!");

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
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




