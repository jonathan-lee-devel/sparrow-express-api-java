package io.jonathanlee.sparrowexpressapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class EmptyEmailListValidator implements ConstraintValidator<EmptyEmailList, List<String>> {

  private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-]+)@([a-zA-Z0-9_\\-]+)\\.([a-zA-Z]{2,5})$";

  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


  @Override
  public boolean isValid(List<String> emailList, ConstraintValidatorContext context) {
    if (emailList == null) {
      return false;
    }

    for (String email : emailList) {
      if (!isValidEmail(email)) {
        return false;
      }
    }

    return true;
  }

  private boolean isValidEmail(String email) {
    return email != null && EMAIL_PATTERN.matcher(email).matches();
  }

}
