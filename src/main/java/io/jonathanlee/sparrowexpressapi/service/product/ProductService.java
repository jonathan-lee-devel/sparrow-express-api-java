package io.jonathanlee.sparrowexpressapi.service.product;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductDto;
import java.util.Optional;

public interface ProductService {

  Optional<ProductDto> getProductById(String id);

}
