package lv.javaguru.currency.converter.services.exchange.api;

import java.util.Currency;
import java.util.Set;
import lv.javaguru.currency.converter.entities.Rate;

public interface ExternalExchangeAPIService {

    Set<Rate> getAllRates(Currency base);
}
