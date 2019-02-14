
package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> opt = userRep.findByUsername(username);

        if (opt.isPresent()) {
            AppUser user = opt.get();
            return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    @Override
    public AppUser saveUser(RegisterUserDTO registerUser) {

        AppUser user = AppUser.builder()
                .username(registerUser.getUsername())
                .password(bCryptPasswordEncoder.encode(registerUser.getPassword()))
                .email(registerUser.getEmail())
                .firstName(registerUser.getFirstName())
                .lastName(registerUser.getLastName())
                .gender(registerUser.getGender())
                .image(Base64.getDecoder().decode(registerUser.getImage()))
                .roles(Collections.singletonList(roleRep.findRoleByName("ROLE_NORMAL_USER")))
                .build();
        userRep.save(user);

        return user;

    }

    @Override
    @Async
    public void deleteUser(AppUser user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null");
        } else {
            userRep.delete(user);
        }


    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
