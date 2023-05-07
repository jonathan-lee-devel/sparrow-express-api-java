package io.jonathanlee.sparrowexpressapi.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

  private final String field;

  private final String message;

  public BadRequestException(String field, String message) {
    super(message);
    this.field = field;
    this.message = message;
  }

}
