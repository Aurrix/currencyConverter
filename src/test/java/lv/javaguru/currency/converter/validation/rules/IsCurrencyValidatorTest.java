package lv.javaguru.currency.converter.validation.rules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IsCurrencyValidatorTest {

  @Test
  public void isValid() {
    IsCurrencyValidator victim = new IsCurrencyValidator();
    assertTrue(victim.isValid("USD", null));
    assertFalse(victim.isValid("US", null));
  }
}
