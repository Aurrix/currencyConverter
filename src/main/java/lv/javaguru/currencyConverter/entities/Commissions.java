package lv.javaguru.currencyConverter.entities;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Commissions {
  @Id
  @GeneratedValue
  private Long id;
  private String currencyPair;
  private BigDecimal commission;
}
