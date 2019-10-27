package lv.javaguru.currency.converter.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rate {

  private Currency currency;
  private BigDecimal rate;
  private LocalDateTime dateTime;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Rate) {
      Rate rate = (Rate) obj;
      return rate.getCurrency().getCurrencyCode().equals(this.getCurrency().getCurrencyCode())
          && rate.getRate().equals(this.getRate());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, rate, dateTime);
  }
}
