package io.jonathanlee.sparrowexpressapi.util.oauth2;

import static io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils.EMAIL_ATTRIBUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

class OAuth2ClientUtilsTest {

  private final String TEST_EMAIL = "test@example.com";
  private final String TEST_NAME = "Test User";

  @Test
  void testGetEmailAttributeFromOAuth2AuthenticationToken() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put(OAuth2ClientUtils.EMAIL_ATTRIBUTE, TEST_EMAIL);
    attributes.put(OAuth2ClientUtils.NAME_ATTRIBUTE, TEST_NAME);
    OAuth2AuthenticationToken oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
        new DefaultOAuth2User(
            AuthorityUtils.NO_AUTHORITIES, attributes, EMAIL_ATTRIBUTE),
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        "regId"
    );
    String email = OAuth2ClientUtils.getEmailAttributeFromOAuth2AuthenticationToken(oAuth2AuthenticationToken);
    assertEquals(TEST_EMAIL, email);
  }

  @Test
  void testGetEmailAttributeFromOAuth2AuthenticationToken_nullToken() {
    String email = OAuth2ClientUtils.getEmailAttributeFromOAuth2AuthenticationToken(null);
    assertNull(email);
  }

  @Test
  void testGetEmailAttributeFromOAuth2AuthenticationToken_noEmailAttribute() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put(OAuth2ClientUtils.NAME_ATTRIBUTE, TEST_NAME);
    assertThrows(IllegalArgumentException.class, () -> {
      OAuth2AuthenticationToken oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
          new DefaultOAuth2User(
              AuthorityUtils.NO_AUTHORITIES, attributes, EMAIL_ATTRIBUTE),
          Collections.singleton(new SimpleGrantedAuthority("USER")),
          "regId"
      );
      String email = OAuth2ClientUtils.getEmailAttributeFromOAuth2AuthenticationToken(oAuth2AuthenticationToken);
      assertNull(email);
    });
  }

  @Test
  void testIsNoAuthentication() {
    OAuth2AuthenticationToken authenticationToken = null;
    boolean isNoAuth = OAuth2ClientUtils.isNoAuthentication(authenticationToken);
    assertTrue(isNoAuth);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new OAuth2AuthenticationToken(
          new DefaultOAuth2User(
              AuthorityUtils.NO_AUTHORITIES, Collections.emptyMap(), EMAIL_ATTRIBUTE),
          Collections.singleton(new SimpleGrantedAuthority("USER")),
          "regId"
      );
    });
  }

  @Test
  void testIsNoAuthentication_withEmailAttribute() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put(OAuth2ClientUtils.EMAIL_ATTRIBUTE, TEST_EMAIL);
    attributes.put(OAuth2ClientUtils.NAME_ATTRIBUTE, TEST_NAME);
    OAuth2AuthenticationToken oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
        new DefaultOAuth2User(
            AuthorityUtils.NO_AUTHORITIES, attributes, EMAIL_ATTRIBUTE),
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        "regId"
    );
    boolean isNoAuth = OAuth2ClientUtils.isNoAuthentication(oAuth2AuthenticationToken);
    assertFalse(isNoAuth);
  }
}
