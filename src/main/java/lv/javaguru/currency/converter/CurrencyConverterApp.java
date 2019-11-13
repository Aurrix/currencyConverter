package lv.javaguru.currency.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyConverterApp {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyConverterApp.class, args);
  }
}
