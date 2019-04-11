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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.powiescdosukcesu.aop.RestControllerExceptionHandler;
import pl.powiescdosukcesu.appuser.*;
import pl.powiescdosukcesu.book.BookService;
import pl.powiescdosukcesu.security.JwtTokenProvider;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AppUserRestController.class)
public class AppUserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private RoleRepository roleRep;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AppUserRepository appUserRepository;



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

    @Test
    @WithUserDetails(value = "test",userDetailsServiceBeanName = "appUserService")
    public void onUserMeRequestShouldReturnInformationAboutUser(){

    }

}
