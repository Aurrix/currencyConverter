package lv.javaguru.currency.converter.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import lv.javaguru.currency.converter.CurrencyConverterApp;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyConverterApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrencyConverterControllerIntegrationTest {

  @Autowired
  TestRestTemplate restTemplate;

  @LocalServerPort
  int port;

  public String rootUrl() {
    return "http://localhost:" + port;
  }

  @Test
  public void convertAmountIsToTrueTest() {
    ConversionRequest conversionRequest =
        new ConversionRequest("EUR", "USD", new BigDecimal(100.00), true);
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(
            rootUrl() + "/convert", conversionRequest, ConversionResponse.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    ConversionResponse response = responseEntity.getBody();
    assertNotNull(response.getAmountCharged());
    assertNotNull(response.getAmountFrom());
    assertNotNull(response.getAmountTo());
    assertNotNull(response.getCommission());
    assertNotNull(response.getRate());
  }

  @Test
  public void convertAmountIsToFalseTest() {
    ConversionRequest conversionRequest =
        new ConversionRequest("EUR", "USD", new BigDecimal(100.00), false);
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(
            rootUrl() + "/convert", conversionRequest, ConversionResponse.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    ConversionResponse response = responseEntity.getBody();
    assertNotNull(response.getAmountCharged());
    assertNotNull(response.getAmountFrom());
    assertNotNull(response.getAmountTo());
    assertNotNull(response.getCommission());
    assertNotNull(response.getRate());
  }
}
