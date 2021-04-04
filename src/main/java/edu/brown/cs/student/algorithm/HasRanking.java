package edu.brown.cs.student.algorithm;

import java.util.List;

public interface HasRanking<T extends HasRanking<T>> {
  List<Integer> getRankings();
  Integer getID();
  int getRanking(Integer obj);
}
