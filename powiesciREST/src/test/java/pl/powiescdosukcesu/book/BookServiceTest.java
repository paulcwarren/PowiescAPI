package pl.powiescdosukcesu.book;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserServiceImpl;

import java.lang.reflect.InvocationTargetException;
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

    private final ArrayList<Book> books = new ArrayList<>();
    private Book book;

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

        bookService.saveBook(null, null);
	}

	@Test
    public void whenSavingBookThenShouldReturnCorrectTitle(){

	    //given
	    BookCreationDTO dto = new BookCreationDTO();
	    dto.setFile("random text");
	    dto.setGenres(Collections.singletonList("Romance"));
	    dto.setTitle("new poem");

	    given(appUserService.getUser("test")).willReturn(new AppUser("test","test","test","tester","test@hg.pl"));
	    given(genreRep.findGenreByName("Romance")).willReturn(new Genre("Romance"));

	    //then
	    assertThat(bookService.saveBook(dto, "test").getTitle()).isEqualTo("new poem");
    }

    @Test
    public void whenBookGetsUpdatedShouldReturnBookWithCorrectUser(){

	    //given
        given(bookRep.findOneByTitle("Harry Potter")).willReturn(Optional.of(book));
        given(bookRep.findById(1L)).willReturn(Optional.of(book));

        //then
        assertThat(bookService.updateBook(book)).isEqualTo(book);
        assertThat(bookService.updateBook(book).getUser()).isEqualTo(book.getUser());
    }

    @Test
    public void whenSearchingForFilesCreatedTodayShouldReturnListOfTwoElements(){

	    //given
        //given(bookRep.findByCreatedDate(LocalDate.now())).willReturn(books);

        //then
        //assertThat(bookService.getBooksByDate(LocalDate.now()).size()).isEqualTo(2);
    }

    @Test(expected = NullPointerException.class)
    public void whenBookIsNullWhenAddingCommentThenShouldThrowNullPinterException(){

	    bookService.addComment(null,"hejo");
    }

    @Test
	public void whenAddingCommentToBookShouldInrcreaseNumberOfCommentsOfFileByOne(){

	    //given
        given(bookRep.findById(1L)).willReturn(Optional.of(book));
        given(bookRep.findOneByTitle(book.getTitle())).willReturn(Optional.of(book));
        given(appUserService.getUser("test")).willReturn(new AppUser("test","test","test","tester","test@hg.pl"));
        AddCommentDTO dto = new AddCommentDTO();
        dto.setBookId(1);
        dto.setComment("cool one");

        //then
        assertThat(bookService.addComment(dto, "test").getComments().size()).isEqualTo(1);

	}

	//TODO
	@Test
    public void whenAddingRatingThenReturnRating() {

	    //given
        AddVoteDTO dto = new AddVoteDTO();
        dto.setBookId(1);
        dto.setRating(6);
        given(bookRep.findById(dto.getBookId())).willReturn(Optional.of(book));


        //then
	    bookService.addRating(new AddVoteDTO(7,book.getId()),book.getUser().getUsername());

	    assertThat(book.getRating()).isEqualTo(7);
    }


}
