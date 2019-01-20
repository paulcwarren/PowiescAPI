package pl.powiescdosukcesu.validation.annotations;

import pl.powiescdosukcesu.validation.logic.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordMatchValidator.class)
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface PasswordMatch {

	String message() default "*The passwords don't match";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
