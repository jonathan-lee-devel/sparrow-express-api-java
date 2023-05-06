package io.jonathanlee.sparrowexpressapi.controller.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationEmailRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationSnippetResponseDto;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationService;
import io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils;
import io.jonathanlee.sparrowexpressapi.util.profile.ActiveProfileService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {

  private final ActiveProfileService activeProfileService;

  private final OrganizationService organizationService;

  @GetMapping(
      value = "/{organizationId}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> getOrganizationById(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @PathVariable String organizationId
  ) {
    if (OAuth2ClientUtils.isUnauthenticated(oAuth2AuthenticationToken, this.activeProfileService)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = OAuth2ClientUtils.getRequestingUserEmail(oAuth2AuthenticationToken, this.activeProfileService);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.getOrganizationById(requestingUserEmail, organizationId);
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  @GetMapping(
      value = "/{organizationId}/snippet",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationSnippetResponseDto> getOrganizationSnippetById(
      @PathVariable String organizationId
  ) {
    Optional<OrganizationSnippetResponseDto> organizationSnippetResponseDtoOptional = this.organizationService.getOrganizationSnippetById(organizationId);
    if (organizationSnippetResponseDtoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    OrganizationSnippetResponseDto organizationSnippetResponseDto = organizationSnippetResponseDtoOptional.get();
    return ResponseEntity.status(organizationSnippetResponseDto.getHttpStatus()).body(organizationSnippetResponseDto);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> createOrganization(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody OrganizationRequestDto organizationRequestDto
  ) {
    if (OAuth2ClientUtils.isUnauthenticated(oAuth2AuthenticationToken, this.activeProfileService)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = OAuth2ClientUtils.getRequestingUserEmail(oAuth2AuthenticationToken, this.activeProfileService);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.createOrganization(requestingUserEmail, organizationRequestDto);
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.internalServerError().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  @PatchMapping(
      value = "/remove-organization-administrator",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> removeOrganizationAdministrator(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody OrganizationEmailRequestDto organizationEmailRequestDto
  ) {
    if (OAuth2ClientUtils.isUnauthenticated(oAuth2AuthenticationToken, this.activeProfileService)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = OAuth2ClientUtils.getRequestingUserEmail(oAuth2AuthenticationToken, this.activeProfileService);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.removeOrganizationAdministrator(requestingUserEmail, organizationEmailRequestDto.getOrganizationId(), organizationEmailRequestDto.getUpdatedEmail());
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.internalServerError().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  @PatchMapping(
      value = "/remove-organization-member",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> removeOrganizationMember(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody OrganizationEmailRequestDto organizationEmailRequestDto
  ) {
    if (OAuth2ClientUtils.isUnauthenticated(oAuth2AuthenticationToken, this.activeProfileService)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = OAuth2ClientUtils.getRequestingUserEmail(oAuth2AuthenticationToken, this.activeProfileService);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.removeOrganizationMember(requestingUserEmail, organizationEmailRequestDto.getOrganizationId(), organizationEmailRequestDto.getUpdatedEmail());
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.internalServerError().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  @PatchMapping(
      value = "/update-organization-administrator-to-join-as-member",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> updateOrganizationAdministratorToJoinAsMember(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody OrganizationEmailRequestDto organizationEmailRequestDto
  ) {
    if (OAuth2ClientUtils.isUnauthenticated(oAuth2AuthenticationToken, this.activeProfileService)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = OAuth2ClientUtils.getRequestingUserEmail(oAuth2AuthenticationToken, this.activeProfileService);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.updateOrganizationAdministratorToJoinAsMember(requestingUserEmail, organizationEmailRequestDto.getOrganizationId(), organizationEmailRequestDto.getUpdatedEmail());
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.internalServerError().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

}
