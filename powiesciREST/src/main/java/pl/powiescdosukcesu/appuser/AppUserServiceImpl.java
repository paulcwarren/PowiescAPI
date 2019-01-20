
package pl.powiescdosukcesu.appuser;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
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
		AppUser user = opt.get();
		return user;
	}

	@Override
	public AppUser getUser(String userName) {

		AppUser user = userRep.findByUsername(userName);

		return user;
	}

	@Override
	public List<AppUser> getAllUsers() {

		Iterable<AppUser> iterableUser = userRep.findAll();
		List<AppUser> users = new ArrayList<>();

		iterableUser.forEach(users::add);

		return users;

	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		AppUser user = userRep.findByUsername(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	@Async
	public void saveUser(RegisterUserDTO formUser) {

		AppUser user = new AppUser();
		user.setUserName(formUser.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(formUser.getPassword()));
		user.setFirstName(formUser.getFirstName());
		user.setLastName(formUser.getLastName());
		user.setEmail(formUser.getEmail());
		user.setGender(formUser.getGender());

		Base64 base = new Base64();
		String stringImage = formUser.getImage();

		user.setImage(base.decode(stringImage.getBytes()));

		user.setRoles(Collections.singletonList(roleRep.findRoleByName("ROLE_NORMAL_USER")));

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
