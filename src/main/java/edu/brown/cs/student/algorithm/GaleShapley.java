package edu.brown.cs.student.algorithm;


import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GaleShapley {
  public static <T extends HasRanking<T>> Map<T, T> galeShapleyAlgo(
          List<T> listOne, List<T> listTwo) {
    Queue<T> freeOne = new ArrayDeque<>(listOne);
    Map<T, T> pairings = new HashMap<>();
    Map<T, T> reversePairings = new HashMap<>();
    // boolean isError = false;
    while (!freeOne.isEmpty()) {
      // TODO: Make sure to remove one from freeOne and sometimes add back onePrime to freeOne
      T one = freeOne.poll();
      boolean notMatched = true;
      for (String twoId : one.getRankings()) {
        if (!notMatched) {
          break;
        }
        //two is one’s highest ranked such person in listTwo to whom they have not yet proposed
        T two = null;

        for (T obj : listTwo) {
          if (obj.getID().equals(twoId)) {
            two = obj;
            break;
          }
        }

        if (two == null) {
          throw new RuntimeException("ERROR: Could not find matching");
        } else {
          if (reversePairings.get(two) == null) {
            pairings.put(one, two);
            reversePairings.put(two, one);
            notMatched = false;
          } else {
            T onePrime = reversePairings.get(two);
            // two prefers one to onePrime
            if (two.getRanking(one.getID()) < two.getRanking(onePrime.getID())) {
              // (one, two) become engaged
              pairings.put(one, two);
              reversePairings.put(two, one);
              pairings.remove(onePrime);
              freeOne.offer(onePrime);
              notMatched = false;
              // onePrime becomes free
            }
          }
        }
      }
    }
    return pairings;
  }
}
