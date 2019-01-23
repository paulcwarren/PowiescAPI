package pl.powiescdosukcesu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.powiescdosukcesu.appuser.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AppUserServiceImpl appUserService;


    @Test
    public void shouldCreateAndSaveUserWithBcryptedPasswordAndRolesGive(){
        //given
        given(roleRepository.findRoleByName("ROLE_NORMAL_USER")).willReturn(new Role("NORMAL_USER"));
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                                            .userName("barry")
                                            .password("sally12345!")
                                            .matchingPassword("sally12345!")
                                            .email("Sally@gmail.com")
                                            .firstName("Anthony")
                                            .lastName("Captain")
                                            .gender("Mezczyzna")
                                            .image("image")
                                            .build();

        assertThat(appUserService.saveUser(registerUserDTO).getRoles())
                .contains(new Role("NORMAL_USER"));
        assertThat(appUserService.saveUser(registerUserDTO).getPassword())
                .isEqualTo(bCryptPasswordEncoder.encode(registerUserDTO.getPassword()));
    }

}