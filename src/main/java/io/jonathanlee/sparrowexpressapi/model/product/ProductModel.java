package io.jonathanlee.sparrowexpressapi.model.product;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "products")
public class ProductModel {

  @Id
  private ObjectId objectId;

  private String id;

  private String creatorEmail;

  private String organizationId;

  private String title;

  private BigDecimal price;

}
