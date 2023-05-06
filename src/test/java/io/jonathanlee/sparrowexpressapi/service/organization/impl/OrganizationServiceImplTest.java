package io.jonathanlee.sparrowexpressapi.service.organization.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.organization.OrganizationMapper;
import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationModel;
import io.jonathanlee.sparrowexpressapi.repository.organization.OrganizationRepository;
import io.jonathanlee.sparrowexpressapi.service.random.RandomService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

  @Mock
  private OrganizationRepository organizationRepository;

  @Mock
  private OrganizationMapper organizationMapper;

  @Mock
  private RandomService randomService;

  @InjectMocks
  private OrganizationServiceImpl organizationService;

  @Test
  void testGetOrganizationByIdWithValidRequest() {
    String requestingUserEmail = "test@example.com";
    String organizationId = "1234";
    OrganizationModel organizationModel = new OrganizationModel();
    organizationModel.setAdministratorEmails(List.of("test@example.com"));
    organizationModel.setMemberEmails(List.of("user1@example.com"));
    when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organizationModel));
    OrganizationResponseDto expectedResponse = new OrganizationResponseDto();
    expectedResponse.setHttpStatus(HttpStatus.OK);
    when(organizationMapper.organizationModelToOrganizationResponseDto(organizationModel)).thenReturn(expectedResponse);
    Optional<OrganizationResponseDto> actualResponse = organizationService.getOrganizationById(requestingUserEmail, organizationId);
    assertTrue(actualResponse.isPresent());
    assertEquals(expectedResponse, actualResponse.get());
  }

  @Test
  void testGetOrganizationByIdWithInvalidRequest() {
    String requestingUserEmail = "test@example.com";
    String organizationId = "1234";
    OrganizationModel organizationModel = new OrganizationModel();
    organizationModel.setAdministratorEmails(List.of("user1@example.com"));
    organizationModel.setMemberEmails(List.of("user2@example.com"));
    when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organizationModel));
    OrganizationResponseDto expectedResponse = new OrganizationResponseDto();
    expectedResponse.setHttpStatus(HttpStatus.FORBIDDEN);
    Optional<OrganizationResponseDto> actualResponse = organizationService.getOrganizationById(requestingUserEmail, organizationId);
    assertTrue(actualResponse.isPresent());
    assertEquals(expectedResponse, actualResponse.get());
  }

  @Test
  void testGetOrganizationByIdWithNonexistentOrganization() {
    String requestingUserEmail = "test@example.com";
    String organizationId = "1234";
    when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());
    Optional<OrganizationResponseDto> actualResponse = organizationService.getOrganizationById(requestingUserEmail, organizationId);
    assertFalse(actualResponse.isPresent());
  }

  @Test
  void testCreateOrganization() {
    String requestingUserEmail = "test@example.com";
    String organizationId = "1234";
    OrganizationRequestDto organizationRequestDto = new OrganizationRequestDto();
    OrganizationModel organizationModel = new OrganizationModel();
    organizationModel.setId(organizationId);
    organizationModel.setAdministratorEmails(List.of("test@example.com"));
    organizationModel.setMemberEmails(List.of("user1@example.com"));
    when(randomService.generateNewId()).thenReturn(organizationId);
    when(organizationMapper.organizationRequestDtoToOrganizationModel(organizationRequestDto)).thenReturn(organizationModel);
    when(organizationRepository.save(organizationModel)).thenReturn(organizationModel);
    OrganizationResponseDto expectedResponse = new OrganizationResponseDto();
    expectedResponse.setHttpStatus(HttpStatus.CREATED);
    when(organizationMapper.organizationModelToOrganizationResponseDto(organizationModel)).thenReturn(expectedResponse);
    Optional<OrganizationResponseDto> actualResponse = organizationService.createOrganization(requestingUserEmail, organizationRequestDto);
    assertTrue(actualResponse.isPresent());
    assertEquals(expectedResponse, actualResponse.get());
  }

  @Test
  void testRemoveOrganizationAdministratorSuccess() {
    OrganizationModel organizationModel = new OrganizationModel();
    organizationModel.setName("Org1");
    organizationModel.setId("org1");
    organizationModel.setObjectId(ObjectId.get());
    organizationModel.setAdministratorEmails(List.of("admin1@test.com"));
    organizationModel.setMemberEmails(Collections.emptyList());
    List<String> administratorEmails = new ArrayList<>();
    administratorEmails.add("admin1@test.com");
    administratorEmails.add("admin2@test.com");
    organizationModel.setAdministratorEmails(administratorEmails);
    when(organizationRepository.findById("org1")).thenReturn(Optional.of(organizationModel));
    when(organizationRepository.save(any(OrganizationModel.class))).thenReturn(organizationModel);
    OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
    organizationResponseDto.setHttpStatus(HttpStatus.OK);
    organizationResponseDto.setName(organizationModel.getName());
    organizationResponseDto.setId(organizationModel.getId());
    organizationResponseDto.setAdministratorEmails(organizationModel.getAdministratorEmails());
    organizationResponseDto.setMemberEmails(organizationModel.getMemberEmails());
    when(organizationMapper.organizationModelToOrganizationResponseDto(any(OrganizationModel.class))).thenReturn(organizationResponseDto);

    Optional<OrganizationResponseDto> result = organizationService.removeOrganizationAdministrator("admin1@test.com", "org1", "admin2@test.com");

    assertTrue(result.isPresent());
    assertEquals(HttpStatus.OK, result.get().getHttpStatus());
    assertEquals(List.of("admin1@test.com"), result.get().getAdministratorEmails());
  }

  @Test
  void testRemoveOrganizationAdministratorFailureNoOrganization() {
    when(organizationRepository.findById("org1")).thenReturn(Optional.empty());

    Optional<OrganizationResponseDto> result = organizationService.removeOrganizationAdministrator("admin1@test.com", "org1", "admin2@test.com");

    assertEquals(Optional.empty(), result);
  }

  @Test
  void testRemoveOrganizationAdministratorFailureNotAdmin() {
    OrganizationModel organizationModel = new OrganizationModel();
    organizationModel.setAdministratorEmails(List.of("admin1@test.com", "admin2@test.com"));
    when(organizationRepository.findById("org1")).thenReturn(Optional.of(organizationModel));
    OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
    organizationResponseDto.setHttpStatus(HttpStatus.FORBIDDEN);
    organizationResponseDto.setAdministratorEmails(List.of("admin1@test.com"));

    Optional<OrganizationResponseDto> result = organizationService.removeOrganizationAdministrator("user@test.com", "org1", "admin2@test.com");

    assertTrue(result.isPresent());
    assertEquals(HttpStatus.FORBIDDEN, result.get().getHttpStatus());
  }

}
