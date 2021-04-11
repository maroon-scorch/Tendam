package edu.brown.cs.student.algorithm;

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

  /**
   * Creates a new PropertyBasedTesting object.
   */
  public PropertyBasedTesting() {
  }

  /**
   * Runs n trials of property based testing.
   *
   * @param n    Number of trials to run
   * @param kMax Maximum number of persons in list
   * @return True if all n tests pass and false otherwise
   */
  public boolean nTrials(int n, int kMax) {
    Random rd = new Random();
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
   * @param k Number of persons to be generated in list
   * @return True if test passes, false otherwise
   */
  private boolean oneTrial(int k) {
    List<Person> personsList1 = generateInputs(0, k, k, 2*k);
    List<Person> personsList2 = generateInputs(k, 2*k, 0, k);

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
   * Generates input list of persons.
   *
   * @param kMin minimum id
   * @param kMax maximum id
   * @return
   */
  private List<Person> generateInputs(int kMin, int kMax, int prefKMin, int prefKMax) {
    List<Person> persons = new ArrayList<>();
    List<Integer> personList = IntStream.range(kMin, kMax).boxed().collect(Collectors.toList());
    Collections.shuffle(personList);
    List<Integer> prefL = IntStream.range(prefKMin, prefKMax).boxed().collect(Collectors.toList());
    List<String> prefList = new ArrayList<>();
    for (Integer i : prefL) {
      prefList.add(i.toString());
    }

    for (int i = 0; i < kMax - kMin; i++) {
      String personID = personList.remove(0).toString();
      Collections.shuffle(prefList);
      List<String> preferenceList = new ArrayList<>(prefList);

      Person toAdd = new Person(personID, preferenceList);

      persons.add(toAdd);
    }

    return persons;
  }

  /**
   * Property based testing for gale-shapley algorithm.
   *
   * @param p1 First list of persons as input
   * @param p2 Second list of persons as input
   * @return true if test passes and false otherwise
   */
  private boolean galeShapleyProperty(List<Person> p1, List<Person> p2) {
    Map<Person, Person> pairings = GaleShapley.galeShapleyAlgo(p1, p2);
    Map<Person, Person> reversePairings = new HashMap<>();

    int count = 0;

    for (Map.Entry<Person, Person> entry : pairings.entrySet()) {
      reversePairings.put(entry.getValue(), entry.getKey());
      count++;
    }

    // This for loop checks that all pairings are stable
    for (Map.Entry<Person, Person> entry : pairings.entrySet()) {
      if (!(isOneStable(p1, pairings, List.of(entry.getKey(), entry.getValue()))
              && isOneStable(p2, reversePairings, List.of(entry.getValue(), entry.getKey())))) {
        System.out.println("Incorrect pairing for: " + entry.getKey().getID()
                + " and " + entry.getValue().getID());
        return false;
      }
    }

    // This checks that there are no duplicates and that there is a right number of pairings
    Set<Person> p1Set = new HashSet<>(p1);
    Set<Person> p2Set = new HashSet<>(p2);
    if (p1Set.size() < p1.size() || p2Set.size() < p2.size() || count != p1.size()) {
      return false;
    }

    return true;
  }

  private boolean isOneStable(List<Person> p1, Map<Person, Person> pairings, List<Person> pairing) {
    for (String personID : pairing.get(1).getRankings()) {
      if (personID.equals(pairing.get(0).getID())) {
        return true;
      }
      for (Person p : p1) {
        if (p.getID().equals(personID)) {
          Person currentPairingOfP = pairings.get(p);
          if (p.getRanking(currentPairingOfP.getID()) > p.getRanking(pairing.get(1).getID())) {
            return false;
          }
          break;
        }
      }
    }
    return false;
  }
}
