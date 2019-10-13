package lv.javaguru.currencyConverter.controllers;

import java.math.BigDecimal;
import java.util.Currency;
import javax.validation.Valid;
import lv.javaguru.currencyConverter.entities.ApplicationState;
import lv.javaguru.currencyConverter.entities.ConversionRequest;
import lv.javaguru.currencyConverter.entities.ConversionResponse;
import lv.javaguru.currencyConverter.services.comission.CommissionChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {
  private final CommissionChargeService commissionChargeService;
  private ApplicationState applicationState;

  public CurrencyConverterController(
      CommissionChargeService commissionChargeService,
      ApplicationState applicationState) {
    this.commissionChargeService = commissionChargeService;
    this.applicationState = applicationState;
  }

  @PostMapping
  public ResponseEntity<?> convertAmount(@RequestBody @Valid ConversionRequest request) {
    if (applicationState.isConnecting()) {
      return new ResponseEntity<>(
          "Service is unavailable. Try again later. ", HttpStatus.SERVICE_UNAVAILABLE);
    }
    if (!request.isConvertTo()) {
      ConversionResponse conversionResponse =
          commissionChargeService.returnChargedAndConvertedAmount(
              Currency.getInstance(request.getFrom()),
              Currency.getInstance(request.getTo()),
              request.getAmount(),
              false);
      return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
    } else {
      ConversionResponse conversionResponse =
          commissionChargeService.returnChargedAndConvertedAmount(
              Currency.getInstance(request.getFrom()),
              Currency.getInstance(request.getTo()),
              request.getAmount(),
              true);
      return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
    }
  }

  @GetMapping("/example")
  public ResponseEntity<ConversionRequest> getPostExample() {
    ConversionRequest request = new ConversionRequest("EUR", "USD", new BigDecimal(200.00), false);
    return new ResponseEntity<>(request, HttpStatus.OK);
  }
}
