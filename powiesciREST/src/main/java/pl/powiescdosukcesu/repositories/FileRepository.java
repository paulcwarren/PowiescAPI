package pl.powiescdosukcesu.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.powiescdosukcesu.entities.FileEnt;

@Repository
public interface FileRepository extends CrudRepository<FileEnt, Long>, FileRepositoryCustom {

	@Query("SELECT file FROM FileEnt file JOIN file.user user "
			+ "WHERE file.title LIKE %:keyword% OR user.userName LIKE %:keyword%" )
	List<FileEnt> findFilesByKeyword(@Param("keyword") String keyword);

	List<FileEnt> findByCreatedDate(LocalDate date);

	@Query("SELECT file FROM FileEnt file JOIN file.genres genres WHERE genres.name IN (?1)")
	List<FileEnt> findByGenres(String[] genreName);
	
	@Query(value="SELECT image FROM images",
			nativeQuery=true)
	List<byte[]> loadImages();
}
