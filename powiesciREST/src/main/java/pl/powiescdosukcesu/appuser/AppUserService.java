package pl.powiescdosukcesu.appuser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface AppUserService extends UserDetailsService{

	AppUser getUser(long id);

	AppUser getUser(String userName);

	List<AppUser> getAllUsers();

	void saveUser(RegisterUserDTO user);

	void deleteUser(AppUser user);

}
