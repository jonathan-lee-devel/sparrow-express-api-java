package io.jonathanlee.sparrowexpressapi.service.product.impl;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  @Override
  public Optional<ProductResponseDto> getProductById(String productId) {
    Optional<ProductModel> productModelOptional = this.productRepository.findById(productId);
    return productModelOptional.map(this.productMapper::productModelToProductResponseDto);
  }

  @Override
  public Optional<ProductResponseDto> createProduct(ProductRequestDto productRequestDto) {
    if (productRequestDto == null) {
      return Optional.empty();
    }
    ProductModel productModel = this.productMapper.productRequestDtoToProductModel(productRequestDto);
    productModel.setObjectId(ObjectId.get());
    return Optional.of(this.productMapper.productModelToProductResponseDto(this.productRepository.save(productModel)));
  }

}
