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

import pl.powiescdosukcesu.entities.Comment;
import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.repositories.FileRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class FileServiceTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public FileService fileService() {
			return new FileServiceImpl();
		}
	}

	@Autowired
	private FileService fileService;

	
	@Autowired
	private FileRepository fileRepository;

	
	@Test
	public void shouldBase64EncodeByteImages() {
		
		List<String> images=fileService.loadImages();
		ExpectedException.none();
		assertFalse(images.isEmpty());
	}
	
	@Test
	public void shouldDeleteFile() {
		
		long currentNumberOfFIles=fileRepository.count();
		FileEnt fileToGetDeleted=fileService.getFileById(528l);
		fileToGetDeleted.setUser(null);
		fileService.deleteBook(fileToGetDeleted);
		assertEquals(currentNumberOfFIles-1, fileRepository.count());
	}
	
	@Test
	public void shouldLoadFileWithComments(){
		
		FileEnt file = fileService.getFileById(1);
		Comment comment = new Comment("Wow thats a great story", file.getUser());
		
		file.addComment(comment);
		
		fileService.updateFile(file);
		
		assertNotNull(fileService.getFileById(1).getComments());
		
	}
	

}
