package lv.javaguru.currency.converter.config.exchange;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api", ignoreInvalidFields = true)
@Setter
@Getter
public class ExchangeAPIsConfig {

  private List<String> exchanges;
  private Map<String, String> commissions;
}
