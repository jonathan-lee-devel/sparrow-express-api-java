package io.jonathanlee.sparrowexpressapi.dto.organization;

import io.jonathanlee.sparrowexpressapi.constraint.CommonConstraints;
import io.jonathanlee.sparrowexpressapi.validation.EmptyEmailList;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDto {

  @NotNull
  @Size(
      min = CommonConstraints.MIN_TITLE_LENGTH,
      max = CommonConstraints.MAX_TITLE_LENGTH
  )
  private String name;

  @NotNull
  @EmptyEmailList
  private List<String> administratorEmails;

  @NotNull
  @EmptyEmailList
  private List<String> memberEmails;

}
