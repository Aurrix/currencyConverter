package lv.javaguru.currencyConverter.entities;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class Rate {
    private Currency currency;
    private BigDecimal rate;
}
