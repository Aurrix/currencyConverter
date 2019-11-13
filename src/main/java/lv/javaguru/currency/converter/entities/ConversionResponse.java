package lv.javaguru.currency.converter.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {

  @Id
  @GeneratedValue
  private Long id;
  private BigDecimal amountFrom;
  private BigDecimal amountTo;
  private Currency curFrom;
  private Currency curTo;
  private BigDecimal commission;
  private BigDecimal amountCharged;
  private BigDecimal rate;
  private LocalDateTime timeStamp;
}
