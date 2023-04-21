package io.jonathanlee.sparrowexpressapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String COOKIE_NAME_TO_CLEAR = "JSESSIONID";

  private final ClientRegistrationRepository clientRegistrationRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests()
        .requestMatchers("/logout-success")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .defaultSuccessUrl("http://localhost:4200/login-success", true)
        .and()
        .logout()
        .logoutSuccessUrl("http://localhost:4200/logout-success")
        .logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler())
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies(COOKIE_NAME_TO_CLEAR);

    return http.build();
  }

  private OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(
        clientRegistrationRepository
    );
    successHandler.setPostLogoutRedirectUri("http://localhost:42000/logout-success");
    return successHandler;
  }

}
