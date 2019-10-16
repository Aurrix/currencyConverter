package lv.javaguru.currencyConverter.validation.rules;

import org.junit.Assert;
import org.junit.Test;

public class IsCurrencyValidatorTest {

  @Test
  public void isValid() {
    IsCurrencyValidator victim = new IsCurrencyValidator();
    Assert.assertTrue(victim.isValid("USD", null));
    Assert.assertFalse(victim.isValid("US", null));
  }
}