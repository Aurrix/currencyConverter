package lv.javaguru.currency.converter.services.comission;

import java.math.BigDecimal;
import java.util.Currency;
import lv.javaguru.currency.converter.entities.ConversionResponse;

public interface CommissionChargeService {

  ConversionResponse getResponse(
      Currency from, Currency to, BigDecimal amount, boolean isTo);
}
