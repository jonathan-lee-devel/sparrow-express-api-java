package io.jonathanlee.sparrowexpressapi.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ListUtil {

  static <E> List<E> removeDuplicatesFromList(List<E> list) {
    Set<E> set = new HashSet<>(list);
    return new ArrayList<>(set);
  }

}
