package lv.javaguru.currencyConverter.services.update;

import java.util.Currency;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lv.javaguru.currencyConverter.entities.Rate;
import lv.javaguru.currencyConverter.services.exchangeAPI.ExternalExchangeAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyUpdateServiceImpl implements CurrencyUpdateService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${updateRateMinutes}")
  private int updateRate;
  @Value("${baseCurrency}")
  private String baseCurrency;
  private final Set<Rate> rates;
  private final ExternalExchangeAPIService externalExchangeAPIService;

  private boolean initiated;
  private ScheduledExecutorService executorService;
  private Thread updateThread;


  public CurrencyUpdateServiceImpl(Set<Rate> rates,
      ExternalExchangeAPIService externalExchangeAPIService) {
    this.rates = rates;
    this.externalExchangeAPIService = externalExchangeAPIService;
  }

  @Override
  public void init() {
    if (!initiated){
      initiated = true;
      updateThread = new Thread(()-> {
        rates.addAll(externalExchangeAPIService.getAllRates(Currency.getInstance(baseCurrency)));
        logger.info("Rates updated");
      });

      executorService = Executors.newSingleThreadScheduledExecutor();
      executorService.scheduleAtFixedRate(updateThread,0,updateRate, TimeUnit.MINUTES);
    }
    else logger.warn("Update service was already initiated!");
  }
}
