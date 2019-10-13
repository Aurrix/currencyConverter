package lv.javaguru.currencyConverter.config;

import java.util.LinkedHashSet;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GlobalBeans {

  private final Set<Rate> rateSet;
  private final RestTemplate restTemplate;

  public GlobalBeans() {
    rateSet = new LinkedHashSet<>();
    restTemplate = new RestTemplate();
  }

  @Bean
  public Set<Rate> returnRatesCache() {
    return rateSet;
  }

  @Bean
  public RestTemplate returnRestTemplate() {
    return restTemplate;
  }
}
