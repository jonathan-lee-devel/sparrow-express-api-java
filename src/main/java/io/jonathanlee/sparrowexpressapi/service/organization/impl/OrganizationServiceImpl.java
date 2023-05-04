package io.jonathanlee.sparrowexpressapi.service.organization.impl;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationSnippetResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.organization.OrganizationMapper;
import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationModel;
import io.jonathanlee.sparrowexpressapi.repository.organization.OrganizationRepository;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationService;
import io.jonathanlee.sparrowexpressapi.service.random.RandomService;
import io.jonathanlee.sparrowexpressapi.util.ListUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  private final OrganizationMapper organizationMapper;

  private final RandomService randomService;

  @Override
  public Optional<OrganizationResponseDto> getOrganizationById(String requestingUserEmail, String organizationId) {
    Optional<OrganizationModel> organizationModelOptional = this.organizationRepository.findById(organizationId);
    if (organizationModelOptional.isEmpty()) {
      return Optional.empty();
    }
    OrganizationModel organizationModel = organizationModelOptional.get();
    OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
    if (!isOrganizationAdministrator(organizationModel, requestingUserEmail) && !isOrganizationMember(organizationModel, requestingUserEmail)) {
      organizationResponseDto.setHttpStatus(HttpStatus.FORBIDDEN);
      return Optional.of(organizationResponseDto);
    }
    organizationResponseDto = this.organizationMapper.organizationModelToOrganizationResponseDto(organizationModel);
    organizationResponseDto.setHttpStatus(HttpStatus.OK);
    return Optional.of(organizationResponseDto);
  }

  @Override
  public Optional<OrganizationSnippetResponseDto> getOrganizationSnippetById(String organizationId) {
    Optional<OrganizationModel> organizationModelOptional = this.organizationRepository.findById(organizationId);
    if (organizationModelOptional.isEmpty()) {
      return Optional.empty();
    }
    OrganizationModel organizationModel = organizationModelOptional.get();
    OrganizationSnippetResponseDto organizationSnippetResponseDto = this.organizationMapper.organizationModelToOrganizationSnippetResponseDto(organizationModel);
    organizationSnippetResponseDto.setHttpStatus(HttpStatus.OK);
    return Optional.of(organizationSnippetResponseDto);
  }


  @Override
  public Optional<OrganizationResponseDto> createOrganization(String requestingUserEmail, OrganizationRequestDto organizationRequestDto) {
    OrganizationModel organizationModel = this.organizationMapper.organizationRequestDtoToOrganizationModel(organizationRequestDto);
    organizationModel.setId(this.randomService.generateNewId());
    organizationModel.setAdministratorEmails(ListUtil.removeDuplicatesFromList(organizationModel.getAdministratorEmails()));
    organizationModel.setMemberEmails(ListUtil.removeDuplicatesFromList(organizationModel.getMemberEmails()));
    if (!organizationModel.getAdministratorEmails().contains(requestingUserEmail)) {
      organizationModel.getAdministratorEmails().add(requestingUserEmail);// Ensure the creator has administrative privileges
    }
    organizationModel.setObjectId(ObjectId.get());
    OrganizationResponseDto organizationResponseDto = this.organizationMapper.organizationModelToOrganizationResponseDto(
        this.organizationRepository.save(organizationModel)
    );
    organizationResponseDto.setHttpStatus(HttpStatus.CREATED);
    return Optional.of(organizationResponseDto);
  }

  private boolean isOrganizationAdministrator(OrganizationModel organizationModel, String requestingUserEmail) {
    return organizationModel.getAdministratorEmails().contains(requestingUserEmail);
  }

  private boolean isOrganizationMember(OrganizationModel organizationModel, String requestingUserEmail) {
    return organizationModel.getMemberEmails().contains(requestingUserEmail);
  }

}
