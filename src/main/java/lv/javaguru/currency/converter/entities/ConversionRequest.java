package lv.javaguru.currency.converter.entities;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.javaguru.currency.converter.validation.annotations.IsCurrency;
import lv.javaguru.currency.converter.validation.annotations.UniqueCurrencies;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueCurrencies(first = "from", second = "to")
public class ConversionRequest {

  @NotNull
  @IsCurrency
  public String from;
  @NotNull
  @IsCurrency
  public String to;

  @Range(
      min = 1,
      max = 1000000000,
      message = "Amount not within allowed range (min 1, max 1,000,000,000.00). ")
  private BigDecimal amount;

  private boolean convertTo;
}
