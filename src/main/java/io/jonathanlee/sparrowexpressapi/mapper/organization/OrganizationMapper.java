package io.jonathanlee.sparrowexpressapi.mapper.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationSnippetResponseDto;
import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

  @Mapping(source = "id", target = "id")
  OrganizationResponseDto organizationModelToOrganizationResponseDto(OrganizationModel organizationModel);

  @Mapping(source = "id", target = "id")
  OrganizationSnippetResponseDto organizationModelToOrganizationSnippetResponseDto(OrganizationModel organizationModel);

  @Mapping(source = "name", target = "name")
  OrganizationModel organizationRequestDtoToOrganizationModel(OrganizationRequestDto organizationRequestDto);

}
