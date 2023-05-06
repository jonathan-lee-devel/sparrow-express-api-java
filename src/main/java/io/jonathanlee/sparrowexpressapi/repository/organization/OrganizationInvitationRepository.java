package io.jonathanlee.sparrowexpressapi.repository.organization;

import io.jonathanlee.sparrowexpressapi.model.organization.OrganizationInvitationModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationInvitationRepository extends MongoRepository<OrganizationInvitationModel, ObjectId> {

}
