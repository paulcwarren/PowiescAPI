
package pl.powiescdosukcesu.appuser;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

        Optional<AppUser> opt = userRep.findByUsername(username);

        if (opt.isPresent()) {
            AppUser user = opt.get();
            return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    @Override
    public AppUser saveUser(@NonNull RegisterUserDTO registerUserDTO) {

        AppUser user = modelMapper.map(registerUserDTO, AppUser.class);
        user.setImage(Base64.getDecoder().decode(registerUserDTO.getImage()));
        user.setRoles(Collections.singletonList(roleRep.findRoleByName("ROLE_NORMAL_USER")));
        userRep.save(user);

        return user;

    }

    @Override
    public void deleteUser(@NonNull AppUser user) {

        userRep.delete(user);

    }

    protected Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
