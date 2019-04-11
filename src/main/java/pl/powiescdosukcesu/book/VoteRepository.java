package pl.powiescdosukcesu.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.powiescdosukcesu.appuser.AppUser;

import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    Optional<Vote> findByUserAndBook(AppUser user, Book book);
}
