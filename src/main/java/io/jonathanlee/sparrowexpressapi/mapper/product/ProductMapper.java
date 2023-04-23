package io.jonathanlee.sparrowexpressapi.mapper.product;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductDto;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(source = "id", target = "id")
  ProductDto productModelToProductDto(ProductModel productModel);

}
