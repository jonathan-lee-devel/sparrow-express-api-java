package io.jonathanlee.sparrowexpressapi.service.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationInvitationResponseDto;
import java.util.Optional;

public interface OrganizationInvitationService {

  Optional<OrganizationInvitationResponseDto> inviteToJoinOrganization(String requestingUserEmail, String organizationId, String emailToInvite);

}
