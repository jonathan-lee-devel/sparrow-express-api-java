package io.jonathanlee.sparrowexpressapi.controller.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductControllerTest {

  @Mock
  private ProductService productService;

  private ProductController productController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    productController = new ProductController(productService);
  }

  @Test
  @DisplayName("Get product by id - success")
  void getProductByIdSuccessTest() {
    String productId = "p001";
    ProductResponseDto expectedProduct = new ProductResponseDto(productId, "test@example.com", "o001", "Test Product", BigDecimal.TEN);

    when(productService.getProductById(productId)).thenReturn(Optional.of(expectedProduct));

    ResponseEntity<ProductResponseDto> responseEntity = productController.getProductById(productId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedProduct, responseEntity.getBody());
  }

  @Test
  @DisplayName("Get product by id - not found")
  void getProductByIdNotFoundTest() {
    String productId = "p001";

    when(productService.getProductById(productId)).thenReturn(Optional.empty());

    ResponseEntity<ProductResponseDto> responseEntity = productController.getProductById(productId);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  @DisplayName("Create product - success")
  void createProductSuccessTest() {
    ProductRequestDto productRequest = new ProductRequestDto("test@example.com", "o001", "Test Product", BigDecimal.TEN);
    ProductResponseDto expectedProduct = new ProductResponseDto("p001", "test@example.com", "o001", "Test Product", BigDecimal.TEN);

    when(productService.createProduct(productRequest)).thenReturn(Optional.of(expectedProduct));

    ResponseEntity<ProductResponseDto> responseEntity = productController.createProduct(productRequest);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(expectedProduct, responseEntity.getBody());
  }

  @Test
  @DisplayName("Create product - internal server error")
  void createProductInternalServerErrorTest() {
    ProductRequestDto productRequest = new ProductRequestDto("test@example.com", "o001", "Test Product", BigDecimal.TEN);

    when(productService.createProduct(productRequest)).thenReturn(Optional.empty());

    ResponseEntity<ProductResponseDto> responseEntity = productController.createProduct(productRequest);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }
}
