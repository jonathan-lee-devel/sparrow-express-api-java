package io.jonathanlee.sparrowexpressapi.service.product.impl;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductDto;
import io.jonathanlee.sparrowexpressapi.mapper.product.ProductMapper;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import io.jonathanlee.sparrowexpressapi.repository.product.ProductRepository;
import io.jonathanlee.sparrowexpressapi.service.product.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  @Override
  public Optional<ProductDto> getProductById(String id) {
    Optional<ProductModel> productModelOptional = this.productRepository.findById(id);
    return productModelOptional.map(this.productMapper::productModelToProductDto);
  }

}
