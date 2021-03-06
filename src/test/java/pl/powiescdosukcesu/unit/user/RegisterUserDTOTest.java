package pl.powiescdosukcesu.unit.user;

import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.validation.annotations.NotUsed;
import pl.powiescdosukcesu.validation.logic.NotUsedValidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserDTOTest {

    @Mock
    private AppUserRepository appUserRepository;

    private Validator validator;

    private final ConstraintAnnotationDescriptor.Builder<NotUsed> descriptorBuilder =
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
    public void contextLoads() {}

    @Test
    public void blankFieldsShouldFailValidation() {

        registerUserDTO = RegisterUserDTO.builder()
                            .email("")
                            .username("")
                            .password("")
                            .matchingPassword("")
                            .sex("").build();

        Set<ConstraintViolation<RegisterUserDTO>> violations = validator.validate(registerUserDTO);

        assertThat(violations).isNotEmpty();
    }

    @Test
    public void anAlreadyInUseUsernameShouldFailValidation() {

        given(appUserRepository.existsByUsername("alreadyInUseUsername")).willReturn(true);
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

        assertThat(violations.stream().map(ConstraintViolation::getPropertyPath).collect(Collectors.toList())
                .contains("email"));
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

        violations.stream().map(ConstraintViolation::getPropertyPath).collect(Collectors.toList())
                .contains("matchingPassword");
    }
}
