package io.jonathanlee.sparrowexpressapi.service.product.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

  private ProductRepository productRepository;
  private ProductMapper productMapper;
  private ProductService productService;

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepository.class);
    productMapper = mock(ProductMapper.class);
    productService = new ProductServiceImpl(productRepository, productMapper);
  }

  @Test
  void getProductById_returnsEmptyOptional_whenProductNotFound() {
    // Arrange
    String productId = "non-existent-id";
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // Act
    Optional<ProductResponseDto> result = productService.getProductById(productId);

    // Assert
    assertTrue(result.isEmpty());
    verify(productRepository).findById(productId);
    verifyNoInteractions(productMapper);
  }

  @Test
  void getProductById_returnsProductResponseDto_whenProductFound() {
    // Arrange
    String productId = "existing-id";
    ProductModel productModel = new ProductModel();
    ProductResponseDto expectedResponseDto = new ProductResponseDto();
    when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));
    when(productMapper.productModelToProductResponseDto(productModel)).thenReturn(expectedResponseDto);

    // Act
    Optional<ProductResponseDto> result = productService.getProductById(productId);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(expectedResponseDto, result.get());
    verify(productRepository).findById(productId);
    verify(productMapper).productModelToProductResponseDto(productModel);
  }

  @Test
  void createProduct_returnsEmptyOptional_whenProductRequestDtoIsNull() {
    // Act
    Optional<ProductResponseDto> result = productService.createProduct(null);

    // Assert
    assertTrue(result.isEmpty());
    verifyNoInteractions(productRepository, productMapper);
  }

  @Test
  void createProduct_returnsProductResponseDto_whenProductRequestDtoIsValid() {
    // Arrange
    ProductRequestDto productRequestDto = new ProductRequestDto();
    productRequestDto.setCreatorEmail("test@test.com");
    productRequestDto.setOrganizationId("5678");
    productRequestDto.setTitle("Test product");
    productRequestDto.setPrice(BigDecimal.TEN);
    ProductModel productModel = new ProductModel();
    ProductResponseDto expectedResponseDto = new ProductResponseDto();
    when(productMapper.productRequestDtoToProductModel(productRequestDto)).thenReturn(productModel);
    when(productRepository.save(productModel)).thenReturn(productModel);
    when(productMapper.productModelToProductResponseDto(productModel)).thenReturn(expectedResponseDto);

    // Act
    Optional<ProductResponseDto> result = productService.createProduct(productRequestDto);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(expectedResponseDto, result.get());
    assertNotNull(productModel.getObjectId());
    verify(productMapper).productRequestDtoToProductModel(productRequestDto);
    verify(productRepository).save(productModel);
    verify(productMapper).productModelToProductResponseDto(productModel);
  }

}
