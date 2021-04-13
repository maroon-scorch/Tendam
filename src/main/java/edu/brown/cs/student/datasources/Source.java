package edu.brown.cs.student.datasources;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;


public interface Source {

  Reflections SOURCE_REFLECTIONS = new
          Reflections("edu.brown.cs.student.datasources");

  Set<Class<? extends Source>> GLOBAL_SOURCES = SOURCE_REFLECTIONS.getSubTypesOf(Source.class);

//  List<Class<?>> GLOBAL_SOURCES = Arrays.asList(FoodSurvey.class,
//          HoroscopeSurvey.class, MbtiSurvey.class, BlackJack.class);

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

  /**
   * Converts an object retrieved from FireBase into a Source.
   *
   * @param data the input data
   * @return a Source object
   */
  Source convert(Object data);
}
