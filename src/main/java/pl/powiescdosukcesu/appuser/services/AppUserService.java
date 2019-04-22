package pl.powiescdosukcesu.appuser.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;

import java.io.IOException;

public interface AppUserService extends UserDetailsService{

    AppUser registerUser(RegisterUserDTO dto, MultipartFile image) throws IOException;
}
