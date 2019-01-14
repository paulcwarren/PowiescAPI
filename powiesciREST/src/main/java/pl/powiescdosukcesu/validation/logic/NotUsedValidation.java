package pl.powiescdosukcesu.validation.logic;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pl.powiescdosukcesu.entities.PowiesciUser;
import pl.powiescdosukcesu.services.UserService;
import pl.powiescdosukcesu.validation.annotations.NotUsed;

public class NotUsedValidation implements ConstraintValidator<NotUsed,String> {

	@Autowired
	private UserService userService;
	
	@Override
	public void initialize(NotUsed constraintAnnotation) {

	}

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		PowiesciUser user = userService.getUser(value);
		
		if(user==null)
			return true;
		
		return false;
	}

}
