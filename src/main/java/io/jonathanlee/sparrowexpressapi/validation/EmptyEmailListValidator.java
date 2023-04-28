package io.jonathanlee.sparrowexpressapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import java.util.List;

public class EmptyEmailListValidator implements ConstraintValidator<EmptyEmailList, List<String>> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
    return this.validator.validate(email, Email.class).isEmpty();
  }
}
