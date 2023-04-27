package io.jonathanlee.sparrowexpressapi.model.organization;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "organizations")
public class OrganizationModel {

  @Id
  private ObjectId objectId;

  private String id;

  private String name;

  private List<String> administratorEmails;

  private List<String> memberEmails;

}
