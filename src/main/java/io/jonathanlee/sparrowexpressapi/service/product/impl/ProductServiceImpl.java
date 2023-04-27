package io.jonathanlee.sparrowexpressapi.service.product.impl;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import io.jonathanlee.sparrowexpressapi.service.random.RandomService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  private final RandomService randomService;

  @Override
  public Optional<ProductResponseDto> getProductById(String productId) {
    Optional<ProductModel> productModelOptional = this.productRepository.findById(productId);
    if (productModelOptional.isEmpty()) {
      return Optional.empty();
    }
    ProductResponseDto productResponseDto = this.productMapper.productModelToProductResponseDto(productModelOptional.get());
    productResponseDto.setHttpStatus(HttpStatus.OK);
    return Optional.of(productResponseDto);
  }

  @Override
  public Optional<ProductResponseDto> createProduct(String requestingUserEmail, ProductRequestDto productRequestDto) {
    ProductModel productModel = this.productMapper.productRequestDtoToProductModel(productRequestDto);
    productModel.setId(this.randomService.generateNewId());
    productModel.setCreatorEmail(requestingUserEmail);
    productModel.setObjectId(ObjectId.get());
    ProductResponseDto productResponseDto = this.productMapper.productModelToProductResponseDto(this.productRepository.save(productModel));
    productResponseDto.setHttpStatus(HttpStatus.CREATED);
    return Optional.of(productResponseDto);
  }

  @Override
  public Optional<ProductResponseDto> updateProduct(String requestingUserEmail,
      String productId,
      ProductRequestDto productRequestDto) {
    Optional<ProductModel> productModelOptional = this.productRepository.findById(productId);
    if (productModelOptional.isEmpty()) {
      return Optional.empty();
    }
    ProductModel productModel = productModelOptional.get();
    productModel.setTitle(productRequestDto.getTitle());
    productModel.setPrice(productRequestDto.getPrice());
    ProductResponseDto responseDto = this.productMapper.productModelToProductResponseDto(this.productRepository.save(productModel));
    responseDto.setHttpStatus(HttpStatus.NO_CONTENT);
    return Optional.of(responseDto);
  }

}
