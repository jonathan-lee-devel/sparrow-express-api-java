package io.jonathanlee.sparrowexpressapi.controller.organization;

import static io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils.EMAIL_ATTRIBUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jonathanlee.sparrowexpressapi.dto.organization.OrganizationResponseDto;
import io.jonathanlee.sparrowexpressapi.service.organization.OrganizationService;
import io.jonathanlee.sparrowexpressapi.util.profile.ActiveProfileService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

class OrganizationControllerTest {

  private static OAuth2AuthenticationToken createOAuth2AuthenticationToken(String emailAttribute) {
    return new OAuth2AuthenticationToken(
        new DefaultOAuth2User(
            AuthorityUtils.NO_AUTHORITIES, Collections.singletonMap(EMAIL_ATTRIBUTE, emailAttribute), EMAIL_ATTRIBUTE),
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        "regId"
    );
  }

  private OrganizationController organizationController;

  @Mock
  private ActiveProfileService activeProfileService;

  @Mock
  private OrganizationService organizationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    organizationController = new OrganizationController(activeProfileService, organizationService);
  }

  @Test
  void testGetOrganizationById() {
    // Setup
    OAuth2AuthenticationToken oAuth2AuthenticationToken = mock(OAuth2AuthenticationToken.class);
    String organizationId = "org-123";
    String userEmail = "user@example.com";
    OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();
    organizationResponseDto.setHttpStatus(HttpStatus.OK);

    when(activeProfileService.isLocalActiveProfile()).thenReturn(false);
    OAuth2AuthenticationToken auth2AuthenticationToken = createOAuth2AuthenticationToken(userEmail);
    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(auth2AuthenticationToken.getPrincipal());
    when(organizationService.getOrganizationById(userEmail, organizationId)).thenReturn(
        Optional.of(organizationResponseDto));

    // Execute
    ResponseEntity<OrganizationResponseDto> responseEntity = organizationController.getOrganizationById(
        oAuth2AuthenticationToken, organizationId);

    // Verify
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(organizationResponseDto, responseEntity.getBody());
  }

  @Test
  void testGetOrganizationByIdUnauthenticated() {
    // Setup
    OAuth2AuthenticationToken oAuth2AuthenticationToken = mock(OAuth2AuthenticationToken.class);
    String organizationId = "org-123";

    when(activeProfileService.isLocalActiveProfile()).thenReturn(false);
    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(null);

    // Execute
    ResponseEntity<OrganizationResponseDto> responseEntity = organizationController.getOrganizationById(
        oAuth2AuthenticationToken, organizationId);

    // Verify
    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void testGetOrganizationByIdNotFound() {
    // Setup
    OAuth2AuthenticationToken oAuth2AuthenticationToken = mock(OAuth2AuthenticationToken.class);
    String organizationId = "org-123";
    String userEmail = "user@example.com";

    when(activeProfileService.isLocalActiveProfile()).thenReturn(false);
    OAuth2AuthenticationToken auth2AuthenticationToken = createOAuth2AuthenticationToken(userEmail);
    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(auth2AuthenticationToken.getPrincipal());
    when(organizationService.getOrganizationById(userEmail, organizationId)).thenReturn(
        Optional.empty());

    // Execute
    ResponseEntity<OrganizationResponseDto> responseEntity = organizationController.getOrganizationById(
        oAuth2AuthenticationToken, organizationId);

    // Verify
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

}