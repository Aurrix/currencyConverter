package lv.javaguru.currencyConverter.services.exchangeAPI;

import java.util.Currency;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;

public interface ExternalExchangeAPIService {
    Set<Rate> getAllRates(Currency base);
}
