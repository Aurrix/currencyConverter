package lv.javaguru.currencyConverter.services.covert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConvertServiceImpl implements CurrencyConvertService {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${baseCurrency}")
  public String baseCurrency;
  @Value("${defaultRate}")
  private BigDecimal defaultRate;
  private final Set<Rate> rates;

  public CurrencyConvertServiceImpl(Set<Rate> rates) {
    this.rates = rates;
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
    return convertTo.divide(rate,3,RoundingMode.HALF_UP);
  }

  public BigDecimal getCurrencyRate(Currency from, Currency to) {
    if (from.getCurrencyCode().equals(baseCurrency)) {
      logger.info("Trigger from / base");
      return getRateOfCurrency(to).getRate();
    }
    if (to.getCurrencyCode().equals(baseCurrency)) {
      logger.info("Trigger to / base");
      return new BigDecimal(1).setScale(3, RoundingMode.HALF_UP)
          .divide(getRateOfCurrency(from).getRate(),10,RoundingMode.HALF_UP);
    }
    else{
      logger.info("Trigger nor from / to");
      return getRateOfCurrency(to)
          .getRate()
          .divide(getRateOfCurrency(from).getRate(), RoundingMode.HALF_UP);
    }
  }

  private Rate getRateOfCurrency(Currency currency) {
    for (Rate rate : rates) {
      if (rate.getCurrency().getCurrencyCode().equals(currency.getCurrencyCode())) {
        return rate;
      }
    }
    return new Rate(currency,defaultRate);
  }
}