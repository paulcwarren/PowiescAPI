package pl.powiescdosukcesu.appuser.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.projections.NoBooksProjection;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "users", itemResourceRel = "user", excerptProjection = NoBooksProjection.class)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @EntityGraph(attributePaths = {"books", "roles"})
    Page<AppUser> findAll(Pageable pageable);

    boolean existsByUsername(String username);

    @RestResource(exported = false)
    AppUser save(AppUser user);

    @RestResource(exported = false)
    AppUser saveAndFlush(AppUser user);

    Optional<AppUser> findByUsername(String username);
}