package io.jonathanlee.sparrowexpressapi.service.organization.impl;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.organization.OrganizationMapper;
import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationModel;
import io.jonathanlee.sparrowexpressapi.repository.organization.OrganizationRepository;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationService;
import io.jonathanlee.sparrowexpressapi.service.random.RandomService;
import io.jonathanlee.sparrowexpressapi.util.ListUtil;
import java.util.List;
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
    if (!organizationModel.getAdministratorEmails().contains(requestingUserEmail) &&
        !organizationModel.getMemberEmails().contains(requestingUserEmail)) {
      organizationResponseDto.setHttpStatus(HttpStatus.FORBIDDEN);
      return Optional.of(organizationResponseDto);
    }
    organizationResponseDto = this.organizationMapper.organizationModelToOrganizationResponseDto(organizationModel);
    organizationResponseDto.setHttpStatus(HttpStatus.OK);
    return Optional.of(organizationResponseDto);
  }

  @Override
  public Optional<OrganizationResponseDto> createOrganization(String requestingUserEmail, OrganizationRequestDto organizationRequestDto) {
    OrganizationModel organizationModel = this.organizationMapper.organizationRequestDtoToOrganizationModel(organizationRequestDto);
    organizationModel.setId(this.randomService.generateNewId());
    organizationModel.setAdministratorEmails(List.of(requestingUserEmail));
    organizationModel.setObjectId(ObjectId.get());
    OrganizationResponseDto organizationResponseDto = this.organizationMapper.organizationModelToOrganizationResponseDto(organizationModel);
    organizationResponseDto.setHttpStatus(HttpStatus.CREATED);
    return Optional.of(organizationResponseDto);
  }

  @Override
  public Optional<OrganizationResponseDto> updateOrganization(
      String requestingUserEmail,
      String organizationId,
      OrganizationRequestDto organizationRequestDto
  ) {
    Optional<OrganizationModel> organizationModelOptional = this.organizationRepository.findById(organizationId);
    if (organizationModelOptional.isEmpty()) {
      return Optional.empty();
    }
    OrganizationModel organizationModel = organizationModelOptional.get();
    OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
    if (!organizationModel.getAdministratorEmails().contains(requestingUserEmail)) {
      organizationResponseDto.setHttpStatus(HttpStatus.FORBIDDEN);
      return Optional.of(organizationResponseDto);
    }
    organizationModel.setName(organizationRequestDto.getName());
    organizationModel.setAdministratorEmails(ListUtil.removeDuplicatesFromList(organizationModel.getAdministratorEmails()));
    organizationModel.setMemberEmails(ListUtil.removeDuplicatesFromList(organizationModel.getMemberEmails()));
    organizationResponseDto.setHttpStatus(HttpStatus.NO_CONTENT);
    return Optional.of(organizationResponseDto);
  }

}