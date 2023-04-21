package io.jonathanlee.sparrowexpressapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class IndexControllerTest {

  private static final String NAME_ATTRIBUTE = "name";

  @Mock
  private OAuth2AuthenticationToken oAuth2AuthenticationToken;

  @InjectMocks
  private IndexController indexController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testIndexSuccess() {
    Map<String, Object> attributes = new HashMap<>();
    final String nameAttributeValue = "John";
    attributes.put(NAME_ATTRIBUTE, nameAttributeValue);

    OAuth2User oAuth2User = new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        attributes,
        NAME_ATTRIBUTE
        );

    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(oAuth2User);

    ResponseEntity<String> response = indexController.index(oAuth2AuthenticationToken);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(String.format("{ \"greeting\": \"Hello %s\" }", nameAttributeValue), response.getBody());
  }

  @Test
  void testIndexWithNullToken() {
    ResponseEntity<String> response = indexController.index(null);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void testIndexWithNullPrincipal() {
    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(null);

    ResponseEntity<String> response = indexController.index(oAuth2AuthenticationToken);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void testIndexWithNullName() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put(NAME_ATTRIBUTE, null);

    OAuth2User oAuth2User = new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        attributes,
        NAME_ATTRIBUTE
    );

    when(oAuth2AuthenticationToken.getPrincipal()).thenReturn(oAuth2User);

    ResponseEntity<String> response = indexController.index(oAuth2AuthenticationToken);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

}
