package lv.javaguru.currencyConverter.config.exchange;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api", ignoreInvalidFields = true)
@Setter@Getter
public class ExchangeAPIsConfig {
public List<ExchangeAPIProperties> exchanges;
}
