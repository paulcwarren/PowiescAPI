package pl.powiescdosukcesu.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom {

	@Query("SELECT file FROM Book file JOIN file.user user "
			+ "WHERE file.title LIKE %:keyword% OR user.userName LIKE %:keyword%" )
	List<Book> findFilesByKeyword(@Param("keyword") String keyword);

	List<Book> findByCreatedDate(LocalDate date);

	@Query("SELECT file FROM Book file JOIN file.genres genres WHERE genres.name IN (?1)")
	List<Book> findByGenres(String[] genreName);
	
	@Query(value="SELECT image FROM images",
			nativeQuery=true)
	List<byte[]> loadImages();
	
	
}
