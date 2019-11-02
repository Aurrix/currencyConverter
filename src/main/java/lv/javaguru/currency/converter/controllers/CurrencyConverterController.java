package lv.javaguru.currency.converter.controllers;

import javax.validation.Valid;
import lv.javaguru.currency.converter.dao.CurrencyConverterDAO;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {

  private final CurrencyConverterDAO currencyConverterDAO;

  public CurrencyConverterController(CurrencyConverterDAO currencyConverterDAO) {

    this.currencyConverterDAO = currencyConverterDAO;
  }

  @PostMapping
  public ResponseEntity<ConversionResponse> convertAmount(
      @RequestBody @Valid ConversionRequest request) {
    return new ResponseEntity<>(currencyConverterDAO.convert(request), HttpStatus.CREATED);
  }
}
