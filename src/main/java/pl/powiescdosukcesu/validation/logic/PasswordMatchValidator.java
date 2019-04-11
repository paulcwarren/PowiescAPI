package pl.powiescdosukcesu.validation.logic;

import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.validation.annotations.PasswordMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch,Object> {

	
	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		RegisterUserDTO user=(RegisterUserDTO)object;
		if(user.getPassword()==null || user.getMatchingPassword()==null)
			return false;
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
