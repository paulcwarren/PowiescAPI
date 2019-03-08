package pl.powiescdosukcesu.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

	@Query("SELECT book FROM Book book JOIN book.user user "
            + "WHERE book.title LIKE %:keyword% OR book.description LIKE %:keyword%  OR user.username LIKE %:keyword%")
    Page<Book> findFilesByKeyword(Pageable pageable, @Param("keyword") String keyword);

	List<Book> findByCreatedDate(LocalDate date);

	@Query("SELECT book FROM Book book JOIN book.genres genres WHERE genres.name IN (?1)")
	List<Book> findByGenres(String[] genreName);
	
	@Query(value="SELECT image FROM images",
			nativeQuery=true)
	List<byte[]> loadImages();


    @Query(value = "INSERT INTO files_votes(name) values (?1)", nativeQuery = true)
    void addRating(double rating);

	@EntityGraph(attributePaths = {"user","genres"})
    Page<Book> findAll(Pageable pageable);

    Optional<Book> findOneByTitle(String title);
    
}
