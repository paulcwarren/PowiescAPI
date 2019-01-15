
package pl.powiescdosukcesu.appuser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@DependsOn("securityConfig")
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleRepository roleRep;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//TODO possible to assign null to PowiesciUser
	@Override
	public AppUser getUser(long id) {

		Optional<AppUser> opt = userRep.findById(id);
		AppUser user = opt.get();
		return user;
	}

	@Override
	public AppUser getUser(String userName) {
		
		AppUser user= userRep.findByUsername(userName);
		 
		return user;
	}

	@Override
	public List<AppUser> getAllUsers() {

		Iterable<AppUser> iterableUser = userRep.findAll();
		List<AppUser> users = new ArrayList<>();

		iterableUser.forEach(u -> users.add(u));

		return users;

	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		AppUser user = userRep.findByUsername(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new User(user.getUserName(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	@Async
	public void saveUser(RegisterUserDTO formUser) {

		AppUser user = new AppUser();
		user.setUserName(formUser.getUserName());
		user.setPassword(passwordEncoder.encode(formUser.getPassword()));
		user.setFirstName(formUser.getFirstName());
		user.setLastName(formUser.getLastName());
		user.setEmail(formUser.getEmail());
		user.setGender(formUser.getGender());
		
		Base64 base = new Base64();
		String stringImage=formUser.getImage();
		
		user.setImage(base.decode(stringImage.getBytes()));
		
		user.setRoles(Arrays.asList(roleRep.findRoleByName("ROLE_NORMAL_USER")));

		userRep.save(user);

	}

	@Override
	@Async
	public void deleteUser(AppUser user) {
		
		userRep.delete(user);
		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
