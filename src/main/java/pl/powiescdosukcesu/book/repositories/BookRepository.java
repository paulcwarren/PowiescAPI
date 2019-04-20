package pl.powiescdosukcesu.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import pl.powiescdosukcesu.book.projections.NoContentBookProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
@RepositoryRestResource(excerptProjection = NoContentBookProjection.class)
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @RestResource(path = "by-keyword")
    @Query("SELECT book FROM Book book JOIN book.user user "
            + "WHERE book.title LIKE %:keyword% OR book.description LIKE %:keyword%  OR user.username LIKE %:keyword%")
    Page<Book> findFilesByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @RestResource(path = "by-createdDate")
    Page<Book> findByCreatedDate(Pageable pageable, LocalDate date);


    @Query("SELECT book FROM Book book JOIN book.genres genres WHERE genres.name IN (?1)")
    List<Book> findByGenres(String[] genreName);

    @EntityGraph(attributePaths = {"user", "genres", "comments"})
    Page<Book> findAll(Pageable pageable);

    Optional<Book> findOneByTitle(String title);

}
