package io.jonathanlee.sparrowexpressapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class IndexController {

  private static final String NAME_ATTRIBUTE = "name";

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> index(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    if (oAuth2AuthenticationToken == null || oAuth2AuthenticationToken.getPrincipal() == null || oAuth2AuthenticationToken.getPrincipal().getAttributes().get(NAME_ATTRIBUTE) == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String name = oAuth2AuthenticationToken.getPrincipal().getAttributes().get(NAME_ATTRIBUTE).toString();
    return ResponseEntity.ok(
        String.format("{ \"greeting\": \"Hello %s\" }", name)
    );
  }

}
