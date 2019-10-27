package lv.javaguru.currency.converter.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import lv.javaguru.currency.converter.validation.rules.UniqueCurrencyValidator;

@Target({TYPE, ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCurrencyValidator.class)
public @interface UniqueCurrencies {

  String message() default "Conversion currencies should not be the same.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String first();

  String second();
}
