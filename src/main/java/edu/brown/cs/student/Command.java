package edu.brown.cs.student;

/**
 * Interface for all commands.
 */
public interface Command {

  void execute(String params) throws Exception;
}
