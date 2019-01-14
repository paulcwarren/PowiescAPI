package pl.powiescdosukcesu.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.powiescdosukcesu.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	@Query("SELECT role FROM Role role WHERE role.name = ?1")
	Role findRoleByName(String name);
}
