package pl.powiescdosukcesu.genre;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre,Short> {
    Genre findByName(String name);
}
