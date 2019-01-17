package pl.powiescdosukcesu.appuser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

	@Query("SELECT user FROM AppUser user WHERE user.userName = ?1")
	AppUser findByUsername(String userName);

}
