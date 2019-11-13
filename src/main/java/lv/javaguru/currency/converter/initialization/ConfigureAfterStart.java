package lv.javaguru.currency.converter.initialization;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import lv.javaguru.currency.converter.config.exchange.ExchangeAPIsConfig;
import lv.javaguru.currency.converter.dao.repositories.CommissionsRepository;
import lv.javaguru.currency.converter.entities.Commissions;
import org.springframework.stereotype.Component;

@Component
public class ConfigureAfterStart {

  private final ExchangeAPIsConfig exchangeAPIsConfig;
  private final CommissionsRepository commissionsRepository;

  public ConfigureAfterStart(
      ExchangeAPIsConfig exchangeAPIsConfig, CommissionsRepository commissionsRepository) {
    this.exchangeAPIsConfig = exchangeAPIsConfig;
    this.commissionsRepository = commissionsRepository;
  }

  @PostConstruct
  private void configure() {
    exchangeAPIsConfig
        .getCommissions()
        .forEach(
            (key, value) ->
                commissionsRepository.save(
                    new Commissions(
                        0L, key.replace(".", "/").substring(2), new BigDecimal(value))));
  }
}
