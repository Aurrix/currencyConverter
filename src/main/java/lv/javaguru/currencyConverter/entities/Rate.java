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

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Rate) {
      Rate rate = (Rate) obj;
      return rate.getCurrency().getCurrencyCode().equals(this.getCurrency().getCurrencyCode())
          && rate.getRate().equals(this.getRate());
    }
    return false;
  }
}
