package pl.powiescdosukcesu.validation.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pl.powiescdosukcesu.validation.logic.PasswordMatchValidator;

@Constraint(validatedBy = PasswordMatchValidator.class)
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface PasswordMatch {

	String message() default "*Hasła nie są identyczne";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
