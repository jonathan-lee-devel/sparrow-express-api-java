package io.jonathanlee.sparrowexpressapi.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class EmptyEmailListValidatorTest {

  private final EmptyEmailListValidator validator = new EmptyEmailListValidator();

  @Test
  void testValidEmailList() {
    List<String> emailList = Arrays.asList("john@example.com", "jane@example.com");

    boolean isValid = validator.isValid(emailList, mock(ConstraintValidatorContext.class));

    assertTrue(isValid);
  }

  @Test
  void testEmptyEmailList() {
    List<String> emailList = Collections.emptyList();

    boolean isValid = validator.isValid(emailList, mock(ConstraintValidatorContext.class));

    assertTrue(isValid);
  }

  @Test
  void testNullEmailList() {
    boolean isValid = validator.isValid(null, mock(ConstraintValidatorContext.class));

    assertFalse(isValid);
  }

  @Test
  void testEmailListContainsNull() {
    List<String> list = new ArrayList<>();
    list.add("john@example.com");
    list.add(null);
    boolean isValid = validator.isValid(list, mock(ConstraintValidatorContext.class));

    assertFalse(isValid);
  }

  @Test
  void testInvalidEmail() {
    List<String> emailList = Arrays.asList("john@example.com", "invalid-email");

    boolean isValid = validator.isValid(emailList, mock(ConstraintValidatorContext.class));

    assertFalse(isValid);
  }

}
