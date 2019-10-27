package lv.javaguru.currency.converter.services.exchange.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lv.javaguru.currency.converter.entities.Rate;
import lv.javaguru.currency.converter.entities.Rates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    Mockito.when(basicExchangeAPIStatusScanner.returnWorkingAPI())
        .thenReturn(Optional.of(("https://someapi.com")));
    Rates newRates = new Rates();
    Map<String, Double> newRateMap = new HashMap<>();
    newRateMap.put("MYR", 4.622);
    newRateMap.put("SGD", 1.5177);
    newRateMap.put("CZK", 25.807);
    newRates.setRates(newRateMap);
    Mockito.when(exchangeAPIRequest.getForEntity(Mockito.anyString(), Mockito.eq(Rates.class)))
        .thenReturn(new ResponseEntity<>(newRates, HttpStatus.OK));
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

    Assert.assertEquals(MYR, getRateOfCurrency("MYR", rates));
    Assert.assertEquals(SGD, getRateOfCurrency("SGD", rates));
    Assert.assertEquals(CZK, getRateOfCurrency("CZK", rates));
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
