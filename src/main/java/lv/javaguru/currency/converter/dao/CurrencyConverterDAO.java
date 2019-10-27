package lv.javaguru.currency.converter.dao;

import java.util.Currency;
import javax.validation.Valid;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import lv.javaguru.currency.converter.services.comission.CommissionChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterDAO {

  private final CommissionChargeService commissionChargeService;

  public CurrencyConverterDAO(CommissionChargeService commissionChargeService) {
    this.commissionChargeService = commissionChargeService;
  }

  public ResponseEntity<ConversionResponse> convert(@Valid ConversionRequest request) {
    if (!request.isConvertTo()) {
      ConversionResponse conversionResponse =
          commissionChargeService.returnChargedAndConvertedAmount(
              Currency.getInstance(request.getFrom()),
              Currency.getInstance(request.getTo()),
              request.getAmount(),
              false);
      return new ResponseEntity<>(conversionResponse, HttpStatus.CREATED);
    } else {
      ConversionResponse conversionResponse =
          commissionChargeService.returnChargedAndConvertedAmount(
              Currency.getInstance(request.getFrom()),
              Currency.getInstance(request.getTo()),
              request.getAmount(),
              true);
      return new ResponseEntity<>(conversionResponse, HttpStatus.CREATED);
    }
  }
}
