package pl.powiescdosukcesu.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import pl.powiescdosukcesu.dtos.RegisterUserDTO;
import pl.powiescdosukcesu.entities.PowiesciUser;

public interface UserService extends UserDetailsService {

	PowiesciUser getUser(long id);

	PowiesciUser getUser(String userName);

	List<PowiesciUser> getAllUsers();

	void saveUser(RegisterUserDTO user);
	
	void deleteUser(PowiesciUser user);

}
