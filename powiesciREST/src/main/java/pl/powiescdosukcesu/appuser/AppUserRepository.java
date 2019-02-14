package pl.powiescdosukcesu.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {


	Optional<AppUser> findByUsername(String userName);
	Optional<AppUser> findByEmail(String mail);
	Optional<AppUser> findByFirstName(String firstName);
	Optional<AppUser> findByLastName(String lastName);
	List<AppUser> findAll();

}
