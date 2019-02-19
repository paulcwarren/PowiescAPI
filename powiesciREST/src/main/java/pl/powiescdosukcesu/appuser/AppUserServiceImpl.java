
package pl.powiescdosukcesu.appuser;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.powiescdosukcesu.security.UserPrincipal;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@DependsOn("securityConfig")
@Service("appUserService")
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRep;

    private final RoleRepository roleRep;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public AppUser getUser(long id) {
        Optional<AppUser> opt = userRep.findById(id);
        if (opt.isPresent()) {

            return opt.get();
        } else {
            throw new AppUserNotFoundException();
        }
    }

    @Override
    public AppUser getUser(String username) {

        Optional<AppUser> opt = userRep.findByUsername(username);
        return opt.get();

    }

    @Override
    public List<AppUser> getAllUsers() {

        List<AppUser> users = userRep.findAll();
        if (users.isEmpty()) {
            throw new AppUserNotFoundException("No users found");
        }


        return users;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        AppUser user = userRep.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        return UserPrincipal.create(user);
    }

    @Override
    public AppUser saveUser(@NonNull RegisterUserDTO registerUserDTO) {

        AppUser user = modelMapper.map(registerUserDTO, AppUser.class);

        if (registerUserDTO.getImage() != null)
            user.setImage(Base64.getDecoder().decode(registerUserDTO.getImage()));
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDTO.getPassword()));
        user.setRoles(Collections.singletonList(roleRep.findRoleByName("ROLE_NORMAL_USER")));
        userRep.save(user);

        return user;

    }

    @Override
    public void deleteUser(@NonNull AppUser user) {

        userRep.delete(user);

    }


}
