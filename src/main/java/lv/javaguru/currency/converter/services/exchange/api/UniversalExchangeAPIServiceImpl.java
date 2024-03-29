package lv.javaguru.currency.converter.services.exchange.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lv.javaguru.currency.converter.entities.Rate;
import lv.javaguru.currency.converter.entities.RatesMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UniversalExchangeAPIServiceImpl implements ExternalExchangeAPIService {

  private final ExchangeAPIStatusScanner basicExchangeAPIStatusScanner;
  private final RestTemplate exchangeAPIRequest;
  private final Set<Rate> rates;

  public UniversalExchangeAPIServiceImpl(
      ExchangeAPIStatusScanner basicExchangeAPIStatusScanner,
      RestTemplate exchangeAPIRequest,
      Set<Rate> rates) {
    this.basicExchangeAPIStatusScanner = basicExchangeAPIStatusScanner;
    this.exchangeAPIRequest = exchangeAPIRequest;
    this.rates = rates;
  }

  @Override
  public Set<Rate> getAllRates(Currency base) {
    Optional<String> exchangeUrlOptional = basicExchangeAPIStatusScanner.returnWorkingAPI();
    exchangeUrlOptional.ifPresent(url -> {
      ResponseEntity<RatesMap> responseRates =
          exchangeAPIRequest.getForEntity(
              UriComponentsBuilder.fromHttpUrl(url)
                  .queryParam("base", base.getCurrencyCode())
                  .build()
                  .toUriString(),
              RatesMap.class);
      Map<String, Double> newRates = Objects.requireNonNull(responseRates.getBody()).getRates();
      this.rates.clear();
      newRates.forEach(
          (key, value) ->
              this.rates.add(
                  new Rate(
                      Currency.getInstance(key), BigDecimal.valueOf(value), LocalDateTime.now())));
    });
    return this.rates;
  }
}
