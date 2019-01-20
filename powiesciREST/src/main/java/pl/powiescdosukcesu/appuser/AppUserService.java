package pl.powiescdosukcesu.appuser;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AppUserService extends UserDetailsService{

	AppUser getUser(long id);

	AppUser getUser(String userName);

	List<AppUser> getAllUsers();

	void saveUser(RegisterUserDTO user);

	void deleteUser(AppUser user);

}
