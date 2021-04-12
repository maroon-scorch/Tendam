package edu.brown.cs.student.miscenllaneous;

/**
 * Class dealing with unique exceptions.
 */
public abstract class CustomException extends Exception {

  /**
   * Private empty constructor.
   */
  private CustomException() {
  }

  /**
   * Returns the response associated with this exception.
   *
   * @return a string response
   */
  public abstract String getResponse();

  /**
   * Sub-class for when the star cannot be found in a file.
   */
  public static class ValuePointNotFoundException extends CustomException {
    private static final long serialVersionUID = 42;

    public static final String RESPONSE = "ERROR: The coordinate cannot be found in the data";

    /**
     * Empty Constructor.
     */
    public ValuePointNotFoundException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when the the wrong number of
   * arguments are inputted into naive_neighbors.
   */
  public static class NaiveWrongArgsException extends CustomException {
    private static final long serialVersionUID = 44;

    public static final String RESPONSE = "ERROR: The number of inputted arguments is invalid";

    /**
     * Empty Constructor.
     */
    public NaiveWrongArgsException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when there are no quotes to remove from the string.
   */
  public static class RemoveQuotesException extends CustomException {
    private static final long serialVersionUID = 46;

    public static final String RESPONSE = "ERROR: Quotes cannot be removed from the string";

    /**
     * Empty Constructor.
     */
    public RemoveQuotesException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when the inputted radius is negative.
   */
  public static class NegativeRadiusException extends CustomException {
    private static final long serialVersionUID = 48;

    public static final String RESPONSE = "ERROR: Radius cannot be negative";

    /**
     * Empty Constructor.
     */
    public NegativeRadiusException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when the inputs cannot be parsed correctly.
   */
  public static class CannotParseException extends CustomException {
    private static final long serialVersionUID = 50;

    public static final String RESPONSE =
            "ERROR: Your input types do not conform to the requirements";

    /**
     * Empty Constructor.
     */
    public CannotParseException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when the inputted k is negative.
   */
  public static class NegativeKException extends CustomException {
    private static final long serialVersionUID = 52;

    public static final String RESPONSE =
            "ERROR: The number of returned neighbors cannot be negative";

    /**
     * Empty Constructor.
     */
    public NegativeKException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when there are no loaded stars to be accessed.
   */
  public static class NoStarsLoadedException extends CustomException {
    private static final long serialVersionUID = 54;

    public static final String RESPONSE = "ERROR: No stars data loaded yet";

    /**
     * Empty Constructor.
     */
    public NoStarsLoadedException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when the file is malformed.
   */
  public static class MalformedFileException extends CustomException {
    private static final long serialVersionUID = 56;

    public static final String RESPONSE = "ERROR: The input data file is malformed";

    /**
     * Empty Constructor.
     */
    public MalformedFileException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for a runtime error where the string input
   * to the kd-tree comparator is not x, y, or z.
   */
  public static class NotWithinRangeException extends CustomException {
    private static final long serialVersionUID = 58;

    public static final String RESPONSE = "ERROR: Your range was not within program scope";

    /**
     * Empty Constructor.
     */
    public NotWithinRangeException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for a runtime error where the type is inputted incorrectly.
   */
  public static class InvalidTypeException extends CustomException {
    private static final long serialVersionUID = 60;

    public static final String RESPONSE = "ERROR: Your program inputted an invalid type";

    /**
     * Empty constructor.
     */
    public InvalidTypeException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for an error where there are too few arguments inputted into the REPL.
   */
  public static class SingleInputException extends CustomException {
    private static final long serialVersionUID = 62;

    public static final String RESPONSE = "ERROR: You need to input more than a single argument";

    /**
     * Empty constructor.
     */
    public SingleInputException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when too many inputs are placed into the REPL.
   */
  public static class TooManyInputException extends CustomException {
    private static final long serialVersionUID = 64;

    public static final String RESPONSE = "ERROR: You have too many input arguments";

    /**
     * Empty constructor.
     */
    public TooManyInputException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for a runtime error where the output is larger than it should be.
   */
  public static class OutputSizeException extends CustomException {
    private static final long serialVersionUID = 66;

    public static final String RESPONSE = "ERROR: The Output Size was Incorrect.";

    /**
     * Empty constructor.
     */
    public OutputSizeException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for a runtime error where weights have not yet been calculated.
   */
  public static class WeightNonexistentException extends CustomException {
    private static final long serialVersionUID = 68;

    public static final String RESPONSE = "ERROR: Weights Did Not Exist";

    /**
     * Empty constructor.
     */
    public WeightNonexistentException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for an error where an intersection cannot be found.
   */
  public static class NoIntersectionException extends CustomException {
    private static final long serialVersionUID = 70;

    public static final String RESPONSE = "ERROR: An intersection could not be found";

    /**
     * Empty constructor.
     */
    public NoIntersectionException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for a runtime error where the database has not been loaded yet.
   */
  public static class NoDatabaseLoadedException extends CustomException {
    private static final long serialVersionUID = 72;

    public static final String RESPONSE = "ERROR: A database has not yet been loaded";

    /**
     * Empty constructor.
     */
    public NoDatabaseLoadedException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for an error where a nearest neighbor cannot be found.
   */
  public static class NoNearestException extends CustomException {
    private static final long serialVersionUID = 74;

    public static final String RESPONSE = "ERROR: There was no nearest node.";

    /**
     * Empty constructor.
     */
    public NoNearestException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }

  }

  /**
   * Sub-class for when the the wrong number of
   * arguments are inputted into naive_neighbors.
   */
  public static class NoUsersException extends CustomException {
    private static final long serialVersionUID = 76;

    public static final String RESPONSE = "ERROR: There are no users in your database!";

    /**
     * Empty Constructor.
     */
    public NoUsersException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }

  /**
   * Sub-class for when something goes wrong with the future.
   */
  public static class FutureBreakException extends CustomException {
    private static final long serialVersionUID = 76;

    public static final String RESPONSE = "ERROR: Something went wrong with your future!";

    /**
     * Empty Constructor.
     */
    public FutureBreakException() {
    }

    /**
     * Returns the response associated with this exception.
     *
     * @return a string response
     */
    public String getResponse() {
      return RESPONSE;
    }
  }
}

