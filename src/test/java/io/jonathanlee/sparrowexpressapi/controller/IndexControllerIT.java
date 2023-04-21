package io.jonathanlee.sparrowexpressapi.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(IndexController.class)
class IndexControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OAuth2AuthenticationToken mockOAuth2AuthenticationToken;

  @BeforeEach
  void setUp() {
    OAuth2User oAuth2User = new DefaultOAuth2User(
        Collections.singleton(new OAuth2UserAuthority(Collections.singletonMap("name", "John"))),
        Collections.singletonMap("name", "John"),
        "name"
    );
    when(mockOAuth2AuthenticationToken.getPrincipal()).thenReturn(oAuth2User);
  }

  @Test
  void testIndex_withNullPrincipal_returnsBadRequest() throws Exception {
    when(mockOAuth2AuthenticationToken.getPrincipal()).thenReturn(null);

    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
  }
}
