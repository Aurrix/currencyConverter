package lv.javaguru.currencyConverter.services.exchangeAPI;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Currency;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniversalExchangeAPIServiceImplTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Mock
  private BasicExchangeAPIStatusScannerImpl basicExchangeAPIStatusScanner;
  @MockBean
  private RestTemplate exchangeAPIRequest;

  private UniversalExchangeAPIServiceImpl universalExchangeAPIService;

  @Before
  public void setUp(){
    universalExchangeAPIService = new UniversalExchangeAPIServiceImpl(
        basicExchangeAPIStatusScanner,
        exchangeAPIRequest);

  }

  @Test
  public void getAllRates() throws URISyntaxException {
    Mockito.when(basicExchangeAPIStatusScanner.returnWorkingAPI()).thenReturn(new URI("https://someapi.com"));
    Mockito.when(exchangeAPIRequest.getForEntity(Mockito.any(),Mockito.any()))
        .thenReturn(
            new ResponseEntity<>(
                "{\n"
                    + "\"base\": \"EUR\",\n"
                    + "\"rates\": {\n"
                    + "\"GBP\": 0.87518,\n"
                    + "\"HKD\": 8.6614,\n"
                    + "\"IDR\": 15601.55,\n"
                    + "\"ILS\": 3.8673,\n"
                    + "\"DKK\": 7.4688,\n"
                    + "\"INR\": 78.4875,\n"
                    + "\"CHF\": 1.1025,\n"
                    + "\"MXN\": 21.3965,\n"
                    + "\"CZK\": 25.807,\n"
                    + "\"SGD\": 1.5177,\n"
                    + "\"THB\": 33.642,\n"
                    + "\"HRK\": 7.428,\n"
                    + "\"MYR\": 4.622,\n"
                    + "\"NOK\": 10.0375,\n"
                    + "\"CNY\": 7.8417,\n"
                    + "\"BGN\": 1.9558,\n"
                    + "\"PHP\": 56.927,\n"
                    + "\"SEK\": 10.8448,\n"
                    + "\"PLN\": 4.3057,\n"
                    + "\"ZAR\": 16.3978,\n"
                    + "\"CAD\": 1.4679,\n"
                    + "\"ISK\": 137.7,\n"
                    + "\"BRL\": 4.5291,\n"
                    + "\"RON\": 4.7573,\n"
                    + "\"NZD\": 1.7419,\n"
                    + "\"TRY\": 6.4713,\n"
                    + "\"JPY\": 119.75,\n"
                    + "\"RUB\": 70.8034,\n"
                    + "\"KRW\": 1308.61,\n"
                    + "\"USD\": 1.1043,\n"
                    + "\"HUF\": 331.71,\n"
                    + "\"AUD\": 1.6246\n"
                    + "},\n"
                    + "\"date\": \"2019-10-11\"\n"
                    + "}",
                HttpStatus.OK));

    Set<Rate> rates = universalExchangeAPIService.getAllRates(Currency.getInstance("EUR"));

    for (Rate rate:rates){logger.info("{} {}",rate.getCurrency().getCurrencyCode(),rate.getRate());}

    Rate MYR = new Rate(Currency.getInstance("MYR"), BigDecimal.valueOf(4.622));
    Rate SGD = new Rate(Currency.getInstance("SGD"), BigDecimal.valueOf(1.5177));
    Rate CZK = new Rate(Currency.getInstance("CZK"), BigDecimal.valueOf(25.807));

    assertTrue(rates.contains(MYR));
    assertTrue(rates.contains(SGD));
    assertTrue(rates.contains(CZK));
  }
}
