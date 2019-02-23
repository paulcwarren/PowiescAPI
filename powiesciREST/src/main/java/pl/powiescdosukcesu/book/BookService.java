package pl.powiescdosukcesu.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.powiescdosukcesu.security.UserPrincipal;

import java.time.LocalDate;
import java.util.List;


public interface BookService {

	Book getBookById(long id);
	void deleteBook(Book file);
	List<Book> getBooksByKeyword(String keyword);

	Page<BookShortInfoDTO> getBooks(Pageable pageable);
	List<Book> getBooksByGenres(String[] genres);
	List<Book> getBooksByDate(LocalDate localDate);
	Book updateBook(Book book);
	List<String> loadImages();

	Book addComment(AddCommentDTO addCommentDTO, String username);

    Book saveBook(BookCreationDTO book, UserPrincipal user);

    FullBookInfoDTO getBookByTitle(String bookTitle);
}
