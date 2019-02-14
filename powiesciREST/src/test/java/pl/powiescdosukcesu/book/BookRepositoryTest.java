package pl.powiescdosukcesu.book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.powiescdosukcesu.appuser.AppUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRep;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	
	@Before
	public void prepeareSomedData() {

		Set<Genre> genres = new HashSet<>();
		genres.add(new Genre("Horror"));
		genres.add(new Genre("Romance"));
		Book book = Book.builder()
				.id(1)
				.user(new AppUser("test", "test", null, null, "test@as.pl"))
				.title("Harry Potter")
				.backgroundImage("image".getBytes())
				.genres(genres)
				.file("file".getBytes())
				.build();
		bookRep.save(book);
		genres.add(new Genre("Comedy"));
		genres.add(new Genre("Romance"));
		Book book2 = Book.builder()
				.id(2)
				.user(new AppUser("admin", "admin", null, null, "admin@as.pl"))
				.title("Harry Potter")
				.backgroundImage("image".getBytes())
				.genres(genres)
				.file("file".getBytes())
				.build();

		
		bookRep.save(book2);
		
	}

	@Test
	public void shouldntCauseNPlus1Problem(){

		bookRep.findAll();
	}
	
	@Test
	public void whenSearchingForBookWithParamKeywordShouldReturnListOfOneElement() {
		
		assertThat(bookRep.findFilesByKeyword("test").size()).isEqualTo(1);
	}

	@Test
	public void whenSearchingForFilesCreatedTodayShouldReturnListOfAllFiles() {
		
		assertThat(bookRep.findByCreatedDate(LocalDate.parse(new SimpleDateFormat("YYYY-MM-DD").format(new Date()))).size())
				.isEqualTo(bookRep.count());
	}
	
	@Test
	public void whenSearchingForRomanceOrHorrorGenreBooksShouldReturnListOfTwoBooks() {
		assertThat(bookRep.findByGenres(new String[] {"Romance"}).size()).isEqualTo(2);
	}
	
	@Test
	public void whenSearchingForComedyGenreBooksShouldReturnListOfOneBook() {
		assertThat(bookRep.findByGenres(new String[] {"Comedy"}).size()).isEqualTo(1);
	}
	
	
	

}
