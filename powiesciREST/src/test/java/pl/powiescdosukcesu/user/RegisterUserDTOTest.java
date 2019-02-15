package pl.powiescdosukcesu.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.powiescdosukcesu.appuser.AppUserRepository;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserDTOTest {

    @Mock
    private AppUserRepository appUserRepository;

    private Validator validator;

    @InjectMocks
    private RegisterUserDTO registerUserDTO = new RegisterUserDTO();



    @Before
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void blankFieldsShouldFailValidation(){

        given(appUserRepository.findByUsername("")).willReturn(Optional.empty());

        registerUserDTO =
                new RegisterUserDTO("", "","","","","","","");
        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations.stream().map(val->val.getMessage())).contains("*Pole jest wymagane");
    }

    @Test
    public void invalidEmailShouldFailValidation(){

        //given
        given(appUserRepository.findByUsername("test")).willReturn(Optional.empty());

        registerUserDTO = RegisterUserDTO.builder()
                            .username("test")
                            .password("test")
                            .matchingPassword("test")
                            .email("invalidemail")
                            .build();

        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations.stream().map(val->val.getMessage())).contains("*Niepoprawny adres E-Mail");
    }

}
