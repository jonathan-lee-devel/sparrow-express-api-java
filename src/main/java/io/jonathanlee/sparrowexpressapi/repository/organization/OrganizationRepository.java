package io.jonathanlee.sparrowexpressapi.repository.organization;

import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationModel;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRepository extends MongoRepository<OrganizationModel, ObjectId> {

  Optional<OrganizationModel> findById(String id);

}
