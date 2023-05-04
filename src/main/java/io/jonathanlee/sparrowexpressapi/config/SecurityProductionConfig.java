package io.jonathanlee.sparrowexpressapi.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("production")
public class SecurityProductionConfig {

  private static final String COOKIE_NAME_TO_CLEAR = "JSESSIONID";

  private final ClientRegistrationRepository clientRegistrationRepository;

  @Value("${sparrow.environment.frontEndHost}")
  private String frontEndHost;

  @Value("${sparrow.environment.googleLoginHost}")
  private String googleLoginHost;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(urlBasedCorsConfigurationSource()))
        .authorizeHttpRequests()
        .requestMatchers("/logout-success")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer.defaultSuccessUrl("http://localhost:4200/login-success", true))
        .logout(httpSecurityLogoutConfigurer -> {
          httpSecurityLogoutConfigurer.logoutSuccessUrl("http://localhost:4200/logout-success");
          httpSecurityLogoutConfigurer.logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler());
          httpSecurityLogoutConfigurer.invalidateHttpSession(true);
          httpSecurityLogoutConfigurer.clearAuthentication(true);
          httpSecurityLogoutConfigurer.deleteCookies(COOKIE_NAME_TO_CLEAR);
          httpSecurityLogoutConfigurer.permitAll();
        });

    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    return new CorsFilter(urlBasedCorsConfigurationSource());
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    String[] allowedDomains = new String[]{frontEndHost, googleLoginHost};

    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedDomains);
      }
    };
  }

  private UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(List.of(frontEndHost, googleLoginHost));
    corsConfiguration.setAllowedHeaders(List.of(
        "Origin",
        "Access-Control-Allow-Origin",
        "Content-Type",
        "Accepts",
        "Authorization",
        "Origin, Accept",
        "X-Requested-With",
        "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
    ));
    corsConfiguration.setExposedHeaders(List.of(
        "Origin", "Content-Type", "Accept", "Authorization",
        "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
    ));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return urlBasedCorsConfigurationSource;
  }

  private OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(
        clientRegistrationRepository
    );
    successHandler.setPostLogoutRedirectUri("http://localhost:42000/logout-success");
    return successHandler;
  }

}
