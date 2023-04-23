package io.jonathanlee.sparrowexpressapi.repository.product;

import io.jonathanlee.sparrowexpressapi.model.product.ProductModel;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductModel, ObjectId> {

  Optional<ProductModel> findById(String id);

}
