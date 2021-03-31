//package edu.brown.cs.student;
//
//import java.security.InvalidParameterException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Class containing static helper methods.
// */
//
//public final class Common {
//  /**
//   * Empty Constructor.
//   */
//  private Common() { }
//
//  private static List<Star> allStars;
//
//  /**
//   * Accesses the list of all stars.
//   * @return list of all stars
//   */
//  public static List<Star> getAllStars() {
//    return allStars;
//  }
//
//  /**
//   * Mutates all stars.
//   * @param stars a list of stars
//   */
//  public static void setAllStars(List<Star> stars) {
//    allStars = stars;
//  }
//
//  /**
//   * Finds a star by name from the list allStars.
//   * Print an error if it does not exist.
//   * @param name Name of star
//   * @return Star named name
//   */
//  public static Star findStarByName(String name) {
//    for (Star star : allStars) {
//      if (star.getName().equals(name)) {
//        return star;
//      }
//    }
//    System.err.println("ERROR: The star " + name + " does not exist");
//    return null;
//  }
//
//  /**
//   * Sets all stars' distance field to their distance from the given coordinate.
//   * @param coordinate A point in 3D space
//   * @param stars A list of stars
//   */
//  public static void setStarDistanceFrom(double[] coordinate, List<Star> stars) {
//    for (Star s : stars) {
//      double d = Common.calcDistance(s.getCoordinate(), coordinate);
//      s.setDistance(d);
//    }
//  }
//
//  /**
//   * Makes a copy of AllStars, excluding the star named name.
//   * @param name A star name
//   * @return List of copied stars
//   */
//  public static List<Star> copyStarListExcept(String name) {
//    Star currentStar = findStarByName(name);
//    List<Star> starsCopy = new ArrayList<>();
//    if (currentStar != null) {
//      starsCopy.addAll(Common.allStars);
//      starsCopy.remove(currentStar);
//    }
//    return starsCopy;
//  }
//
//  /**
//   * Checks if a string matches a Regex format.
//   * @param input String input
//   * @param format String representing a Regex format
//   * @return a boolean indicating if input matches format
//   */
//  public static boolean checkRegex(String input, String format) {
//    Pattern p = Pattern.compile(format);
//    Matcher m = p.matcher(input);
//    return m.matches();
//  }
//
//  /**
//   * Checks if a list of strings matches a list of Regex formats, in respective positions.
//   * @param input List of String
//   * @param format List of String, each one represents a Regex format
//   * @return a boolean indicating if all items in input matches their respective regex formats
//   */
//  public static boolean checkRegex(List<String> input, List<String> format) {
//    if (input.size() == format.size()) {
//      for (int i = 0; i < input.size(); i++) {
//        if (!checkRegex(input.get(i), format.get(i))) {
//          return false;
//        }
//      }
//      return true;
//    } else {
//      return false;
//    }
//  }
//
//  /**
//   * Calculates the distance between the two points in a k-dimensional space.
//   * @param coordinates1 a point in k-dimensional space
//   * @param coordinates2 another point in k-dimensional space
//   * @return distance between two points
//   * @throws InvalidParameterException if two coordinates are not in the same dimension
//   */
//  public static double calcDistance(double[] coordinates1, double[] coordinates2)
//      throws InvalidParameterException {
//    if (coordinates1.length != coordinates2.length) {
//      throw new InvalidParameterException("ERROR: Points not in the same dimension");
//    } else {
//      double distance = 0;
//      for (int i = 0; i < coordinates1.length; i++) {
//        distance += Math.pow((coordinates1[i] - coordinates2[i]), 2);
//      }
//      return Math.sqrt(distance);
//    }
//  }
//
//  /**
//   * Removes quotation marks for string enclosed in quotes.
//   * @param s a String
//   * @return string with quotations removed
//   */
//  public static String removeQuotes(String s) {
//    return s.substring(1, s.length() - 1);
//  }
//}
//
//
