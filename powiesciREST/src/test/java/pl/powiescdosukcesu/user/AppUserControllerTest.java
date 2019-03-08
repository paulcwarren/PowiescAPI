package pl.powiescdosukcesu.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.powiescdosukcesu.aop.RestControllerExceptionHandler;
import pl.powiescdosukcesu.appuser.AppUserRestController;
import pl.powiescdosukcesu.appuser.AppUserService;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AppUserControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private AppUserRestController appUserRestController;

    @Mock
    private AppUserService appUserService;



    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(appUserRestController)
                .setControllerAdvice(RestControllerExceptionHandler.class)
                .build();
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void invalidRegisterUserShouldReturnBadRequest() throws Exception {


        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                .username("")
                .password("test")
                .matchingPassword("test")
                .sex("seg")
                .email("invalidemail")
                .build();


        String content = objectMapper.writeValueAsString(registerUserDTO);
        System.out.println(content);
        this.mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());


    }

}
