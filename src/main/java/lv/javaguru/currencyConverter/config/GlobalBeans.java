package lv.javaguru.currencyConverter.config;

import java.util.LinkedHashSet;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalBeans {

  private final Set<Rate> rateSet;

  public GlobalBeans() {
    rateSet = new LinkedHashSet<>();
  }

  @Bean
  public Set<Rate> returnRatesCache(){
    return rateSet;
  }

}
