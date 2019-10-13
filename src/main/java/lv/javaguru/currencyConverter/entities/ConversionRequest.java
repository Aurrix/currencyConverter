package lv.javaguru.currencyConverter.entities;


import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lv.javaguru.currencyConverter.validation.annotations.IsCurrency;
import lv.javaguru.currencyConverter.validation.annotations.UniqueCurrencies;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@UniqueCurrencies(first = "from",second = "to")
public class ConversionRequest {
  @NotNull
  @IsCurrency
  public String from;
  @NotNull
  @IsCurrency
  public String to;
  @Range(min = 1,max = 1000000000, message = "Amount not within allowed range (min 1, max 1,000,000,000.00). ")
  private BigDecimal amount;
  private boolean convertTo = false;
}
