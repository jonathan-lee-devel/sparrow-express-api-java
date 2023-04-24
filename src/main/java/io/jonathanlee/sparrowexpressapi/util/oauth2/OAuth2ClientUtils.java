package io.jonathanlee.sparrowexpressapi.util.oauth2;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public abstract class OAuth2ClientUtils {

  public static final String NAME_ATTRIBUTE = "name";

  public static final String EMAIL_ATTRIBUTE = "email";
  public static final String DEFAULT_LOCAL_EMAIL = "test@example.com";

  public static String getEmailAttributeFromOAuth2AuthenticationToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    if (oAuth2AuthenticationToken == null || oAuth2AuthenticationToken.getPrincipal() == null) {
      return null;
    }
    return oAuth2AuthenticationToken.getPrincipal().getAttributes().get(EMAIL_ATTRIBUTE).toString();
  }

  public static boolean isNoAuthentication(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    return (oAuth2AuthenticationToken == null || oAuth2AuthenticationToken.getPrincipal() == null || oAuth2AuthenticationToken.getPrincipal().getAttributes().get(EMAIL_ATTRIBUTE) == null);
  }

  private OAuth2ClientUtils() {
    // Private constructor to hide the implicit public constructor
  }

}
