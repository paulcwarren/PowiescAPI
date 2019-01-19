package pl.powiescdosukcesu.book;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import pl.powiescdosukcesu.appuser.AppUserServiceImpl;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookNotFoundException;
import pl.powiescdosukcesu.book.BookRepository;
import pl.powiescdosukcesu.book.BookServiceImpl;
import pl.powiescdosukcesu.book.Genre;
import pl.powiescdosukcesu.book.GenreRepository;

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

	private Book book, book2;
	private List<Book> books = new ArrayList<>();

	@Before
	public void setup() {
		
		
		Set<Genre> genres = new HashSet<>();
		genres.add(new Genre("Komedia"));
		genres.add(new Genre("Horror"));
		book = Book.builder()
				.id(1)
				.title("Harry Potter")
				.backgroundImage(new String("image").getBytes())
				.file(new String("file").getBytes())
				.genres(genres)
				.build();
	
		book2 = Book.builder()
				.id(2)
				.title("James Bond")
				.backgroundImage(new String("image").getBytes())
				.file(new String("file").getBytes())
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
		given(bookRep.findById(3l)).willReturn(Optional.empty());
		
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
