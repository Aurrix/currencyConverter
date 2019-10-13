package lv.javaguru.currencyConverter.services.update;

import java.util.Currency;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lv.javaguru.currencyConverter.entities.ApplicationState;
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
  private ApplicationState applicationState;
  private Thread updateThread;


  public CurrencyUpdateServiceImpl(Set<Rate> rates,
      ExternalExchangeAPIService externalExchangeAPIService,
      ApplicationState applicationState) {
    this.rates = rates;
    this.externalExchangeAPIService = externalExchangeAPIService;
    this.applicationState = applicationState;
  }

  @Override
  public void init() {
    if (!initiated || applicationState.isConnecting()){
      initiated = true;
      updateThread = new Thread(()-> {
        rates.addAll(externalExchangeAPIService.getAllRates(Currency.getInstance(baseCurrency)));
        logger.info("Rates updated");
      });
      startExecutorService(updateThread, updateRate);
    }
    else logger.warn("Update service was already initiated!");
  }
  private void startExecutorService(Thread thread, int tick){
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(thread,0,tick, TimeUnit.MINUTES);
  }
}
