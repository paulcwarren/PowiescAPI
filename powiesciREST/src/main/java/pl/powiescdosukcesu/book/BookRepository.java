package pl.powiescdosukcesu.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom {

	@Query("SELECT book FROM Book book JOIN book.user user "
			+ "WHERE book.title LIKE %:keyword% OR user.username LIKE %:keyword%" )
	List<Book> findFilesByKeyword(@Param("keyword") String keyword);

	List<Book> findByCreatedDate(LocalDate date);

	@Query("SELECT book FROM Book book JOIN book.genres genres WHERE genres.name IN (?1)")
	List<Book> findByGenres(String[] genreName);
	
	@Query(value="SELECT image FROM images",
			nativeQuery=true)
	List<byte[]> loadImages();

	List<Book> findAll();

    Optional<Book> findOneByTitle(String title);


}
