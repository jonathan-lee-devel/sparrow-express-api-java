package io.jonathanlee.sparrowexpressapi.exception.handler;

import io.jonathanlee.sparrowexpressapi.exception.BadRequestException;
import io.jonathanlee.sparrowexpressapi.validation.dto.ErrorDto;
import io.jonathanlee.sparrowexpressapi.validation.dto.ValidationErrorDto;
import io.jonathanlee.sparrowexpressapi.validation.dto.ValidationErrorsContainerDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<ValidationErrorDto> errors = ex.getBindingResult()
        .getFieldErrors()
        .parallelStream()
        .map(fieldError -> new ValidationErrorDto(
            fieldError.getField(),
            fieldError.getDefaultMessage()
        ))
        .toList();

    ValidationErrorsContainerDto validationErrorsContainerDto = new ValidationErrorsContainerDto(errors);

    return ResponseEntity.status(status).body(validationErrorsContainerDto);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<Object> handleBadRequest(
      RuntimeException ex, WebRequest request) {
    BadRequestException badRequestException = (BadRequestException) ex;
    ValidationErrorsContainerDto validationErrorsContainerDto = null;
    if (badRequestException.getField() != null) {
      validationErrorsContainerDto = new ValidationErrorsContainerDto(
          List.of(new ValidationErrorDto(badRequestException.getField(), badRequestException.getMessage()))
      );
    }
    return handleExceptionInternal(ex,
        (validationErrorsContainerDto == null) ?
        new ErrorDto(badRequestException.getMessage()) : validationErrorsContainerDto,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

}
