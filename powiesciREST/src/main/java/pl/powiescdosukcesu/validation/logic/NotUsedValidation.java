package pl.powiescdosukcesu.validation.logic;

import org.springframework.beans.factory.annotation.Autowired;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;
import pl.powiescdosukcesu.validation.annotations.NotUsed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotUsedValidation implements ConstraintValidator<NotUsed,String> {

	@Autowired
	private AppUserService appUserService;
	
	@Override
	public void initialize(NotUsed constraintAnnotation) {

	}

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		AppUser user = appUserService.getUser(value);

        return user == null;

    }

}
