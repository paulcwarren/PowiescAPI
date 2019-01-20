package pl.powiescdosukcesu.book;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.powiescdosukcesu.appuser.AppUserServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Mock
	private BookRepository bookRep;

	@Mock
	private GenreRepository genreRep;

	@Mock
	private AppUserServiceImpl appUserService;

	@InjectMocks
	private BookServiceImpl bookService;

	private List<Book> books = new ArrayList<>();

	@Before
	public void setup() {
		
		
		Set<Genre> genres = new HashSet<>();
		genres.add(new Genre("Komedia"));
		genres.add(new Genre("Horror"));
		Book book = Book.builder()
				.id(1)
				.title("Harry Potter")
				.backgroundImage("image".getBytes())
				.file("file".getBytes())
				.genres(genres)
				.build();

		Book book2 = Book.builder()
				.id(2)
				.title("James Bond")
				.backgroundImage("image".getBytes())
				.file("file".getBytes())
				.genres(genres)
				.build();
		books.add(book);
		books.add(book2);
		
	
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void shouldLoadTwoFiles() {
		Iterable<Book> iter = books;
		
		//given
		given(bookRep.findAll()).willReturn(iter);
		
		//then
		assertThat(bookService.getFiles().size()).isEqualTo(2);

	}
	
	@Test(expected=BookNotFoundException.class)
	public void whenBookIdNotFoundShouldThrowBookNotFoundException() {
		
		//given
		given(bookRep.findById(3L)).willReturn(Optional.empty());
		
		//then
		bookService.getFileById(3);
	}
	
	@Test(expected=BookNotFoundException.class)
	public void whenNoFilesWereFoundWithGivenGenreThenShouldThrowBookNotFoundException() {
		
		//given
		given(bookRep.findByGenres(new String[] {"Comedy"})).willReturn(Collections.emptyList());
		
		//then
		bookService.getFilesByGenres(new String[] {"Comedy"});
	}
	
	


}
