package io.jonathanlee.sparrowexpressapi.repository.organization;

import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationMembershipRequestModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationMembershipRequestRepository extends MongoRepository<OrganizationMembershipRequestModel, ObjectId> {

}
