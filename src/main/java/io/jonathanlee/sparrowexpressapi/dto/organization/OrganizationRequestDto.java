package io.jonathanlee.sparrowexpressapi.dto.organization;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDto {

  private String name;

  private List<String> administratorEmails;

  private List<String> memberEmails;

}
