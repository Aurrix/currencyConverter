package lv.javaguru.currency.converter.services.comission;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;
import lv.javaguru.currency.converter.config.GlobalSettings;
import lv.javaguru.currency.converter.dao.repositories.CommissionsRepository;
import lv.javaguru.currency.converter.dao.repositories.ConversionResponseRepository;
import lv.javaguru.currency.converter.entities.Commissions;
import lv.javaguru.currency.converter.entities.ConversionResponse;
import lv.javaguru.currency.converter.services.covert.CurrencyConvertServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommissionChargeServiceImpl implements CommissionChargeService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ConversionResponseRepository conversionResponseRepository;
  private final CommissionsRepository commissionsRepository;
  private final CurrencyConvertServiceImpl currencyConvertService;
  private final GlobalSettings globalSettings;

  public CommissionChargeServiceImpl(
      ConversionResponseRepository conversionResponseRepository,
      CommissionsRepository commissionsRepository,
      CurrencyConvertServiceImpl currencyConvertService,
      GlobalSettings globalSettings) {
    this.conversionResponseRepository = conversionResponseRepository;
    this.commissionsRepository = commissionsRepository;
    this.currencyConvertService = currencyConvertService;
    this.globalSettings = globalSettings;
  }

  @Override
  public ConversionResponse returnChargedAndConvertedAmount(
      Currency from, Currency to, BigDecimal amount, boolean isTo) {
    ConversionResponse conversionResponse = new ConversionResponse();
    conversionResponse.setId(0L);
    conversionResponse.setCurFrom(from);
    conversionResponse.setCurTo(to);

    Commissions commissions =
        commissionsRepository.findByCurrencyPair(
            from.getCurrencyCode() + "/" + to.getCurrencyCode());
    logger.info("Commissions {}", from.getCurrencyCode() + "/" + to.getCurrencyCode());
    if (commissions != null) {
      conversionResponse.setCommission(
          commissions.getCommission().setScale(4, RoundingMode.HALF_EVEN));
    } else {
      conversionResponse.setCommission(
          new BigDecimal(globalSettings.getDefaultCommission())
              .setScale(4, RoundingMode.HALF_EVEN));
    }
    BigDecimal charges = conversionResponse.getCommission().multiply(amount);
    conversionResponse.setAmountCharged(charges.setScale(2, RoundingMode.HALF_EVEN));
    if (isTo) {
      conversionResponse.setAmountFrom(
          currencyConvertService
              .convertTo(from, to, amount.subtract(charges))
              .setScale(2, RoundingMode.HALF_EVEN));
      conversionResponse.setAmountTo(amount.setScale(2, RoundingMode.HALF_EVEN));
    } else {
      conversionResponse.setAmountFrom(amount.setScale(2, RoundingMode.HALF_EVEN));
      conversionResponse.setAmountTo(
          currencyConvertService
              .convertFrom(from, to, amount.subtract(charges))
              .setScale(2, RoundingMode.HALF_EVEN));
    }
    BigDecimal rate = currencyConvertService.getCurrencyRate(from, to);
    conversionResponse.setRate(rate);
    conversionResponse.setTimeStamp(LocalDateTime.now());
    return conversionResponseRepository.save(conversionResponse);
  }
}
