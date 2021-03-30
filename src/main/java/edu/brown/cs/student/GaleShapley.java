package edu.brown.cs.student;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaleShapley {
  public static <T extends hasRanking<T>> Map<T, T> galeShapleyAlgo(
          List<T> listOne, List<T> listTwo) {
    List<T> freeOne = new ArrayList<>(listOne);
    Map<T, T> pairings = new HashMap<>();
    Map<T, T> reversePairings = new HashMap<>();
    // boolean isError = false;
    while (!freeOne.isEmpty()) {
      // TODO: Make sure to remove one from freeOne and sometimes add back onePrime to freeOne
      T one = freeOne.get(0);
      boolean notMatched = true;
      int i = 0;
      while (notMatched) {
        //two is one’s highest ranked such person in listTwo to whom they have not yet proposed
        String twoId = one.getRankings().get(i);
        T two = null;

        for (T obj : listTwo) {
          if (obj.getID().equals(twoId)) {
            two = obj;
            break;
          }
        }

        if (two == null) {
          throw new RuntimeException("ERROR: Could not find matching");
//          notMatched = true;
//          isError = true;
        } else {
          if (reversePairings.get(two) == null) {
            pairings.put(one, two);
            reversePairings.put(two, one);
            freeOne.remove(one);
            notMatched = false;
          } else {
            T onePrime = reversePairings.get(two);
            // two prefers one to onePrime
            if (two.getRanking(one.getID()) < two.getRanking(onePrime.getID())) {
              // (one, two) become engaged
              pairings.put(one, two);
              reversePairings.put(two, one);
              pairings.put(onePrime, null);
              freeOne.remove(one);
              freeOne.add(onePrime);
              notMatched = false;
              // onePrime becomes free
            } else {
              // (onePrime, two) remain engaged  (do nothing)
              i++;
            }
          }
        }
      }
    }
    return pairings;
  }
}
