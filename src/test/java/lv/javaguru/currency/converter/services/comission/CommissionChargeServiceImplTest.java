package lv.javaguru.currency.converter.services.comission;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lv.javaguru.currency.converter.config.GlobalSettings;
import lv.javaguru.currency.converter.dao.repositories.CommissionsRepository;
import lv.javaguru.currency.converter.dao.repositories.ConversionResponseRepository;
import lv.javaguru.currency.converter.entities.Commissions;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import lv.javaguru.currency.converter.services.covert.CurrencyConvertServiceImpl;
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
  @Mock
  private GlobalSettings globalSettings;
  private CommissionChargeServiceImpl commissionChargeService;

  @Before
  public void setUp() {
    commissionChargeService =
        new CommissionChargeServiceImpl(
            conversionResponseRepository,
            commissionsRepository,
            currencyConvertService,
            globalSettings);
  }

  @Test
  public void returnChargedAndConvertedAmount() {
    Commissions commissions = new Commissions();
    commissions.setId(1L);
    commissions.setCurrencyPair("EUR/USD");
    commissions.setCommission(new BigDecimal(0.05));
    Mockito.when(commissionsRepository.findByCurrencyPair(Mockito.anyString()))
        .thenReturn(commissions);
    Currency from = Currency.getInstance("EUR");
    Currency to = Currency.getInstance("USD");
    BigDecimal amount = new BigDecimal(100);
    Mockito.when(currencyConvertService.convertFrom(Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(new BigDecimal(100));
    Mockito.when(currencyConvertService.convertTo(Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(new BigDecimal(200));
    Mockito.when(conversionResponseRepository.save(Mockito.any()))
        .then(invocation -> invocation.getArgument(0));
    ConversionResponse isToResponse =
        commissionChargeService.returnChargedAndConvertedAmount(from, to, amount, true);
    commissions.setCommission(new BigDecimal(0.1));
    Mockito.when(commissionsRepository.findByCurrencyPair(Mockito.anyString()))
        .thenReturn(commissions);
    ConversionResponse isFromResponse =
        commissionChargeService.returnChargedAndConvertedAmount(from, to, amount, false);
    Assert.assertEquals(
        new BigDecimal(5.00).setScale(2, RoundingMode.HALF_EVEN), isToResponse.getAmountCharged());
    Assert.assertEquals(
        new BigDecimal(10.00).setScale(2, RoundingMode.HALF_EVEN),
        isFromResponse.getAmountCharged());
  }
}
