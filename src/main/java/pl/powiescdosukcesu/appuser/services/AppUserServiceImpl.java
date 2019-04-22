package pl.powiescdosukcesu.appuser.services;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserImage;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;
import pl.powiescdosukcesu.appuser.UserImageContentStore;
import pl.powiescdosukcesu.appuser.repositories.AppUserRepository;
import pl.powiescdosukcesu.roles.Role;
import pl.powiescdosukcesu.roles.RoleRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserImageContentStore imageContentStore;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository,
                              ModelMapper modelMapper,
                              BCryptPasswordEncoder passwordEncoder,
                              RoleRepository roleRepository,
                              UserImageContentStore imageContentStore) {

        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageContentStore = imageContentStore;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapRolesToAuthorities(user.getRoles()))
                .build();
    }

    @Override
    public AppUser registerUser(@NonNull RegisterUserDTO dto,
                                MultipartFile image) throws IOException {

        final String role = "ROLE_NORMAL_USER";

        AppUser user = modelMapper.map(dto, AppUser.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName(role)));

        if(image!=null && !image.isEmpty()) {
            AppUserImage userImage = new AppUserImage();
            userImage.setContentLength(image.getSize());
            userImage.setMimeType(image.getContentType());

            user.setUserImage(userImage);

            imageContentStore.setContent(userImage, image.getInputStream());
        }

        return appUserRepository.saveAndFlush(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {

        return roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
