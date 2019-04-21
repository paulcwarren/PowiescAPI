package pl.powiescdosukcesu.book.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.genre.Genre;

import java.time.LocalDate;
import java.util.Set;

@Projection(name = "noContent", types = {Book.class})
public interface NoContentBookProjection {

    String getTitle();

    Set<Genre> getGenres();

    @Value("#{target.user.username}")
    String getUsername();

    LocalDate getCreatedDate();

    Double getRating();
}
