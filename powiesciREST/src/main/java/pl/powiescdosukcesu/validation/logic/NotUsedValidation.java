package pl.powiescdosukcesu.validation.logic;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.powiescdosukcesu.appuser.AppUserRepository;
import pl.powiescdosukcesu.validation.annotations.NotUsed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@NoArgsConstructor
public class NotUsedValidation implements ConstraintValidator<NotUsed,String> {


	private AppUserRepository appUserRepository;


	@Autowired
	public NotUsedValidation(AppUserRepository appUserRepository){
		this.appUserRepository=appUserRepository;
	}
	@Override
	public void initialize(NotUsed constraintAnnotation) {

	}


	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {

		if(username==null || username.equals(""))
			return false;

		return !(appUserRepository.findByUsername(username).isPresent());

    }

}
