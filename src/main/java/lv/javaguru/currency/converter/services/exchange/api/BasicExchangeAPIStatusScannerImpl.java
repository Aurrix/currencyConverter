package lv.javaguru.currency.converter.services.exchange.api;

import java.util.Optional;
import lv.javaguru.currency.converter.config.exchange.ExchangeAPIsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BasicExchangeAPIStatusScannerImpl implements ExchangeAPIStatusScanner {

  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
  private final ExchangeAPIsConfig exchangeAPIsConfig;
  private RestTemplate apiConnection;

  public BasicExchangeAPIStatusScannerImpl(
      ExchangeAPIsConfig exchangeAPIsConfig, RestTemplate apiConnection) {
    this.exchangeAPIsConfig = exchangeAPIsConfig;
    this.apiConnection = apiConnection;
  }

  @Override
  public Optional<String> returnWorkingAPI() {
    for (String url : exchangeAPIsConfig.getExchanges()) {
      ResponseEntity response = apiConnection.getForEntity(url, String.class);
      if (response.getStatusCode().equals(HttpStatus.OK)) {
        return Optional.of(url);
      } else {
        logger.warn("Service API {} not working!", url);
      }
    }
    return Optional.empty();
  }
}
