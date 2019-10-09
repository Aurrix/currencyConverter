package lv.javaguru.currencyConverter.controllers;

import java.math.BigDecimal;
import java.util.Currency;
import lv.javaguru.currencyConverter.entities.ConversionResponse;
import lv.javaguru.currencyConverter.services.comission.CommissionChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {
  private final CommissionChargeService commissionChargeService;
  public CurrencyConverterController(
      CommissionChargeService commissionChargeService) {
    this.commissionChargeService = commissionChargeService;
  }

  @GetMapping("/to")
  public ResponseEntity<?> convertTo(
      @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
    ConversionResponse conversionResponse =
        commissionChargeService.returnChargedAndConvertedAmount(
            Currency.getInstance(from), Currency.getInstance(to), amount, true);
    return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
  }

  @GetMapping("/from")
  public ResponseEntity<?> convertFrom(
      @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) {
    ConversionResponse conversionResponse =
        commissionChargeService.returnChargedAndConvertedAmount(
            Currency.getInstance(from), Currency.getInstance(to), amount, false);
    return new ResponseEntity<>(conversionResponse, HttpStatus.OK);
  }

}
