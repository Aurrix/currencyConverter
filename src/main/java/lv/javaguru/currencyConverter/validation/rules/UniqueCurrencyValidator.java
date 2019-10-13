package lv.javaguru.currencyConverter.validation.rules;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lv.javaguru.currencyConverter.validation.annotations.UniqueCurrencies;
import org.apache.commons.beanutils.BeanUtils;

public class UniqueCurrencyValidator implements ConstraintValidator<UniqueCurrencies,Object> {

  private String firstCurrency;
  private String secondCurrency;

  @Override
  public void initialize(UniqueCurrencies constraintAnnotation) {
    firstCurrency = constraintAnnotation.first();
    secondCurrency = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Object fieldOne;
    Object fieldTwo;
    try {
      fieldOne = BeanUtils.getProperty(value,firstCurrency);
      fieldTwo = BeanUtils.getProperty(value,secondCurrency);
      if(fieldOne.equals(fieldTwo)){
        return false;
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return true;
  }
}
