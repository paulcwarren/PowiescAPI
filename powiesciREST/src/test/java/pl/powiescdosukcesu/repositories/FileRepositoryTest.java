package pl.powiescdosukcesu.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.UserRepository;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookRepository;
import pl.powiescdosukcesu.book.Genre;
import pl.powiescdosukcesu.book.GenreRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class FileRepositoryTest {

	
	@Autowired
	private BookRepository fileRep;

	@SuppressWarnings("unused")
	@Autowired
	private UserRepository userRep;

	@Autowired
	private GenreRepository genreRep;
	

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	
	@Before
	public void prepeareSomedData() {

		String byteSrc="espifwefgio";
		Set<Genre> genres = new HashSet<Genre>();
		genres.add(new Genre("Horror"));
		Book file = new Book();
		

		file.setUser(new AppUser("test", "test", null, null, "test@as.pl"));
		file.setTitle("test");
		file.setFile(byteSrc.getBytes());
		file.setRating(2.0);
		file.setGenres(genres);

		fileRep.save(file);
		
	}
	
	@Test
	public void shouldReturnAllFiles() {
		List<Book> allFiles=new ArrayList<>();
		fileRep.findAll().iterator().forEachRemaining(f->allFiles.add(f));
		assertEquals(fileRep.count(), allFiles.size());
	}

	@Test
	public void findByKeywordShouldFilesShouldContainTheKeyword() {
		
		List<Book> files=fileRep.findFilesByKeyword("test");
		files.forEach(f->assertTrue(f.getTitle().equals("test") || f.getUser().getUserName().equals("test")));
	}
	
	@Test
	public void findByGenresFileEntShouldHaveTheGivenGenreInGenreList() {
		List<Genre> genres = new ArrayList<>();
		genres.add(genreRep.findGenreByName("Horror"));
		genres.add(genreRep.findGenreByName("Romans"));
		List<Book> files = fileRep.findByGenres(new String[] { "Horror", "Romans" });

		files.stream().map(f -> f.getGenres())
				.forEach(gL -> assertTrue(gL.contains(genreRep.findGenreByName("Horror"))));

	}

	@Test
	public void shouldSaveFileUserWithoutExceptions() {

	
		
		long currentNumberOfFiles=fileRep.count();

	

		Optional<Book> optFile = fileRep.findById(1l);
		Book getFile = optFile.get();
		Book file = new Book();

		file.setUser(new AppUser("test", "test", null, null, "test@as.pl"));
		file.setId(5);
		file.setTitle(getFile.getTitle());
		file.setFile(getFile.getFile());
		file.setRating(2.0);
		file.setGenres(getFile.getGenres());
		ExpectedException.none();

		fileRep.save(file);
		
		assertEquals(currentNumberOfFiles+1,fileRep.count());

	}

	
	@Test
	public void shouldChangeContentOfFileToTheDeclaredOne() {

		Optional<Book> optFile = fileRep.findById(1l);
		Book getFile = optFile.get();
		String stringFile = "thats some really good code";
		byte[] stringByte = stringFile.getBytes();
		Book file = new Book();

		file.setId(getFile.getId());

		file.setTitle("nowy tytul");

		file.setFile(stringByte);
		file.setGenres(getFile.getGenres());
		ExpectedException.none();
		fileRep.updateFile(file);
		
		Book doneFile = fileRep.findById(file.getId()).get();

		assertEquals("thats some really good code", doneFile.getContent());
	}
	
	
	
	@Test
	public void shouldRetrieveFilesByDate() {
		LocalDate date = LocalDate.now();
		
		assertNotNull(fileRep.findByCreatedDate(date));
	}
	
	
	
	
	

}
