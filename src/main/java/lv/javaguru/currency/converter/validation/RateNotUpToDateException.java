package lv.javaguru.currency.converter.validation;

public class RateNotUpToDateException extends RuntimeException {

  public RateNotUpToDateException(String message) {
    super(message);
  }
}
