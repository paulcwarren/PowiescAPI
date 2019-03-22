package pl.powiescdosukcesu.book;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.config.JPAConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes={JPAConfig.class})
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRep;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	
	@Before
	public void prepareSomeData() {

		Set<Genre> genres = new HashSet<>();
		Genre horror = new Genre("Horror");
		genres.add(horror);
		Genre romance = new Genre("Romance");
		genres.add(romance);
		Book book = Book.builder()
				.id(1)
				.user(new AppUser("test", "test", null, null, "test@as.pl"))
				.title("Harry Potter")
				.backgroundImage("image".getBytes())
				.genres(genres)
				.file("file".getBytes())
				.build();

		bookRep.save(book);


		HashSet<Genre> genres2 = new HashSet<>(Set.of(new Genre("Comedy")));
		Book book2 = Book.builder()
				.id(2)
				.user(new AppUser("admin", "admin", null, null, "admin@as.pl"))
				.title("Barry Sanders and Don test")
				.backgroundImage("image2".getBytes())
				.genres(genres2)
				.file("file2".getBytes())
				.build();


		bookRep.save(book2);
		
	}

	@Test
	public void contextLoads(){}

	@Test
	public void shouldntCauseNPlus1Problem(){

		bookRep.findAll();
	}
	
	@Test
	public void whenSearchingForBookWithParamKeywordShouldReturnListOfOneElement() {

        assertThat(bookRep.findFilesByKeyword(null,"test").getTotalElements()).isEqualTo(bookRep.count());
	}


    @Test
	public void whenSearchingForFilesCreatedTodayShouldReturnListOfAllFiles() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String string = LocalDate.now().format(formatter);
		LocalDate date = LocalDate.parse(string, formatter);
		assertThat(bookRep.findByCreatedDate(null,date)
				.getTotalElements())
				.isEqualTo(bookRep.count());
	}
	
	@Test
	public void whenSearchingForRomanceOrHorrorGenreBooksShouldReturnListOfTwoBooks() {
		assertThat(bookRep.findByGenres(new String[] {"Romance"}).size()).isEqualTo(1);
	}
	
	@Test
	public void whenSearchingForComedyGenreBooksShouldReturnListOfOneBook() {
		assertThat(bookRep.findByGenres(new String[] {"Comedy"}).size()).isEqualTo(1);
	}
	
	
	

}
