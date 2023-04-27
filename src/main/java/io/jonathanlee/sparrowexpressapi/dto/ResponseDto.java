package io.jonathanlee.sparrowexpressapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class ResponseDto {

  @JsonIgnore
  protected HttpStatus httpStatus;

}
