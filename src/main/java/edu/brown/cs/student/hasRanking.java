package edu.brown.cs.student;

import java.util.List;

public interface hasRanking<T extends hasRanking<T>> {
  List<String> getRankings();
  String getID();
  int getRanking(String obj);
}
