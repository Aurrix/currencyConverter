package lv.javaguru.currencyConverter.controllers;

import java.math.BigDecimal;
import lv.javaguru.currencyConverter.CurrencyConverterApp;
import lv.javaguru.currencyConverter.entities.ConversionRequest;
import lv.javaguru.currencyConverter.entities.ConversionResponse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyConverterApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrencyConverterControllerTest {

  @Autowired
  TestRestTemplate restTemplate;

  @LocalServerPort
  int port;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  public String rootUrl (){ return "http://localhost:"+port;}

  @Test
  public void convertAmountIsToTrueTest() {
    ConversionRequest conversionRequest = new ConversionRequest(
        "EUR", "USD", new BigDecimal(100.00),true
    );
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(rootUrl()+"/convert", conversionRequest,ConversionResponse.class );

    Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    ConversionResponse response = responseEntity.getBody();
    Assert.assertNotNull(response.getAmountCharged());
    Assert.assertNotNull(response.getAmountFrom());
    Assert.assertNotNull(response.getAmountTo());
    Assert.assertNotNull(response.getCommission());
    Assert.assertNotNull(response.getRate());
  }
  @Test
  public void convertAmountIsToFalseTest() {
    ConversionRequest conversionRequest = new ConversionRequest(
        "EUR", "USD", new BigDecimal(100.00),false
    );
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(rootUrl()+"/convert", conversionRequest,ConversionResponse.class );

    Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    ConversionResponse response = responseEntity.getBody();
    Assert.assertNotNull(response.getAmountCharged());
    Assert.assertNotNull(response.getAmountFrom());
    Assert.assertNotNull(response.getAmountTo());
    Assert.assertNotNull(response.getCommission());
    Assert.assertNotNull(response.getRate());
  }
  @Test
  public void convertAmountCurrencyValidationTest() {
    ConversionRequest conversionRequest = new ConversionRequest(
        "EU", "USD", new BigDecimal(100.00),false
    );
    expectedException.expect(MethodArgumentNotValidException.class);
    expectedException.expectMessage("Not a currency or is not supported currency");
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(rootUrl()+"/convert", conversionRequest,ConversionResponse.class );
  }

  @Test
  public void convertUniqueCurrenciesTest() {
    ConversionRequest conversionRequest = new ConversionRequest(
        "USD", "USD", new BigDecimal(100.00),false
    );
    expectedException.expect(MethodArgumentNotValidException.class);
    expectedException.expectMessage("Not a currency or is not supported currency");
    ResponseEntity<ConversionResponse> responseEntity =
        restTemplate.postForEntity(rootUrl()+"/convert", conversionRequest,ConversionResponse.class );
  }

}