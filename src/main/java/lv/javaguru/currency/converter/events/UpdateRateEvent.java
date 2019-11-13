package lv.javaguru.currency.converter.events;

import java.util.Currency;
import lv.javaguru.currency.converter.config.GlobalSettings;
import lv.javaguru.currency.converter.services.exchange.api.ExternalExchangeAPIService;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.Async;

public class UpdateRateEvent extends ApplicationEvent {

  private final ExternalExchangeAPIService externalExchangeAPIService;
  private final GlobalSettings globalSettings;

  public UpdateRateEvent(
      Object source,
      ExternalExchangeAPIService externalExchangeAPIService,
      GlobalSettings globalSettings) {
    super(source);
    this.externalExchangeAPIService = externalExchangeAPIService;
    this.globalSettings = globalSettings;
    updateRates();
  }

  @Async
  public void updateRates() {
    externalExchangeAPIService.getAllRates(Currency.getInstance(globalSettings.getBaseCurrency()));
  }
}
