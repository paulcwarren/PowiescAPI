package pl.powiescdosukcesu.book;

import java.time.LocalDate;
import java.util.List;

import pl.powiescdosukcesu.appuser.AppUser;

public interface BookService {

	void saveFile(Book file,AppUser user);
	Book getFileById(long id);
	void deleteBook(Book file);
	List<Book> getFilesByKeyword(String keyword);
	List<Book> getFiles();
	List<Book> getFilesByGenres(String[] genres);
	List<Book> getFilesByDate(LocalDate date);
	void updateFile(Book file);
	List<String> loadImages();
	void deleteFileById(long id);
}
