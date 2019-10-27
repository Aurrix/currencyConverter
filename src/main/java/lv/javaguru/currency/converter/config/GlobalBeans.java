package lv.javaguru.currency.converter.config;

import java.util.LinkedHashSet;
import java.util.Set;
import lv.javaguru.currency.converter.entities.Rate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GlobalBeans {

  @Bean
  public Set<Rate> returnRatesCache() {
    return new LinkedHashSet<>();
  }

  @Bean
  public RestTemplate returnRestTemplate() {
    return new RestTemplate();
  }
}
