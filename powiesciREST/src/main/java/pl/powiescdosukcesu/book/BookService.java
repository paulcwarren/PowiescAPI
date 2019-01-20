package pl.powiescdosukcesu.book;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface BookService {

	void saveFile(MultipartFile file, String title, String[] genres, byte[] image, String username) throws IOException;
	Book getFileById(long id);
	void deleteBook(Book file);
	List<Book> getFilesByKeyword(String keyword);
	List<Book> getFiles();
	List<Book> getFilesByGenres(String[] genres);
	List<Book> getFilesByDate(String date);
	void updateFile(Book file);
	List<String> loadImages();
	void deleteBookById(long id);
	void addComment(Book file,String comment);
}
