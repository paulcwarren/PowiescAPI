package pl.powiescdosukcesu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.powiescdosukcesu.appuser.AppUserRepository;
import pl.powiescdosukcesu.appuser.AppUserServiceImpl;
import pl.powiescdosukcesu.appuser.Role;
import pl.powiescdosukcesu.appuser.RoleRepository;

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

    }

}
