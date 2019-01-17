package pl.powiescdosukcesu.book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface BookService {

	void saveFile(MultipartFile file, String title, String[] genres, byte[] image, String username) throws IOException;
	Book getFileById(long id);
	void deleteBook(Book file);
	List<Book> getFilesByKeyword(String keyword);
	List<Book> getFiles();
	List<Book> getFilesByGenres(String[] genres);
	List<Book> getFilesByDate(LocalDate date);
	void updateFile(Book file);
	List<String> loadImages();
	void deleteFileById(long id);
	void addComment(Book file,String comment);
}
