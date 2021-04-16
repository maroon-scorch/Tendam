package edu.brown.cs.student.algorithm;


import edu.brown.cs.student.miscenllaneous.CustomException;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Class that implements and runs the gale shapley algorithm.
 */
public final class GaleShapley {

  /**
   * Private unused constructor.
   */
  private GaleShapley() {
  }

  /**
   * Runs the Gale Shapley Algorithm.
   *
   * @param listOne the first list of users
   * @param listTwo the second list of users. For our purposes this is identical to list one.
   * @param <T>     A HasRanking implementation class
   * @return a a HasRanking implementation class
   * @throws CustomException.NoMatchException if no matches could be found at all.
   *                                          Represents a RuntimeError
   */
  public static <T extends HasRanking<T>> Map<T, T> galeShapleyAlgo(
          List<T> listOne, List<T> listTwo) throws CustomException.NoMatchException {
    // freeOne is a queue of all free objects from first list
    Queue<T> freeOne = new ArrayDeque<>(listOne);
    Map<T, T> pairings = new HashMap<>();
    Map<T, T> reversePairings = new HashMap<>();

    // While loop runs until freeOne is empty so that we create all matches
    while (!freeOne.isEmpty()) {
      T one = freeOne.poll();
      boolean notMatched = true;
      for (String twoId : one.getRankings()) {
        if (!notMatched) {
          break;
        }
        T two = null;

        for (T obj : listTwo) {
          if (obj.getID().equals(twoId)) {
            two = obj;
            break;
          }
        }

        if (two == null) {
          throw new CustomException.NoMatchException();
        } else {
          // If two does not have a match then one matches with two
          if (reversePairings.get(two) == null) {
            pairings.put(one, two);
            reversePairings.put(two, one);
            notMatched = false;
          } else {
            // Otherwise, one matches with two only if two would rather match with one
            // compared to their current match
            T onePrime = reversePairings.get(two);
            // If two prefers one, two's previous match becomes free and one matches with two
            // Otherwise we loop one more to one's next preference
            if (two.getRanking(one.getID()) < two.getRanking(onePrime.getID())) {
              pairings.put(one, two);
              reversePairings.put(two, one);
              pairings.remove(onePrime);
              freeOne.offer(onePrime);
              notMatched = false;
            }
          }
        }
      }
    }
    return pairings;
  }
}
