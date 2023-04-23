package io.jonathanlee.sparrowexpressapi.mapper.product;

import io.jonathanlee.sparrowexpressapi.dto.product.ProductRequestDto;
import io.jonathanlee.sparrowexpressapi.dto.product.ProductResponseDto;
import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(source = "id", target = "id")
  ProductResponseDto productModelToProductResponseDto(ProductModel productModel);

  @Mapping(source = "title", target = "title")
  ProductModel productRequestDtoToProductModel(ProductRequestDto productRequestDto);

}
