package io.jonathanlee.sparrowexpressapi.validation.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorsContainerDto {

  private Collection<ValidationErrorDto> errors;

}
