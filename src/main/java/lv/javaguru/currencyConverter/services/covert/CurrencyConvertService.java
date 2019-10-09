package lv.javaguru.currencyConverter.services.covert;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConvertService {
    BigDecimal convertFrom(Currency from, Currency to, BigDecimal convertAmount);
    BigDecimal convertTo(Currency from, Currency to, BigDecimal convertToAmount);
}
