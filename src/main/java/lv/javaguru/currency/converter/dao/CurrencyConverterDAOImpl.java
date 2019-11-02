package lv.javaguru.currency.converter.dao;

import java.util.Currency;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import lv.javaguru.currency.converter.services.comission.CommissionChargeService;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterDAOImpl implements CurrencyConverterDAO {

  private final CommissionChargeService commissionChargeService;

  public CurrencyConverterDAOImpl(CommissionChargeService commissionChargeService) {
    this.commissionChargeService = commissionChargeService;
  }

  @Override
  public ConversionResponse convert(ConversionRequest request) {
    if (!request.isConvertTo()) {
      return commissionChargeService.getResponse(
          Currency.getInstance(request.getFrom()),
          Currency.getInstance(request.getTo()),
          request.getAmount(),
          false);
    } else {
      return commissionChargeService.getResponse(
          Currency.getInstance(request.getFrom()),
          Currency.getInstance(request.getTo()),
          request.getAmount(),
          true);
    }
  }
}
