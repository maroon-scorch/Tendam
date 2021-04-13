package edu.brown.cs.student.replsupport;

/**
 * Interface for all commands.
 */
public interface Command {

  /**
   * The method to execute.
   *
   * @param params the input parameters into the repl
   * @throws Exception if an exception is thrown
   */
  void execute(String params) throws Exception;
}
