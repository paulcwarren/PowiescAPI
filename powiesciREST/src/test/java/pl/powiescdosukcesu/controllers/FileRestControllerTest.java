package pl.powiescdosukcesu.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.powiescdosukcesu.appuser.UserService;
import pl.powiescdosukcesu.book.Book;
import pl.powiescdosukcesu.book.BookController;
import pl.powiescdosukcesu.book.BookRepository;
import pl.powiescdosukcesu.book.BookService;
import pl.powiescdosukcesu.book.Genre;
import pl.powiescdosukcesu.book.GenreRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRestControllerTest {

	@Autowired
	private WebApplicationContext context;
	@MockBean
	private BookRepository bookRepository;
	
	@MockBean
	private BookService bookService;
	@MockBean
	private UserService userService;
	@MockBean
	private GenreRepository genreRepository;
	@MockBean
	private AuthenticationEntryPoint authEntryPoint;
	private MockMvc mvc;

	@Before
	public void setup() {

		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void contextLoads() {

	}
	
	@Test
	public void shouldReturnFilesAsJsonWithoutErrors() throws Exception {
		mvc.perform(get("/books")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void whenFileNotFoundControllerShouldRespondWithNotFound() throws Exception {
		mvc.perform(get("/books/id/4")).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	// TODO
	@Test
	public void controllerShouldPreAuthorize() throws Exception {

		String temp = "image";
		ObjectMapper objectMapper = new ObjectMapper();
		Set<Genre> tempset = new HashSet<>();
		tempset.add(new Genre("hi"));
		Book file = new Book("hi",temp.getBytes(),tempset, temp.getBytes());
		
		String json = null;
		json=objectMapper.writeValueAsString(file);
		System.out.println(json);
		this.mvc
				.perform(delete("/books").with(user("wrongUser").roles("NORMAL_USER")).content(json).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
	}

}
