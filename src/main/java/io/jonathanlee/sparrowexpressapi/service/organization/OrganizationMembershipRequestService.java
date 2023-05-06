package io.jonathanlee.sparrowexpressapi.service.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationMembershipRequestResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationMembershipStatusResponseDto;
import java.util.List;
import java.util.Optional;

public interface OrganizationMembershipRequestService {

  List<OrganizationMembershipRequestResponseDto> getRequestsToJoinOrganization(String requestingUserEmail, String organizationId);

  Optional<OrganizationMembershipStatusResponseDto> requestToJoinOrganization(String requestingUserEmail, String organizationId);

  Optional<OrganizationMembershipStatusResponseDto> approveRequestToJoinOrganization(String requestingUserEmail, String organizationMembershipRequestId);

}
