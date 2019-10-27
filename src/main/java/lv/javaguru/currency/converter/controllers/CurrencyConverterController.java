package lv.javaguru.currency.converter.controllers;

import lv.javaguru.currency.converter.dao.CurrencyConverterDAO;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.validation.ExchangesDownException;
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
  public ResponseEntity<?> convertAmount(@RequestBody ConversionRequest request)
      throws ExchangesDownException {
    return currencyConverterDAO.convert(request);
  }
}
