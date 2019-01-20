package pl.powiescdosukcesu.repositories;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserRepository;
import pl.powiescdosukcesu.appuser.RoleRepository;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private AppUserRepository userRep;

	@Autowired
	private RoleRepository roleRep;

	
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void idOfUserWithUsernametestShouldEqual1() {

		AppUser user = userRep.findByUsername("test");

		assertEquals("test", user.getUserName());
	}

	@Test
	public void shouldSaveUserWithoutExceptions() {

		long currentNumberOfUsers=userRep.count();
		AppUser user = new AppUser();
		user.setUserName("newUser");
		user.setPassword(passwordEncoder().encode("pass"));
		user.setFirstName("janek2");
		user.setLastName("kowalski");
		user.setEmail("cos@s.pl");

		user.setGender("M");
		user.setRoles(Collections.singletonList(roleRep.findRoleByName("ROLE_EMPLOYEE")));

		ExpectedException.none();
		userRep.save(user);

		assertEquals(currentNumberOfUsers+1, userRep.count());
	}
	

}
