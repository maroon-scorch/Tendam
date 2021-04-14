package edu.brown.cs.student.algorithm;

import edu.brown.cs.student.miscenllaneous.CustomException;
import edu.brown.cs.student.users.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that does property based testing.
 */
public class PropertyBasedTesting {

  private final Random rd = SecureRandom.getInstanceStrong(); //

  /**
   * Creates a new PropertyBasedTesting object.
   */
  public PropertyBasedTesting() throws NoSuchAlgorithmException {
  }

  /**
   * Runs n trials of property based testing.
   *
   * @param n    Number of trials to run
   * @param kMax Maximum number of persons in list
   * @return True if all n tests pass and false otherwise
   */
  public boolean nTrials(int n, int kMax) {
    for (int i = 0; i < n; i++) {
      int k = rd.nextInt(kMax) + 1;

      boolean once;

      // TODO: Fix catch statement from Exception
      try {
        once = oneTrial(k);
      } catch (Exception e) {
        System.err.println(e.getMessage());
        return false;
      }
      if (!once) {
        return false;
      }
    }

    return true;
  }

  /**
   * Run one trial of the property based test.
   *
   * @param k Number of users to be generated in list
   * @return True if test passes, false otherwise
   * @throws CustomException.NoMatchException if no matches could be made at all;
   *                                          represents a runtime error.
   */
  private boolean oneTrial(int k) throws CustomException.NoMatchException {
    List<User> personsList1 = generateInputs(0, k, k, 2 * k);
    List<User> personsList2 = generateInputs(k, 2 * k, 0, k);

//    System.out.println("List 1: ");
//    for (Person p : personsList1) {
//      System.out.println(p.getID() + ": " + p.getRankings().toString());
//    }
//
//    System.out.println("List 2: ");
//    for (Person p : personsList2) {
//      System.out.println(p.getID() + ": " + p.getRankings().toString());
//    }

    boolean test;
    try {
      test = galeShapleyProperty(personsList1, personsList2);
    } catch (Exception e) {
      System.out.println("on trial error");
      throw e;
    }

    // If any tests failed, then output the exact list and command it failed on
    if (!test) {
      System.out.println("Property Based Test failed");
      for (int i = 0; i < k; i++) {
        System.out.println(personsList1.get(i));
      }

      for (int i = 0; i < k; i++) {
        System.out.println(personsList2.get(i));
      }
      return false;
    } else {
      return true;
    }
  }


  /**
   * Generates input list of users.
   *
   * @param kMin minimum id
   * @param kMax maximum id
   * @return a list of users
   */
  private List<User> generateInputs(int kMin, int kMax, int prefKMin, int prefKMax) {
    List<User> persons = new ArrayList<>();
    List<Integer> personList = IntStream.range(kMin, kMax).boxed().collect(Collectors.toList());
    Collections.shuffle(personList);
    List<Integer> prefL = IntStream.range(prefKMin, prefKMax).boxed().collect(Collectors.toList());
    List<String> prefList = new ArrayList<>();
    for (Integer i : prefL) {
      prefList.add(i.toString());
    }

    for (int i = 0; i < kMax - kMin; i++) {
      //TODO: Checkstyle error. you should not be List.remove() within an ascending for loop
      String personID = personList.remove(0).toString();
      Collections.shuffle(prefList);
      List<String> preferenceList = new ArrayList<>(prefList);

      User toAdd = new User(personID, personID, new ArrayList<>());
      toAdd.setPreferences(preferenceList);

      persons.add(toAdd);
    }

    return persons;
  }

  /**
   * Property based testing for gale-shapley algorithm.
   *
   * @param p1 First list of users as input
   * @param p2 Second list of users as input
   * @return true if test passes and false otherwise
   * @throws CustomException.NoMatchException if no matches could be made at all
   */
  private boolean galeShapleyProperty(List<User> p1, List<User> p2)
          throws CustomException.NoMatchException {
    Map<User, User> pairings = GaleShapley.galeShapleyAlgo(p1, p2);
    Map<User, User> reversePairings = new HashMap<>();

    int count = 0;

    for (Map.Entry<User, User> entry : pairings.entrySet()) {
      reversePairings.put(entry.getValue(), entry.getKey());
      count++;
    }

    // TODO: Add docstrings for this
    // This for loop checks that all pairings are stable
    for (Map.Entry<User, User> entry : pairings.entrySet()) {
      if (!(isOneStable(p1, pairings, List.of(entry.getKey(), entry.getValue()))
              && isOneStable(p2, reversePairings, List.of(entry.getValue(), entry.getKey())))) {
        System.out.println("Incorrect pairing for: " + entry.getKey().getID()
                + " and " + entry.getValue().getID());
        return false;
      }
    }

    // This checks that there are no duplicates and that there is a right number of pairings
    Set<User> p1Set = new HashSet<>(p1);
    Set<User> p2Set = new HashSet<>(p2);
    return p1Set.size() >= p1.size() && p2Set.size() >= p2.size() && count == p1.size();
  }

  //TODO: Add docstrings for this
  private boolean isOneStable(List<User> p1, Map<User, User> pairings, List<User> pairing) {
    for (String personID : pairing.get(1).getRankings()) {
      if (personID.equals(pairing.get(0).getID())) {
        return true;
      }
      for (User p : p1) {
        if (p.getID().equals(personID)) {
          User currentPairingOfP = pairings.get(p);
          if (p.getRanking(currentPairingOfP.getID())
                  > p.getRanking(pairing.get(1).getID())) {
            return false;
          }
          break;
        }
      }
    }
    return false;
  }
}
