package io.jonathanlee.sparrowexpressapi.service.product;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import java.util.Optional;

public interface ProductService {

  Optional<ProductResponseDto> getProductById(String productId);

  Optional<ProductResponseDto> createProduct(ProductRequestDto productRequestDto);

}