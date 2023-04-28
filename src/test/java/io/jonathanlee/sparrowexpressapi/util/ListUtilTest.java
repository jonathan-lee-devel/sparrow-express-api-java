package io.jonathanlee.sparrowexpressapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListUtilTest {

  @Test
  void testRemoveDuplicatesFromList() {
    List<String> inputList = new ArrayList<>(Arrays.asList("apple", "banana", "apple", "cherry", "banana"));
    List<String> expectedList = new ArrayList<>(Arrays.asList("apple", "banana", "cherry"));

    List<String> result = ListUtil.removeDuplicatesFromList(inputList);
    Collections.sort(result);

    Assertions.assertTrue(result.containsAll(expectedList));
    Assertions.assertEquals(expectedList, result);
  }

}
