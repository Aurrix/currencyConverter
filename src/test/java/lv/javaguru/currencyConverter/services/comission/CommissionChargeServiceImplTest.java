package lv.javaguru.currencyConverter.services.comission;

import java.math.BigDecimal;
import java.util.Currency;
import lv.javaguru.currencyConverter.dao.CommissionsRepository;
import lv.javaguru.currencyConverter.dao.ConversionResponseRepository;
import lv.javaguru.currencyConverter.entities.Commissions;
import lv.javaguru.currencyConverter.entities.ConversionResponse;
import lv.javaguru.currencyConverter.services.covert.CurrencyConvertServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommissionChargeServiceImplTest {
  @Mock private ConversionResponseRepository conversionResponseRepository;
  @Mock private CommissionsRepository commissionsRepository;
  @Mock private CurrencyConvertServiceImpl currencyConvertService;

  private CommissionChargeServiceImpl commissionChargeService;

  @Before
  public void setUp() {
    commissionChargeService =
        new CommissionChargeServiceImpl(
            conversionResponseRepository, commissionsRepository, currencyConvertService);
  }

  @Test
  public void returnChargedAndConvertedAmount() {
    Commissions commissions = new Commissions();
    commissions.setId(1L);
    commissions.setCurrencyPair("EUR/USD");
    commissions.setCommission(new BigDecimal(0.05));
    Mockito.when(commissionsRepository.findByCurrencyPair(Mockito.any())).thenReturn(commissions);
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("USD");
    BigDecimal amount = new BigDecimal(100);

    Mockito.when(currencyConvertService.convertFrom(Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(new BigDecimal(100));

    Mockito.when(currencyConvertService.convertTo(Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(new BigDecimal(200));

    ConversionResponse isToResponse =
        commissionChargeService.returnChargedAndConvertedAmount(from, to, amount, true);

    ConversionResponse isFromResponse =
        commissionChargeService.returnChargedAndConvertedAmount(from, to, amount, false);

    Mockito.when(conversionResponseRepository.save(Mockito.any())).then(i->i.getArgument(0));

    Assert.assertEquals(new BigDecimal(10), isToResponse.getAmountCharged());
    Assert.assertEquals(new BigDecimal(5), isFromResponse.getAmountCharged());
  }
}
