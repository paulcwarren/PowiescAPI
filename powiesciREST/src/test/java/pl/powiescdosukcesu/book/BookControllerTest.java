package pl.powiescdosukcesu.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.powiescdosukcesu.appuser.AppUser;
import pl.powiescdosukcesu.appuser.AppUserService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void contextLoads() {

    }

    @Test
    @WithMockUser(username = "notTheOwner", roles = {"NORMAL_USER"})
    public void whenUserNotOwnerOfBookThenCantDeleteOrUpdateBook() throws Exception {

        // given
        Set<Genre> genres = new HashSet<>();
        Book bookToDelete = new Book("book", "admin".getBytes(), genres, "file".getBytes());
        bookToDelete.setUser(new AppUser("test", "test", null, null, "test@as.pl"));
        this.mockMvc.perform(delete("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookToDelete)))
                .andExpect(status().isForbidden());

        this.mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookToDelete)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "owner", roles = {"NORMAL_USER"})
    public void whenUserOwnerOfBookThenCanDeleteAndUpdateBook() throws Exception {
        // given
        Set<Genre> genres = new HashSet<>();
        Book bookToDelete = new Book("book", "admin".getBytes(), genres, "file".getBytes());
        bookToDelete.setUser(new AppUser("owner", "test", null, null, "test@as.pl"));
        this.mockMvc.perform(delete("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookToDelete)))
                .andExpect(status().isOk());
        this.mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookToDelete)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenNoBooksFoundWithGivenKeywordThenShouldReturnNotFoundStatus() throws Exception {

        //given
        given(bookService.getBooksByKeyword("noMatch")).willThrow(BookNotFoundException.class);

        //then
        this.mockMvc.perform(get("/api/noMatch")).andExpect(status().isNotFound());
    }

    @Test
    public void whenBookNotValidThenSaveBookAndUpdateBookShouldReturnBadRequest() throws Exception {

        // given
        Set<Genre> genres = new HashSet<>();
        //MIN BOOK TITLE SIZE =4
        Book bookToSaveOrUpdate = new Book("b", "img".getBytes(), genres, "file".getBytes());
        bookToSaveOrUpdate.setUser(new AppUser("owner", "test", null, null, "test@as.pl"));

        //then
        this.mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookToSaveOrUpdate)))
                .andExpect(status().isBadRequest());
        //then
        this.mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookToSaveOrUpdate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUserNotAuthenticatedThenSaveUpdateDeleteBookShouldReturn401() throws Exception {

        Set<Genre> genres = new HashSet<>();

        Book randomBookForNotAuthenticatedUser = new Book("bdfhdfjfj", "img".getBytes(), genres, "file".getBytes());
        randomBookForNotAuthenticatedUser.setUser(new AppUser("owner", "test", null, null, "test@as.pl"));


        this.mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(randomBookForNotAuthenticatedUser)))
                .andExpect(status().isUnauthorized());

        this.mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(randomBookForNotAuthenticatedUser)))
                .andExpect(status().isUnauthorized());


        this.mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(randomBookForNotAuthenticatedUser)))
                .andExpect(status().isUnauthorized());
    }

}
