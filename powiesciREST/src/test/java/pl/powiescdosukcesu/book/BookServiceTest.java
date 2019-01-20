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

import java.time.LocalDate;
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
    private Book book,book2;
	@Before
	public void setup() {
		
		
		Set<Genre> genres = new HashSet<>();
		genres.add(new Genre("Komedia"));
		genres.add(new Genre("Horror"));
		book = Book.builder()
				.id(1)
				.title("Harry Potter")
				.backgroundImage("image".getBytes())
				.file("file".getBytes())
				.genres(genres)
				.build();

		book2 = Book.builder()
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
		assertThat(bookService.getBooks().size()).isEqualTo(2);

	}
	
	@Test(expected=BookNotFoundException.class)
	public void whenBookIdNotFoundShouldThrowBookNotFoundException() {
		
		//given
		given(bookRep.findById(3L)).willReturn(Optional.empty());
		
		//then
		bookService.getBookById(3);
	}
	
	@Test(expected=BookNotFoundException.class)
	public void whenNoFilesWereFoundWithGivenGenreThenShouldThrowBookNotFoundException() {
		
		//given
		given(bookRep.findByGenres(new String[] {"Comedy"})).willReturn(Collections.emptyList());
		
		//then
		bookService.getBooksByGenres(new String[] {"Comedy"});
	}

	@Test(expected = NullPointerException.class)
	public void whenPassingNullToSaveBookShouldThrowNullPointerException(){

		bookService.saveBook(null);
	}

	@Test
    public void shouldReturnSavedBook(){
	    assertThat(bookService.saveBook(book)).isEqualTo(book);
    }

    @Test
    public void whenBookGetsUpdatedShouldReturnBookWithCorrectUser(){

	    //given
        given(bookRep.findById(1L)).willReturn(Optional.of(book));

        //then
        assertThat(bookService.updateBook(book)).isEqualTo(book);
        assertThat(bookService.updateBook(book).getUser()).isEqualTo(book.getUser());
    }

    @Test
    public void whenSearchingForFilesCreatedTodayShouldReturnListOfTwoElements(){

	    //given
        given(bookRep.findByCreatedDate(LocalDate.now())).willReturn(books);

        //then
        assertThat(bookService.getBooksByDate(LocalDate.now()).size()).isEqualTo(2);
    }


}
