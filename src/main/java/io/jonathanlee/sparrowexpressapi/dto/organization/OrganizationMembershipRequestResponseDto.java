package io.jonathanlee.sparrowexpressapi.dto.organization;

import io.jonathanlee.sparrowexpressapi.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrganizationMembershipRequestResponseDto extends ResponseDto {

  private String id;

  private String organizationId;

  private String requestingUserEmail;

  private boolean approved;

  private String approvingAdministratorEmail;

}
