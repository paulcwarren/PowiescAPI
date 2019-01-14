package pl.powiescdosukcesu.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import pl.powiescdosukcesu.validation.logic.NotUsedValidation;

@Constraint(validatedBy=NotUsedValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface NotUsed {

	String message() default "*Nazwa użytkownika już zajęta";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
