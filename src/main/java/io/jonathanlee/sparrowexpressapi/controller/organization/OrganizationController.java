package io.jonathanlee.sparrowexpressapi.controller.organization;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
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
    if (isUnauthenticated(oAuth2AuthenticationToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = getRequestingUserEmail(oAuth2AuthenticationToken);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.getOrganizationById(requestingUserEmail, organizationId);
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationResponseDto> createOrganization(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody OrganizationRequestDto organizationRequestDto
  ) {
    if (isUnauthenticated(oAuth2AuthenticationToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String requestingUserEmail = getRequestingUserEmail(oAuth2AuthenticationToken);
    Optional<OrganizationResponseDto> organizationResponseDtoOptional = this.organizationService.createOrganization(requestingUserEmail, organizationRequestDto);
    if (organizationResponseDtoOptional.isEmpty()) {
      return ResponseEntity.internalServerError().build();
    }
    OrganizationResponseDto organizationResponseDto = organizationResponseDtoOptional.get();
    return ResponseEntity.status(organizationResponseDto.getHttpStatus()).body(organizationResponseDto);
  }

  private String getRequestingUserEmail(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    return this.activeProfileService.isLocalActiveProfile() ?
        OAuth2ClientUtils.DEFAULT_LOCAL_EMAIL :
        oAuth2AuthenticationToken.getPrincipal().getAttribute(OAuth2ClientUtils.EMAIL_ATTRIBUTE);
  }

  private boolean isUnauthenticated(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    boolean isNoAuthentication = OAuth2ClientUtils.isNoAuthentication(oAuth2AuthenticationToken);
    return isNoAuthentication && !this.activeProfileService.isLocalActiveProfile();
  }

}
