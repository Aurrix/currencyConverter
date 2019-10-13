package lv.javaguru.currencyConverter.services.exchangeAPI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import lv.javaguru.currencyConverter.config.exchange.ExchangeAPIProperties;
import lv.javaguru.currencyConverter.config.exchange.ExchangeAPIsConfig;
import lv.javaguru.currencyConverter.entities.ApplicationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BasicExchangeAPIStatusScannerImpl implements ExchangeAPIStatusScanner {
  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
  private final ExchangeAPIsConfig exchangeAPIsConfig;
  private final ApplicationState applicationState;
  public BasicExchangeAPIStatusScannerImpl(ExchangeAPIsConfig exchangeAPIsConfig,
      ApplicationState applicationState) {
    this.exchangeAPIsConfig = exchangeAPIsConfig;

    this.applicationState = applicationState;
  }

  @Override
  public URI returnWorkingAPI() {
    applicationState.setConnecting(true);
    for (ExchangeAPIProperties apiProperties : exchangeAPIsConfig.getExchanges()) {
      try {
        HttpsURLConnection apiConnection =
            (HttpsURLConnection) new URL(apiProperties.listRatesUri).openConnection();
        apiConnection.connect();
        applicationState.setConnecting(false);
        apiConnection.disconnect();
        return new URI(apiProperties.listRatesUri);
      } catch (IOException | URISyntaxException e) {
        logger.warn("Service API {} not working!", apiProperties.listRatesUri, e);
      }
    }
    return null;
  }
}
