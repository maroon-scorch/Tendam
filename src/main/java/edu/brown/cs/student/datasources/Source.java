package edu.brown.cs.student.datasources;

import edu.brown.cs.student.datasources.games.gamelist.BlackJack;
import edu.brown.cs.student.datasources.surveys.surveylist.FoodSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.HoroscopeSurvey;
import edu.brown.cs.student.datasources.surveys.surveylist.MbtiSurvey;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;


public interface Source {

  Reflections SURVEY_REFLECTIONS = new
          Reflections("edu.brown.cs.student.datasources.surveys.Surveys");

  Reflections GAME_REFLECTIONS = new
          Reflections("edu.brown.cs.student.datasources.games.Games");

//  Set<Class<? extends Source>> GLOBAL_SOURCES = SURVEY_REFLECTIONS.getSubTypesOf(Source.class);

  List<Class<?>> GLOBAL_SOURCES = Arrays.asList(FoodSurvey.class, HoroscopeSurvey.class, MbtiSurvey.class, BlackJack.class);

//  Set<Class<? extends Source>> GLOBALGAMES = GAME_REFLECTIONS.getSubTypesOf(Games.class);

//  List<Class<Surveys>> GLOBALSURVEYS = new ArrayList<>();
//  List<Class<Games>> GLOBALGAMES = new ArrayList<>();


  /**
   * Calculates the amount of difference between this data set of results and another.
   * All distance are normalized to a value between 0 and 100.
   *
   * @param otherSource the other data set of results to compare to
   * @return a double representing the level of difference
   */
  double difference(Source otherSource);

//  /**
//   * Converts the attached Source into a list of objects (usually doubles or strings or a mixture).
//   *
//   * @return a list of objects
//   */
//  List<Object> valuesToList();





  /**
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a Source object
   */
  Source convert(Object data);



}
