package pl.powiescdosukcesu.integration.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.appuser.UserImageContentStore;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.appuser.services.AppUserService;
import pl.powiescdosukcesu.appuser.services.AppUserServiceImpl;
import pl.powiescdosukcesu.config.JPAConfig;
import pl.powiescdosukcesu.roles.Role;
import pl.powiescdosukcesu.roles.RoleRepository;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = JPAConfig.class)
public class AppUserServiceTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AppUserService appUserService;

    @Mock
    private UserImageContentStore imageContentStore;

    @Before
    public void setup() {

        appUserService = new AppUserServiceImpl(
                appUserRepository,
                modelMapper,
                passwordEncoder,
                roleRepository,
                imageContentStore);

        roleRepository.save(new Role("ROLE_NORMAL_USER"));
    }

    @Test
    public void whenGivenDtoAndNullImageThenRegisterUserShouldReturnUserWithEncryptedPasswordAndCorrectFields()
            throws IOException {

        String username = "username";
        RegisterUserDTO dto = RegisterUserDTO.builder()
                .username(username)
                .password("password")
                .matchingPassword("password")
                .email("email@dm.pl")
                .sex("MALE").build();

        AppUser user = appUserService.registerUser(dto, null);

        assertThat(passwordEncoder.matches(dto.getPassword(), user.getPassword())).isTrue();
        assertThat(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()).contains("ROLE_NORMAL_USER"));
    }

    //TODO
    @Test
    public void whenGivenDtoAndImageThenRegisterUserShouldReturnUserWithImageSet() {

    }
}
