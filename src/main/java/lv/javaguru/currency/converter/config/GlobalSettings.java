package lv.javaguru.currency.converter.config;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "settings", ignoreInvalidFields = true)
@Getter
@Setter
public class GlobalSettings {

  private Double defaultCommission;
  private String baseCurrency;
  private BigDecimal defaultRate;
  private long updateRate;
}
