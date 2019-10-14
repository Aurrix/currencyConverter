package lv.javaguru.currencyConverter.services.covert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import lv.javaguru.currencyConverter.entities.Rate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CurrencyConvertServiceImplTest {

  private CurrencyConvertServiceImpl victim;

  @Before
  public void initClass(){
    Set<Rate> rates = new HashSet<>();
    rates.add(new Rate(Currency.getInstance("USD"), new BigDecimal(1.55)));
    rates.add(new Rate(Currency.getInstance("MXN"), new BigDecimal(1.25)));
    rates.add(new Rate(Currency.getInstance("CZK"), new BigDecimal(0.24)));



    victim = new CurrencyConvertServiceImpl(rates);
    victim.baseCurrency = "EUR";
  }

  @Test
  public void convertFrom() {
    Assert.assertEquals(new BigDecimal(155.00).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("EUR"),Currency.getInstance("USD"), new BigDecimal(100.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(24.00).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("EUR"),Currency.getInstance("CZK"), new BigDecimal(100.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(100.00).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("MXN"),Currency.getInstance("EUR"), new BigDecimal(125.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(100.00).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("CZK"),Currency.getInstance("EUR"), new BigDecimal(24.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(645.83).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("CZK"),Currency.getInstance("USD"), new BigDecimal(100.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(15.48).setScale(2,RoundingMode.HALF_UP),victim.convertFrom(
        Currency.getInstance("USD"),Currency.getInstance("CZK"), new BigDecimal(100.00)
    ).setScale(2, RoundingMode.HALF_UP));

  }

  @Test
  public void convertTo() {

    Assert.assertEquals(new BigDecimal(64.52).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("EUR"),Currency.getInstance("USD"), new BigDecimal(100.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(4.17).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("EUR"),Currency.getInstance("CZK"), new BigDecimal(1.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(100.00).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("MXN"),Currency.getInstance("EUR"), new BigDecimal(80.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(24.00).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("CZK"),Currency.getInstance("EUR"), new BigDecimal(100)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(0.16).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("CZK"),Currency.getInstance("USD"), new BigDecimal(1.00)
    ).setScale(2, RoundingMode.HALF_UP));

    Assert.assertEquals(new BigDecimal(6.46).setScale(2,RoundingMode.HALF_UP),victim.convertTo(
        Currency.getInstance("USD"),Currency.getInstance("CZK"), new BigDecimal(1.00)
    ).setScale(2, RoundingMode.HALF_UP));

  }
}