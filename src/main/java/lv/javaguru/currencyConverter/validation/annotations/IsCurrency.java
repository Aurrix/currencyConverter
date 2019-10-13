package lv.javaguru.currencyConverter.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import lv.javaguru.currencyConverter.validation.rules.IsCurrencyValidator;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy = IsCurrencyValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsCurrency {
String message() default "Not a currency or is not supported currency";
Class<?>[] groups() default  {};
Class<? extends Payload> [] payload() default  {};
}
