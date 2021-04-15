package edu.brown.cs.student.main;

import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.replsupport.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for REPL.
 */
public class REPL {
  private static Map<String, Command> commands = new HashMap<>();

  /**
   * Sets up a REPL with no command registered.
   */
  public REPL() {
  }

  /**
   * Registers a new command into the commands map.
   *
   * @param commandName command name (user input)
   * @param command     a Command for execution
   */
  public void registerCommand(String commandName, Command command) {
    commands.put(commandName, command);
  }

  /**
   * Parses and runs the user input command.
   * Reports an error if command does not exist.
   *
   * @param input user input
   * @throws Exception for other generic exceptions;
   */
  public void run(String input) throws Exception {
    String[] command = input.split(" ", 2);
    String commandName = command[0];
    String parameters = command.length > 1 ? command[1] : "";
    Command c = commands.get(commandName);
    if (c != null) {
      c.execute(parameters);
    } else {
      throw new CustomException.NaiveWrongArgsException();
    }

  }
}
