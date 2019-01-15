package pl.powiescdosukcesu.appuser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	AppUser getUser(long id);

	AppUser getUser(String userName);

	List<AppUser> getAllUsers();

	void saveUser(RegisterUserDTO user);
	
	void deleteUser(AppUser user);

}
