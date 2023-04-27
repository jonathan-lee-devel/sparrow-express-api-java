package io.jonathanlee.sparrowexpressapi.dto.organization;

import io.jonathanlee.sparrowexpressapi.dto.ResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrganizationResponseDto extends ResponseDto {

  private String id;

  private String name;

  private List<String> administratorEmails;

  private List<String> memberEmails;

}
