package io.jonathanlee.sparrowexpressapi.controller;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
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

  @GetMapping
  public ResponseEntity<String> index(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
    String name = Objects.requireNonNull(oAuth2AuthenticationToken.getPrincipal().getAttributes().get(NAME_ATTRIBUTE))
        .toString();
    return ResponseEntity.ok(
        String.format("Hello %s", name)
    );
  }

}
