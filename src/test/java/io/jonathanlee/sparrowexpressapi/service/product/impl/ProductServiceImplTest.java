package io.jonathanlee.sparrowexpressapi.service.product.impl;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class ProductServiceImplTest {

  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    productService = new ProductServiceImpl(productRepository, productMapper);
  }

  @Test
  void getProductById_productFound_shouldReturnProductDto() {
    // Arrange
    String id = "123";
    ProductModel productModel = new ProductModel();
    productModel.setId(id);
    Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(productModel));
    ProductDto productDto = new ProductDto();
    productDto.setId(id);
    Mockito.when(productMapper.productModelToProductDto(productModel)).thenReturn(productDto);

    // Act
    Optional<ProductDto> result = productService.getProductById(id);

    // Assert
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(id, result.get().getId());
  }

  @Test
  void getProductById_productNotFound_shouldReturnEmptyOptional() {
    // Arrange
    String id = "123";
    Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

    // Act
    Optional<ProductDto> result = productService.getProductById(id);

    // Assert
    Assertions.assertTrue(result.isEmpty());
  }
}
