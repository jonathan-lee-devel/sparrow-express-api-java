package io.jonathanlee.sparrowexpressapi.service.organization.impl;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationInvitationResponseDto;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationInvitationService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationInvitationServiceImpl implements OrganizationInvitationService {

  @Override
  public Optional<OrganizationInvitationResponseDto> inviteToJoinOrganization(
      String requestingUserEmail, String organizationId, String emailToInvite) {
    return Optional.empty();
  }

}
