package lv.javaguru.currency.converter.validation;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> illegalArgumentHandle(Exception e) {
    ErrorResponse error = new ErrorResponse();
    error.setHttpStatus(HttpStatus.BAD_REQUEST);
    error.setMessage(e.getLocalizedMessage());
    error.setTimeStamp(LocalDateTime.now());
    return new ResponseEntity<>(error, error.httpStatus);
  }

  @ExceptionHandler(ExchangesDownException.class)
  public ResponseEntity<ErrorResponse> serviceIsUnavailable(ExchangesDownException e) {
    ErrorResponse error = new ErrorResponse();
    error.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
    error.setMessage(e.getMessage());
    error.setTimeStamp(LocalDateTime.now());
    return new ResponseEntity<>(error, error.httpStatus);
  }

  @ExceptionHandler(RateNotUpToDateException.class)
  public ResponseEntity<ErrorResponse> rateNotUpToDate(RateNotUpToDateException e) {
    ErrorResponse error = new ErrorResponse();
    error.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
    error.setMessage(e.getMessage() + ". It seems exchange rates api not working");
    error.setTimeStamp(LocalDateTime.now());
    return new ResponseEntity<>(error, error.httpStatus);
  }

  @Getter
  @Setter
  private class ErrorResponse {

    private LocalDateTime timeStamp = LocalDateTime.now();
    private HttpStatus httpStatus;
    private String message;
  }
}
