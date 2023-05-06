package io.jonathanlee.sparrowexpressapi.model.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "organization_invitations")
public class OrganizationInvitationModel {

  @Id
  private ObjectId objectId;

  private String id;

  private String organizationId;

}
