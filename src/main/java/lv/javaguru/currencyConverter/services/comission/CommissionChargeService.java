package lv.javaguru.currencyConverter.services.comission;

import java.math.BigDecimal;
import java.util.Currency;
import lv.javaguru.currencyConverter.entities.ConversionResponse;

public interface CommissionChargeService {
ConversionResponse returnChargedAndConvertedAmount(Currency from, Currency to, BigDecimal amount, boolean isTo);
}
