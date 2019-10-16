package lv.javaguru.currencyConverter.validation.rules;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lv.javaguru.currencyConverter.entities.ConversionRequest;
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
    if (value instanceof ConversionRequest) {
      fieldOne = ((ConversionRequest) value).from;
      fieldTwo = ((ConversionRequest) value).to;
      return !fieldOne.equals(fieldTwo);
    } else {
      try {
        fieldOne = getFieldOfName(value, firstCurrency);
        fieldTwo = getFieldOfName(value, secondCurrency);
        if (fieldOne.equals(fieldTwo)) {
          return false;
        }
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  private Object getFieldOfName(Object value, String fieldName)
      throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    return BeanUtils.getProperty(value, fieldName);
  }

}
