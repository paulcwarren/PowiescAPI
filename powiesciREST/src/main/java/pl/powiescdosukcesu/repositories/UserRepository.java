package pl.powiescdosukcesu.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.powiescdosukcesu.entities.PowiesciUser;

@Repository
public interface UserRepository extends CrudRepository<PowiesciUser, Long> {

	@Query("SELECT user FROM PowiesciUser user WHERE user.userName = ?1")
	PowiesciUser findByUsername(String userName);

}
