package lv.javaguru.currency.converter.services.exchange.api;

import java.util.Arrays;
import java.util.Optional;
import lv.javaguru.currency.converter.config.exchange.ExchangeAPIsConfig;
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
public class BasicExchangeAPIStatusScannerImplTest {
  @Mock private ExchangeAPIsConfig exchangeAPIsConfig;
  @Mock
  private RestTemplate restTemplate;
  private ExchangeAPIStatusScanner exchangeAPIStatusScanner;

  @Before
  public void setUp() {
    exchangeAPIStatusScanner =
        new BasicExchangeAPIStatusScannerImpl(exchangeAPIsConfig, restTemplate);
    String workingProperties = "https://google.com";
    String fakeProperties = "https://somefakeurl.com";
    Mockito.when(exchangeAPIsConfig.getExchanges())
        .thenReturn(Arrays.asList(fakeProperties, workingProperties));
    Mockito.when(restTemplate.getForEntity("https://google.com", String.class))
        .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    Mockito.when(restTemplate.getForEntity("https://somefakeurl.com", String.class))
        .thenReturn(new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
  }

  @Test
  public void returnWorkingApiTest() {
    Assert.assertEquals(
        Optional.of("https://google.com"), exchangeAPIStatusScanner.returnWorkingAPI());
  }
}
