package io.jonathanlee.sparrowexpressapi.dto.organization;

import io.jonathanlee.sparrowexpressapi.constraint.CommonConstraints;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationEmailRequestDto {

  @NotNull
  @Size(
      min = CommonConstraints.ID_LENGTH,
      max = CommonConstraints.ID_LENGTH
  )
  private String organizationId;

  @NotNull
  @Email
  private String updatedEmail;

}
