package pl.powiescdosukcesu.user;

import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserRepository;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.validation.annotations.NotUsed;
import pl.powiescdosukcesu.validation.logic.NotUsedValidation;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserDTOTest {

    @Mock
    private AppUserRepository appUserRepository;


    private Validator validator;

    ConstraintAnnotationDescriptor.Builder<NotUsed> descriptorBuilder =
            new ConstraintAnnotationDescriptor.Builder<>(NotUsed.class);

    @InjectMocks
    private NotUsedValidation notUsedValidation;


    private RegisterUserDTO registerUserDTO = new RegisterUserDTO();


    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void contextLoads() {


    }

    @Test
    public void blankFieldsShouldFailValidation() {

        registerUserDTO =
                new RegisterUserDTO("", "", "", "", "", "", "", "");
        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations.stream().map(val -> val.getMessage())).contains("*Pole jest wymagane");
    }

    @Test
    public void anAlreadyInUseUsernameShouldFailValidation() {

        given(appUserRepository.findByUsername("alreadyInUseUsername")).willReturn(Optional.of(new AppUser()));
        notUsedValidation.initialize(descriptorBuilder.build().getAnnotation());

        assertThat(notUsedValidation.isValid("alreadyInUseUsername", null)).isFalse();
    }

    @Test
    public void invalidEmailShouldFailValidation() {


        registerUserDTO = RegisterUserDTO.builder()
                .username("")
                .password("test")
                .matchingPassword("test")
                .email("invalidemail")
                .build();

        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations.stream().map(val -> val.getMessage())).contains("*Niepoprawny adres E-Mail");
    }

    @Test
    public void notMatchingPasswordsShouldFailValidation() {
        registerUserDTO = RegisterUserDTO.builder()
                .username("")
                .password("test")
                .matchingPassword("notTest")
                .email("invalidemail")
                .build();

        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations.stream().map(val -> val.getMessage())).contains("Hasła się nie zgadzają");
    }

}
