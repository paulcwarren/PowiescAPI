package pl.powiescdosukcesu.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import pl.powiescdosukcesu.book.Comment;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookRepository;
import pl.powiescdosukcesu.book.BookService;
import pl.powiescdosukcesu.book.BookServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class FileServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public BookService bookService() {
			return new BookServiceImpl();
		}
	}

	@Autowired
	private BookService bookService;

	
	@Autowired
	private BookRepository bookRepository;

	
	@Test
	public void shouldBase64EncodeByteImages() {
		
		List<String> images=bookService.loadImages();
		ExpectedException.none();
		assertFalse(images.isEmpty());
	}
	
	@Test
	public void shouldDeleteFile() {
		
		long currentNumberOfFIles=bookRepository.count();
		Book fileToGetDeleted=bookService.getFileById(528l);
		fileToGetDeleted.setUser(null);
		bookService.deleteBook(fileToGetDeleted);
		assertEquals(currentNumberOfFIles-1, bookRepository.count());
	}
	
	@Test
	public void shouldLoadFileWithComments(){
		
		Book file = bookService.getFileById(1);
		Comment comment = new Comment("Wow thats a great story", file.getUser());
		
		file.addComment(comment);
		
		bookService.updateFile(file);
		
		assertNotNull(bookService.getFileById(1).getComments());
		
	}
	

}
