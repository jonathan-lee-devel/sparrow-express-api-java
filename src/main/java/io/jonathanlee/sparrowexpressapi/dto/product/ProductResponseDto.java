package io.jonathanlee.sparrowexpressapi.dto.product;

import io.jonathanlee.sparrowexpressapi.constraint.CommonConstraints;
import io.jonathanlee.sparrowexpressapi.constraint.product.ProductConstraints;
import io.jonathanlee.sparrowexpressapi.dto.ResponseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductResponseDto extends ResponseDto {

  @Size(
      min = CommonConstraints.ID_LENGTH,
      max = CommonConstraints.ID_LENGTH
  )
  @NotNull
  private String id;

  @Email
  @NotNull
  private String creatorEmail;

  @Size(
      min = CommonConstraints.ID_LENGTH,
      max = CommonConstraints.ID_LENGTH
  )
  @NotNull
  private String organizationId;

  @Size(
      min = ProductConstraints.MIN_TITLE_LENGTH,
      max = ProductConstraints.MAX_TITLE_LENGTH
  )
  @NotNull
  private String title;

  @NotNull
  private BigDecimal price;

}
