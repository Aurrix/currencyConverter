package lv.javaguru.currencyConverter.validation.rules;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import javax.validation.ConstraintValidatorContext;
import lv.javaguru.currencyConverter.entities.ConversionRequest;
import lv.javaguru.currencyConverter.validation.annotations.UniqueCurrencies;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(MockitoJUnitRunner.class)
public class UniqueCurrencyValidatorTest {

  @Mock
  UniqueCurrencies constraintAnnotation;

  @Mock
  ConstraintValidatorContext constraintValidatorContext;

  @MockBean
  ConversionRequest conversionRequest;

  @InjectMocks
  UniqueCurrencyValidator victim = new UniqueCurrencyValidator();

  @Test
  public void isValid()
      throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    Mockito.when(constraintAnnotation.first()).thenReturn("from");
    Mockito.when(constraintAnnotation.second()).thenReturn("to");

    conversionRequest = new ConversionRequest("USD", "EUR", new BigDecimal(100), false);

    Assert.assertTrue(victim.isValid(conversionRequest, constraintValidatorContext));
  }
}