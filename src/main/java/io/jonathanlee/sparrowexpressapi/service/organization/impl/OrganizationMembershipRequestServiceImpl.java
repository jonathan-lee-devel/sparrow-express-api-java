package io.jonathanlee.sparrowexpressapi.service.organization.impl;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationMembershipRequestResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationMembershipStatusResponseDto;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationMembershipRequestService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationMembershipRequestServiceImpl implements OrganizationMembershipRequestService {

  @Override
  public List<OrganizationMembershipRequestResponseDto> getRequestsToJoinOrganization(
      String requestingUserEmail, String organizationId) {
    return Collections.emptyList();
  }

  @Override
  public Optional<OrganizationMembershipStatusResponseDto> requestToJoinOrganization(
      String requestingUserEmail, String organizationId) {
    return Optional.empty();
  }

  @Override
  public Optional<OrganizationMembershipStatusResponseDto> approveRequestToJoinOrganization(
      String requestingUserEmail, String organizationMembershipRequestId) {
    return Optional.empty();
  }

}
