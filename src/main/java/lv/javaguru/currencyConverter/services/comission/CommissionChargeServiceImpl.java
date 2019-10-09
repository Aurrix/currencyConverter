package lv.javaguru.currencyConverter.services.comission;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.Currency;
import lv.javaguru.currencyConverter.dao.CommissionsRepository;
import lv.javaguru.currencyConverter.dao.ConversionResponseRepository;
import lv.javaguru.currencyConverter.entities.Commissions;
import lv.javaguru.currencyConverter.entities.ConversionResponse;
import lv.javaguru.currencyConverter.services.covert.CurrencyConvertService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommissionChargeServiceImpl implements CommissionChargeService {
  private final ConversionResponseRepository conversionResponseRepository;
  private final CommissionsRepository commissionsRepository;
  private final CurrencyConvertService currencyConvertService;
  @Value("${defaultChargePercent}")
  private Double defaultCommission;

  public CommissionChargeServiceImpl(
      ConversionResponseRepository conversionResponseRepository,
      CommissionsRepository commissionsRepository, CurrencyConvertService currencyConvertService) {
    this.conversionResponseRepository = conversionResponseRepository;
    this.commissionsRepository = commissionsRepository;
    this.currencyConvertService = currencyConvertService;
  }

  @Override
  public ConversionResponse returnChargedAndConvertedAmount(
      Currency from, Currency to, BigDecimal amount, boolean isTo) {
    ConversionResponse conversionResponse = new ConversionResponse();
    conversionResponse.setId(0L);
    conversionResponse.setCurFrom(from);
    conversionResponse.setCurTo(to);
    Commissions commissions = commissionsRepository.findByCurrencyPair(from.getDisplayName()+"/"+to.getDisplayName());
    if (commissions != null) {
      conversionResponse.setCommission(commissions.getCommission());
    }
    else {
    conversionResponse.setCommission(new BigDecimal(defaultCommission));
    }
    BigDecimal charges = conversionResponse.getCommission().multiply(amount);
    conversionResponse.setAmountCharged(charges.setScale(2,RoundingMode.HALF_UP));
    if (isTo) {
      conversionResponse.setAmountFrom(currencyConvertService.convertTo(from, to, amount.subtract(charges)).setScale(2,RoundingMode.HALF_UP));
      conversionResponse.setAmountTo(amount.setScale(2,RoundingMode.HALF_UP));
    } else {
      conversionResponse.setAmountFrom(amount.setScale(2,RoundingMode.HALF_UP));
      conversionResponse.setAmountTo(currencyConvertService.convertFrom(from, to, amount.subtract(charges)).setScale(2,RoundingMode.HALF_UP));
    }
    BigDecimal rate = conversionResponse.getAmountFrom().divide(conversionResponse.getAmountTo(),
        RoundingMode.HALF_UP);
    conversionResponse.setRate(rate);
    conversionResponse.setTimeStamp(LocalTime.now());
    return conversionResponseRepository.save(conversionResponse);
  }
}
