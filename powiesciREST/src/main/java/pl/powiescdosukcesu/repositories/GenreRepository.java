package pl.powiescdosukcesu.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.powiescdosukcesu.entities.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Long> {

	@Query("SELECT g FROM Genre g WHERE g.name = ?1")
	Genre findGenreByName(String name);
}
