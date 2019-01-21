package pl.powiescdosukcesu.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {


    private MockMvc mockMvc;

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookController bookController;

    private JacksonTester<List<Book>> jsonBook;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new BookRestControllerAdvice())
                .build();

    }


    @Test
    public void shouldReturnBooks() throws Exception {
        // given
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre("hi"));
        given(bookService.getBooks())
                .willReturn(Arrays.asList(new Book("book", "admin".getBytes(), genres, "file".getBytes()),
                        new Book("book", "admin".getBytes(), genres, "file".getBytes())));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @WithMockUser(value = "admin", roles = "NORMAL_USER")
    public void whenUserDoesntOwnFileShouldReturnNotAuthorized(){

        //

    }


}
