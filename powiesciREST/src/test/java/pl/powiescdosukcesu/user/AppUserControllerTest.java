package pl.powiescdosukcesu.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.WebApplicationContext;
import pl.powiescdosukcesu.aop.RestControllerExceptionHandler;
import pl.powiescdosukcesu.appuser.AppUserRestController;
import pl.powiescdosukcesu.appuser.AppUserService;
import pl.powiescdosukcesu.appuser.RegisterUserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AppUserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserRestController appUserRestController;



    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup()
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
                .gender("seg")
                .email("invalidemail")
                .build();


        this.mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserDTO)))
                .andExpect(status().isBadRequest());
    }
}
