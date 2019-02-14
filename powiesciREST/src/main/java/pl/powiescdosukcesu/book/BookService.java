package pl.powiescdosukcesu.book;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface BookService {

	void saveFile(MultipartFile file, String title, String[] genres, byte[] image, String username) throws IOException;
	Book getBookById(long id);
	void deleteBook(Book file);
	List<Book> getBooksByKeyword(String keyword);
	List<Book> getBooks();
	List<Book> getBooksByGenres(String[] genres);
	List<Book> getBooksByDate(LocalDate localDate);
	Book updateBook(Book book);
	List<String> loadImages();
	void deleteBookById(long id);
	Book addComment(Book book,String comment);
	Book saveBook(Book book);

    Book getBookByTitle(String bookTitle);
}
