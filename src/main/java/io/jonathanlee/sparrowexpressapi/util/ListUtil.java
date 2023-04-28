package io.jonathanlee.sparrowexpressapi.util;

import java.util.ArrayList;
import java.util.List;

public interface ListUtil {

  static <E> List<E> removeDuplicatesFromList(List<E> list) {
    List<E> listWithoutDuplicates = new ArrayList<>();
    list.parallelStream().distinct().forEach(listWithoutDuplicates::add);
    return listWithoutDuplicates;
  }

}
