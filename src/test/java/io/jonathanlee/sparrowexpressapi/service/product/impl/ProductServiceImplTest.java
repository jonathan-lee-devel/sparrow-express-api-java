package io.jonathanlee.sparrowexpressapi.service.product.impl;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class ProductServiceImplTest {

  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    this.productService = new ProductServiceImpl(productRepository, productMapper);
  }

  @Test
  void testGetProductById() {
    // Setup mock repository to return a ProductModel with ID "test-id"
    ProductModel productModel = new ProductModel();
    productModel.setId("test-id");
    Mockito.when(productRepository.findById("test-id")).thenReturn(Optional.of(productModel));

    // Setup mock mapper to return a ProductResponseDto with ID "test-id"
    ProductResponseDto productResponseDto = new ProductResponseDto();
    productResponseDto.setId("test-id");
    Mockito.when(productMapper.productModelToProductResponseDto(productModel)).thenReturn(productResponseDto);

    // Test the getProductById method
    Optional<ProductResponseDto> optionalProductResponseDto = productService.getProductById("test-id");
    Assertions.assertTrue(optionalProductResponseDto.isPresent());
    ProductResponseDto actualProductResponseDto = optionalProductResponseDto.get();
    Assertions.assertEquals("test-id", actualProductResponseDto.getId());
  }

  @Test
  void testCreateProduct() {
    // Setup mock mapper to return a ProductModel with a new ObjectId
    ProductModel productModel = new ProductModel();
    productModel.setObjectId(ObjectId.get());
    Mockito.when(productMapper.productRequestDtoToProductModel(Mockito.any())).thenReturn(productModel);

    // Setup mock repository to return the same ProductModel as was passed in
    Mockito.when(productRepository.save(productModel)).thenReturn(productModel);

    // Setup mock mapper to return a ProductResponseDto with the same ObjectId as the input ProductModel
    ProductResponseDto productResponseDto = new ProductResponseDto();
    productResponseDto.setId(productModel.getObjectId().toHexString());
    Mockito.when(productMapper.productModelToProductResponseDto(productModel)).thenReturn(productResponseDto);

    // Test the createProduct method
    ProductRequestDto productRequestDto = new ProductRequestDto();
    productRequestDto.setTitle("test-title");
    productRequestDto.setPrice(new BigDecimal("10.99"));
    Optional<ProductResponseDto> optionalProductResponseDto = productService.createProduct("test-email", productRequestDto);
    Assertions.assertTrue(optionalProductResponseDto.isPresent());
  }

}
