package lv.javaguru.currency.converter.validation;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralErrors(Exception e) {
    ErrorResponse error = new ErrorResponse();
    error.setHttpStatus(HttpStatus.BAD_REQUEST);
    error.setMessage(e.getMessage());
    error.setTimeStamp(LocalDateTime.now());
    return new ResponseEntity<>(error, error.httpStatus);
  }

  @Getter
  @Setter
  private static class ErrorResponse {
    private LocalDateTime timeStamp = LocalDateTime.now();
    private HttpStatus httpStatus;
    private String message;
  }
}
