package lv.javaguru.currency.converter.entities;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Commissions {

  @Id
  @GeneratedValue
  private Long id;
  private String currencyPair;
  private BigDecimal commission;
}
