package lv.javaguru.currency.converter.services.exchange.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lv.javaguru.currency.converter.entities.Rate;
import lv.javaguru.currency.converter.entities.RatesMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class UniversalExchangeAPIServiceImplTest {

  @Mock
  private BasicExchangeAPIStatusScannerImpl basicExchangeAPIStatusScanner;
  @Mock
  private RestTemplate exchangeAPIRequest;

  private UniversalExchangeAPIServiceImpl universalExchangeAPIService;

  @Before
  public void setUp() {
    universalExchangeAPIService =
        new UniversalExchangeAPIServiceImpl(
            basicExchangeAPIStatusScanner, exchangeAPIRequest, new LinkedHashSet<>());
    when(basicExchangeAPIStatusScanner.returnWorkingAPI())
        .thenReturn(Optional.of(("https://someapi.com")));
    RatesMap newRatesMap = new RatesMap();
    Map<String, Double> newRateMap = new HashMap<>();
    newRateMap.put("MYR", 4.622);
    newRateMap.put("SGD", 1.5177);
    newRateMap.put("CZK", 25.807);
    newRatesMap.setRates(newRateMap);
    when(exchangeAPIRequest.getForEntity(anyString(), eq(RatesMap.class)))
        .thenReturn(new ResponseEntity<>(newRatesMap, HttpStatus.OK));
  }

  @Test
  public void getAllRates() {

    Set<Rate> rates = universalExchangeAPIService.getAllRates(Currency.getInstance("EUR"));

    Rate MYR =
        new Rate(Currency.getInstance("MYR"), BigDecimal.valueOf(4.622), LocalDateTime.now());
    Rate SGD =
        new Rate(Currency.getInstance("SGD"), BigDecimal.valueOf(1.5177), LocalDateTime.now());
    Rate CZK =
        new Rate(Currency.getInstance("CZK"), BigDecimal.valueOf(25.807), LocalDateTime.now());

    assertEquals(MYR, getRateOfCurrency("MYR", rates));
    assertEquals(SGD, getRateOfCurrency("SGD", rates));
    assertEquals(CZK, getRateOfCurrency("CZK", rates));
  }

  private Rate getRateOfCurrency(String currency, Set<Rate> rates) {
    for (Rate rate : rates) {
      if (rate.getCurrency().getCurrencyCode().equals(currency)) {
        return rate;
      }
    }
    return null;
  }
}
