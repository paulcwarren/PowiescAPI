package pl.powiescdosukcesu.unit.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.UserImageContentStore;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.appuser.services.AppUserService;
import pl.powiescdosukcesu.appuser.services.AppUserServiceImpl;
import pl.powiescdosukcesu.roles.Role;
import pl.powiescdosukcesu.roles.RoleRepository;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private RoleRepository roleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AppUserService appUserService;

    @Mock
    private UserImageContentStore imageContentStore;

    @Before
    public void setup(){

        appUserService = new AppUserServiceImpl(
                appUserRepository,
                modelMapper,
                passwordEncoder,
                roleRepository,
                imageContentStore);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserDoesntExistThenLoadByUsernameShouldTrowException(){

        String notExistingUsername = "notExistingUsername";
        Mockito.when(appUserRepository.findByUsername(notExistingUsername)).thenReturn(Optional.empty());

        appUserService.loadUserByUsername(notExistingUsername);
    }

    @Test
    public void whenUsernameExistsThenShouldReturnInstanceOfUserDetailsWithCorrectValues(){

        String existingUsername = "existingUsername";
        AppUser returnedUser = AppUser.builder()
                                .username("existingUsername")
                                .password("somePassword")
                                .email("email@slp.pl")
                                .roles(Set.of(new Role("ROLE_NORMAL_USER")))
                                .sex("MALE").build();

        Mockito.when(appUserRepository.findByUsername(existingUsername)).thenReturn(Optional.of(returnedUser));


        assertThat(appUserService.loadUserByUsername(existingUsername)).isInstanceOf(UserDetails.class);

        UserDetails user = appUserService.loadUserByUsername(existingUsername);

        assertThat(user.getUsername()).isEqualTo(returnedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(returnedUser.getPassword());
        assertThat(user.getAuthorities().size()).isEqualTo(returnedUser.getRoles().size());
    }

    @Test(expected = NullPointerException.class)
    public void whenDtoIsNullThenRegisterUserShouldThrowNPE() throws Exception{

        appUserService.registerUser(null,null);
    }
}
