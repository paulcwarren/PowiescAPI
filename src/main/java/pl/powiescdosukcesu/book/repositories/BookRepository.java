package pl.powiescdosukcesu.book.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.projections.NoContentBookProjection;

import java.time.LocalDate;

@Repository
@RepositoryRestResource(excerptProjection = NoContentBookProjection.class)
@CrossOrigin
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"user", "genres", "comments"})
    Page<Book> findAll(Pageable pageable);

    @RestResource(path = "by-keyword")
    @Query("SELECT book FROM Book book JOIN book.user user "
            + "WHERE book.title LIKE %:keyword% OR book.description LIKE %:keyword%  OR user.username LIKE %:keyword%")
    @EntityGraph(attributePaths = {"user"})
    Page<Book> findFilesByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @RestResource(path = "by-createdDate")
    @EntityGraph(attributePaths = {"user"})
    Page<Book> findByCreatedDate(Pageable pageable,
                                 @Param("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @Query("SELECT book FROM Book book JOIN book.genres genres WHERE genres.name IN (?1)")
    @EntityGraph(attributePaths = {"user"})
    Page<Book> findByGenres(Pageable pageable, @Param("genres") String[] genreName);

    Book saveAndFlush(Book book);
}
