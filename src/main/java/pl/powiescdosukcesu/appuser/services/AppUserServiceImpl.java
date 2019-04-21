package pl.powiescdosukcesu.appuser.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.roles.Role;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return new User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){

        return roles.stream().map(role->role.getName()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
