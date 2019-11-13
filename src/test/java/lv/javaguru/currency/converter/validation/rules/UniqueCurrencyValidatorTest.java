package lv.javaguru.currency.converter.validation.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import javax.validation.ConstraintValidatorContext;
import lv.javaguru.currency.converter.entities.ConversionRequest;
import lv.javaguru.currency.converter.validation.annotations.UniqueCurrencies;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
  public void isValid() {
    conversionRequest = new ConversionRequest("USD", "EUR", new BigDecimal(100), false);
    assertTrue(victim.isValid(conversionRequest, constraintValidatorContext));

    conversionRequest = new ConversionRequest("EUR", "EUR", new BigDecimal(100), false);
    assertFalse(victim.isValid(conversionRequest, constraintValidatorContext));
  }
}
