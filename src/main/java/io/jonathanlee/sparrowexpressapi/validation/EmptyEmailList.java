package io.jonathanlee.sparrowexpressapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmptyEmailListValidator.class)
public @interface EmptyEmailList {
  String message() default "Invalid list of e-mail addresses";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
