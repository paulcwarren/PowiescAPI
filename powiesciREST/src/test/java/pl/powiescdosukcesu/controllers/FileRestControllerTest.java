package pl.powiescdosukcesu.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.TreeSet;

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

import pl.powiescdosukcesu.entities.FileEnt;
import pl.powiescdosukcesu.entities.Genre;
import pl.powiescdosukcesu.repositories.FileRepository;
import pl.powiescdosukcesu.repositories.GenreRepository;
import pl.powiescdosukcesu.services.FileService;
import pl.powiescdosukcesu.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FileRestController.class)
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRestControllerTest {

	@Autowired
	private WebApplicationContext context;
	@MockBean
	private FileRepository fileRepository;
	
	@MockBean
	private FileService fileService;
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
		Set<Genre> tempset = new TreeSet<>();
		tempset.add(new Genre(1, "hi"));
		FileEnt file = new FileEnt("hi",temp.getBytes(),tempset, temp.getBytes());
		
		String json = null;
		json=objectMapper.writeValueAsString(file);
		System.out.println(json);
		this.mvc
				.perform(delete("/books").with(user("wrongUser").roles("NORMAL_USER")).content(json).accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
	}

}
