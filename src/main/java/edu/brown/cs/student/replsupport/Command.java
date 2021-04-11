package edu.brown.cs.student.replsupport;

/**
 * Interface for all commands.
 */
public interface Command {

  void execute(String params) throws Exception;
}
