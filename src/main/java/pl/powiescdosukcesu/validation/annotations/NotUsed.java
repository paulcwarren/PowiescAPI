package pl.powiescdosukcesu.validation.annotations;

import pl.powiescdosukcesu.validation.logic.NotUsedValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy=NotUsedValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface NotUsed {

	String message() default "*Username already in use";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
