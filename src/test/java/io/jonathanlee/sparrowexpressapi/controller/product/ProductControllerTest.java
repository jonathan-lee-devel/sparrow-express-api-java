package io.jonathanlee.sparrowexpressapi.controller.product;

import static io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils.EMAIL_ATTRIBUTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import io.jonathanlee.sparrowexpressapi.util.profile.ActiveProfileService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  private static ProductResponseDto createProductResponseDto(String productId) {
    return new ProductResponseDto(productId, "test@example.com", "Org-123", "Title", BigDecimal.ZERO);
  }

  private static ProductResponseDto createProductResponseDto() {
    return createProductResponseDto("123");
  }

  private static ProductRequestDto createProductRequestDto() {
    return new ProductRequestDto("1234", "Title", BigDecimal.ZERO);
  }

  private static OAuth2AuthenticationToken createOAuth2AuthenticationToken(String emailAttribute) {
    return new OAuth2AuthenticationToken(
        new DefaultOAuth2User(AuthorityUtils.NO_AUTHORITIES, Collections.singletonMap(EMAIL_ATTRIBUTE, emailAttribute), EMAIL_ATTRIBUTE),
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        "regId"
    );
  }

  private static OAuth2AuthenticationToken createOAuth2AuthenticationToken() {
    return createOAuth2AuthenticationToken("test@example.com");
  }

  private ProductController productController;

  @Mock
  private ProductService productService;

  @Mock
  private ActiveProfileService activeProfileService;

  @BeforeEach
  void setUp() {
    this.productController = new ProductController(activeProfileService, productService);
  }

    @Test
    @DisplayName("returns product response dto when product exists")
    void testGetProductById_returnsProductResponseDto_whenProductExists() {
      String productId = "123";
      ProductResponseDto productResponseDto = createProductResponseDto(productId);
      when(productService.getProductById(productId)).thenReturn(Optional.of(productResponseDto));

      ResponseEntity<ProductResponseDto> response = productController.getProductById(productId);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isEqualTo(productResponseDto);
    }

    @Test
    @DisplayName("returns not found when product does not exist")
    void testGetProductById_returnsNotFound_whenProductDoesNotExist() {
      String productId = "123";
      when(productService.getProductById(productId)).thenReturn(Optional.empty());

      ResponseEntity<ProductResponseDto> response = productController.getProductById(productId);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("returns created product response dto when valid input")
    void testCreateProduct_returnsCreatedProductResponseDto_whenValidInput() {
      String creatorEmail = "test@example.com";
      ProductRequestDto productRequestDto = createProductRequestDto();
      ProductResponseDto productResponseDto = createProductResponseDto();
      when(productService.createProduct(creatorEmail, productRequestDto)).thenReturn(Optional.of(productResponseDto));
      OAuth2AuthenticationToken oAuth2AuthenticationToken = createOAuth2AuthenticationToken(creatorEmail);

      ResponseEntity<ProductResponseDto> response = productController.createProduct(oAuth2AuthenticationToken, productRequestDto);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
      assertThat(response.getBody()).isEqualTo(productResponseDto);
    }

    @Test
    @DisplayName("returns unauthorized when authentication token is null")
    void testCreateProduct_returnsUnauthorized_whenOAuth2AuthenticationTokenIsNull() {
      ProductRequestDto productRequestDto = createProductRequestDto();

      ResponseEntity<ProductResponseDto> response = productController.createProduct(null, productRequestDto);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
      assertThat(response.getBody()).isNull();
    }

    @Test
    void testCreateProductWithValidRequestAndAuthenticatedUser() {
      // given
      OAuth2AuthenticationToken mockToken = mock(OAuth2AuthenticationToken.class);
      OAuth2User mockPrincipal = mock(OAuth2User.class);
      when(mockToken.getPrincipal()).thenReturn(mockPrincipal);
      when(mockPrincipal.getAttributes()).thenReturn(Map.of(EMAIL_ATTRIBUTE, "test@example.com"));
      ProductRequestDto requestDto = new ProductRequestDto("org-123", "Product Title", BigDecimal.valueOf(9.99));
      ProductResponseDto expectedDto = new ProductResponseDto("product-123", "test@example.com", "org-123", "Product Title", BigDecimal.valueOf(9.99));
      when(productService.createProduct(anyString(), eq(requestDto))).thenReturn(Optional.of(expectedDto));

      // when
      ResponseEntity<ProductResponseDto> response = productController.createProduct(mockToken, requestDto);

      // then
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals(expectedDto, response.getBody());
    }

    @Test
    void testCreateProductWithMissingAuthentication() {
      // given
      ProductRequestDto requestDto = new ProductRequestDto("org-123", "Product Title", BigDecimal.valueOf(9.99));

      // when
      ResponseEntity<ProductResponseDto> response = productController.createProduct(null, requestDto);

      // then
      assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
      assertNull(response.getBody());
    }

    @Test
    void testCreateProductWithInvalidRequestBypassingValidation() {
      // given
      ProductRequestDto requestDto = new ProductRequestDto("org-123", "", BigDecimal.valueOf(-1));

      // when
      ResponseEntity<ProductResponseDto> response = productController.createProduct(createOAuth2AuthenticationToken(), requestDto);

      // then
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
      assertNull(response.getBody());
    }

}
