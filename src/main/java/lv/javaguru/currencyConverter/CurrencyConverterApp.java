package lv.javaguru.currencyConverter;

import lv.javaguru.currencyConverter.services.update.CurrencyUpdateService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CurrencyConverterApp {
  public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApp.class,args);
      }

      @Component
      public class Initializer implements ApplicationRunner {

          private final CurrencyUpdateService currencyUpdateService;

        public Initializer(
            CurrencyUpdateService currencyUpdateService) {
          this.currencyUpdateService = currencyUpdateService;
        }

        @Override
        public void run(ApplicationArguments args) {
          currencyUpdateService.init();
        }
      }
}
