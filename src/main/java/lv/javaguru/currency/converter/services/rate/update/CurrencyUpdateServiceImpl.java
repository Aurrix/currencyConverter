package lv.javaguru.currency.converter.services.rate.update;

import lv.javaguru.currency.converter.config.GlobalSettings;
import lv.javaguru.currency.converter.events.UpdateRateEvent;
import lv.javaguru.currency.converter.services.exchange.api.ExternalExchangeAPIService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CurrencyUpdateServiceImpl implements CurrencyUpdateService {

  private final GlobalSettings globalSettings;
  private final ExternalExchangeAPIService externalExchangeAPIService;
  private final ApplicationEventPublisher applicationEventPublisher;

  public CurrencyUpdateServiceImpl(
      GlobalSettings globalSettings,
      ExternalExchangeAPIService externalExchangeAPIService,
      ApplicationEventPublisher applicationEventPublisher) {
    this.globalSettings = globalSettings;
    this.externalExchangeAPIService = externalExchangeAPIService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  @Scheduled(fixedDelayString = "${settings.updateRate}")
  public void scheduling() {
    applicationEventPublisher.publishEvent(
        new UpdateRateEvent(this, externalExchangeAPIService, globalSettings));
  }
}
