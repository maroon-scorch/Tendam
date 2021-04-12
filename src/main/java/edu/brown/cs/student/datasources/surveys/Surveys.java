package edu.brown.cs.student.datasources.surveys;

import edu.brown.cs.student.datasources.Source;

import java.util.Map;

public interface Surveys extends Source {

  /**
   * Accesses the answers of a Source.
   * @return a map of string to objects
   */
  Map<String, Object> getAnswers();
}
