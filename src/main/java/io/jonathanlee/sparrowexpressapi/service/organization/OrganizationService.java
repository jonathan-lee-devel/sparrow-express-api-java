package io.jonathanlee.sparrowexpressapi.service.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationSnippetResponseDto;
import java.util.Optional;

public interface OrganizationService {

  Optional<OrganizationResponseDto> getOrganizationById(String requestingUserEmail, String organizationId);

  Optional<OrganizationSnippetResponseDto> getOrganizationSnippetById(String organizationId);

  Optional<OrganizationResponseDto> createOrganization(String requestingUserEmail, OrganizationRequestDto organizationRequestDto);

  Optional<OrganizationResponseDto> removeOrganizationAdministrator(String requestingUserEmail, String organizationId, String administratorEmailToRemove);

  Optional<OrganizationResponseDto> removeOrganizationMember(String requestingUserEmail, String organizationId, String memberEmailToRemove);

  Optional<OrganizationResponseDto> updateOrganizationAdministratorToJoinAsMember(String requestingUserEmail, String organizationId, String administratorEmailToAddAsMember);

}
