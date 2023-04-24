package io.jonathanlee.sparrowexpressapi.controller.product;

import static io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils.getEmailAttributeFromOAuth2AuthenticationToken;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import io.jonathanlee.sparrowexpressapi.util.oauth2.OAuth2ClientUtils;
import io.jonathanlee.sparrowexpressapi.util.profile.ActiveProfileService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ActiveProfileService activeProfileService;

  private final ProductService productService;

  @GetMapping(
      value = "/{productId}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String productId) {
    Optional<ProductResponseDto> productResponseDtoOptional = this.productService.getProductById(productId);
    return productResponseDtoOptional.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ProductResponseDto> createProduct(
      OAuth2AuthenticationToken oAuth2AuthenticationToken,
      @Valid @RequestBody ProductRequestDto productRequestDto
  ) {
    boolean isNoAuthentication = OAuth2ClientUtils.isNoAuthentication(oAuth2AuthenticationToken);
    if (isNoAuthentication && !this.activeProfileService.isLocalActiveProfile()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    Optional<ProductResponseDto> productResponseDtoOptional = this.productService.createProduct((isNoAuthentication) ? OAuth2ClientUtils.DEFAULT_LOCAL_EMAIL : getEmailAttributeFromOAuth2AuthenticationToken(oAuth2AuthenticationToken), productRequestDto);
    return productResponseDtoOptional.map(ResponseEntity.status(HttpStatus.CREATED)::body)
        .orElseGet(() -> ResponseEntity.internalServerError().build());
  }

}
