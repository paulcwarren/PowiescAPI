package pl.powiescdosukcesu.validation.logic;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;
import pl.powiescdosukcesu.validation.annotations.NotUsed;

public class NotUsedValidation implements ConstraintValidator<NotUsed,String> {

	@Autowired
	private AppUserService appUserService;
	
	@Override
	public void initialize(NotUsed constraintAnnotation) {

	}

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		AppUser user = appUserService.getUser(value);
		
		if(user==null)
			return true;
		
		return false;
	}

}
