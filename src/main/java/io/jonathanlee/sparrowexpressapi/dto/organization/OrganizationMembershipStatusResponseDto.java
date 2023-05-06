package io.jonathanlee.sparrowexpressapi.dto.organization;

import io.jonathanlee.sparrowexpressapi.dto.ResponseDto;
import io.jonathanlee.sparrowexpressapi.enums.organization.OrganizationMembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrganizationMembershipStatusResponseDto extends ResponseDto {

  private OrganizationMembershipStatus organizationMembershipStatus;

}
