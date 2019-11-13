package lv.javaguru.currency.converter.validation.rules;

import java.util.Currency;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lv.javaguru.currency.converter.validation.annotations.IsCurrency;

public class IsCurrencyValidator implements ConstraintValidator<IsCurrency, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    for (Currency currency : Currency.getAvailableCurrencies()) {
      if (currency.getCurrencyCode().equals(value)) return true;
    }
    return false;
  }
}
