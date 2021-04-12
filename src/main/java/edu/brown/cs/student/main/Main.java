package edu.brown.cs.student.main;

import edu.brown.cs.student.databases.FireBaseDatabase;
import edu.brown.cs.student.miscenllaneous.CustomException;
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
import java.util.Map;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

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

    FireBaseDatabase database = null;

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

    try {
      Map<String, Map<String, Object>> surveyMap = database.retrieveSourceData("surveys");
      for (String key : surveyMap.keySet()) {
        System.out.println(key);
        System.out.println(surveyMap.get(key).entrySet());
      }
    } catch (CustomException e) {
      e.printStackTrace();
    }

    System.out.println("------------------------------------");
    System.out.println("------------------------------------");
    System.out.println("------------------------------------");

    try {
      Map<String, Map<String, Object>> gamesMap = database.retrieveSourceData("games");
      for (String key : gamesMap.keySet()) {
        System.out.println(key);
        System.out.println(gamesMap.get(key).entrySet());
      }
    } catch (CustomException e) {
      e.printStackTrace();
    }



    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
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




