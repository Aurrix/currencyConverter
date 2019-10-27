package lv.javaguru.currency.converter.services.covert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Set;
import lv.javaguru.currency.converter.config.GlobalSettings;
import lv.javaguru.currency.converter.entities.Rate;
import lv.javaguru.currency.converter.validation.RateNotUpToDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConvertServiceImpl implements CurrencyConvertService {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private final GlobalSettings globalSettings;
  private final Set<Rate> rates;

  public CurrencyConvertServiceImpl(Set<Rate> rates, GlobalSettings globalSettings) {
    this.rates = rates;
    this.globalSettings = globalSettings;
  }

  @Override
  public BigDecimal convertFrom(Currency from, Currency to, BigDecimal convertAmount) {
    BigDecimal rate = getCurrencyRate(from, to);
    logger.info("Returned rate {}", rate);
    return convertAmount.multiply(rate);
  }

  @Override
  public BigDecimal convertTo(Currency from, Currency to, BigDecimal convertTo) {
    BigDecimal rate = getCurrencyRate(from, to);
    logger.info("Returned rate {}", rate);
    return convertTo.divide(rate, 3, RoundingMode.HALF_EVEN);
  }

  public BigDecimal getCurrencyRate(Currency from, Currency to) {
    if (from.getCurrencyCode().equals(globalSettings.getBaseCurrency())) {
      logger.info("Trigger from / base");
      return getRateOfCurrency(to).getRate();
    }
    if (to.getCurrencyCode().equals(globalSettings.getBaseCurrency())) {
      logger.info("Trigger to / base");
      return new BigDecimal(1)
          .setScale(3, RoundingMode.HALF_EVEN)
          .divide(getRateOfCurrency(from).getRate(), 10, RoundingMode.HALF_EVEN);
    } else {
      logger.info("Trigger nor from / to");
      return getRateOfCurrency(to)
          .getRate()
          .divide(getRateOfCurrency(from).getRate(), RoundingMode.HALF_EVEN);
    }
  }

  private Rate getRateOfCurrency(Currency currency) {
    for (Rate rate : rates) {
      if (rate.getCurrency().getCurrencyCode().equals(currency.getCurrencyCode())) {
        if (rate.getDateTime()
            .isAfter(
                LocalDateTime.now()
                    .minus(globalSettings.getUpdateRate() + 20000, ChronoUnit.MILLIS))) {
          return rate;
        } else {
          throw new RateNotUpToDateException(
              "Rate is not up to date: "
                  + rate.getCurrency().getCurrencyCode()
                  + " / "
                  + rate.getDateTime());
        }
      }
    }
    return new Rate(currency, globalSettings.getDefaultRate(), LocalDateTime.now());
  }
}
