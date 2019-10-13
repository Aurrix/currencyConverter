package lv.javaguru.currencyConverter.services.exchangeAPI;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import lv.javaguru.currencyConverter.config.exchange.ExchangeAPIProperties;
import lv.javaguru.currencyConverter.config.exchange.ExchangeAPIsConfig;
import lv.javaguru.currencyConverter.entities.ApplicationState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasicExchangeAPIStatusScannerImplTest {
  @Mock private ExchangeAPIsConfig exchangeAPIsConfig;
  @Mock private ApplicationState applicationState;
  private ExchangeAPIStatusScanner exchangeAPIStatusScanner;

  @Before
  public void setUp() {
    exchangeAPIStatusScanner = new BasicExchangeAPIStatusScannerImpl(exchangeAPIsConfig, applicationState);
    ExchangeAPIProperties workingProperties = new ExchangeAPIProperties();
    workingProperties.setListRatesUri("https://google.com");
    ExchangeAPIProperties fakeProperties = new ExchangeAPIProperties();
    fakeProperties.setListRatesUri("https://somefakeurl.com");
    Mockito.when(exchangeAPIsConfig.getExchanges())
        .thenReturn(Arrays.asList(fakeProperties, workingProperties));
  }

  @Test
  public void returnWorkingApiTest() throws URISyntaxException {
    Assert.assertEquals(new URI("https://google.com"),
        exchangeAPIStatusScanner.returnWorkingAPI()
        );
  }
}
