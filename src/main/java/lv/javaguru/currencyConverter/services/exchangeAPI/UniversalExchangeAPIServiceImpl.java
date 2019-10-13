package lv.javaguru.currencyConverter.services.exchangeAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.Currency;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UniversalExchangeAPIServiceImpl implements ExternalExchangeAPIService {

  private final ExchangeAPIStatusScanner basicExchangeAPIStatusScanner;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final RestTemplate exchangeAPIRequest;

  public UniversalExchangeAPIServiceImpl(
      ExchangeAPIStatusScanner basicExchangeAPIStatusScanner,
      RestTemplate exchangeAPIRequest) {
    this.basicExchangeAPIStatusScanner = basicExchangeAPIStatusScanner;
    this.exchangeAPIRequest = exchangeAPIRequest;
  }

  @Override
  public Set<Rate> getAllRates(Currency base) {
    Optional<URI> exchangeUrlOptional = Optional
        .ofNullable(basicExchangeAPIStatusScanner.returnWorkingAPI());
    if(exchangeUrlOptional.isPresent()){
      ResponseEntity<String> responseRates = exchangeAPIRequest.getForEntity(
          exchangeUrlOptional.get()+"?base="+base.getCurrencyCode(),String.class);
      ObjectMapper mapper = new ObjectMapper();
      try{
        logger.info(responseRates.toString());
        JsonNode root = mapper.readTree(responseRates.getBody());
        JsonNode ratesNode = root.path("rates");
        Set<Rate> rates = new LinkedHashSet<>();
        ratesNode.fields().forEachRemaining(element ->{
        Currency cur = Currency.getInstance(element.getKey());
        BigDecimal rate = element.getValue().decimalValue();
        rate.setScale(4, RoundingMode.HALF_UP);
        rates.add(new Rate(cur,rate));
        });
        return rates;
      }
      catch (IOException e) {
        logger.error("Could not convert response from exchangeAPIService", e);
      }
    }
    throw new RuntimeException("There are no working exchangeAPIs!");
  }
}
