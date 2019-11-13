package lv.javaguru.currency.converter.services.exchange.api;

import java.util.Optional;

public interface ExchangeAPIStatusScanner {

  Optional<String> returnWorkingAPI();
}
