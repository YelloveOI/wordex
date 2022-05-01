package rsp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import rsp.environment.Generator;
import rsp.exception.NotFoundException;
import rsp.model.User;
import rsp.rest.dto.LoginRequest;
import rsp.service.interfaces.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserService sut;

    private String rawPassword;

    String uri = "http://localhost:7797";

    @BeforeEach
    public void setUp(){
        try{
            User generatedU = Generator.generateRandomUser();
            user = sut.register(generatedU);
            rawPassword = generatedU.getPassword();
        }
        catch(Exception e){
            fail("Failed to set up tests " + e.getMessage());
        }
    }


    @Test
    @WithAnonymousUser
    public void createUser_validUser_registersUserJwtTokenInResponse(){
        final User userToReg = Generator.generateRandomUser();
        try{
            assertThrows(NotFoundException.class, ()->sut.findByUsername(userToReg.getUsername()));
            MvcResult result = mockMvc.perform(post(uri+"/auth/registration")
                            .content(mapper.writeValueAsString(userToReg))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
            assertDoesNotThrow(()->sut.findByUsername(userToReg.getUsername()));
            assertTrue(new JSONObject(result.getResponse().getContentAsString()).has("accessToken"));
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    @WithAnonymousUser
    public void createUser_invalidUser_badRequestResponse(){
        final User userToReg = Generator.generateRandomUser();
        userToReg.setEmail("");
        try{
            assertThrows(NotFoundException.class, ()->sut.findByUsername(userToReg.getUsername()));
            mockMvc.perform(post(uri+"/auth/registration")
                            .content(mapper.writeValueAsString(userToReg))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    public void login_invalidUser_badRequestResponse(){
        LoginRequest req = new LoginRequest();
        req.setUsername(user.getUsername());
        req.setPassword("+ěščřdýFFčš23");
        try{
            MvcResult result = mockMvc.perform(post(uri+"/auth/login")
                            .content(mapper.writeValueAsString(req))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andReturn();
            assertTrue(new JSONObject(result.getResponse().getContentAsString()).get("message").equals("Bad credentials"));
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    public void login_validUser_OkResponseWithToken(){
        LoginRequest req = new LoginRequest();
        req.setUsername(user.getUsername());
        req.setPassword(rawPassword);
        assertDoesNotThrow(()->sut.findByUsername(req.getUsername()));
        try{
            MvcResult result = mockMvc.perform(post(uri+"/auth/login")
                            .content(mapper.writeValueAsString(req))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
            assertTrue(new JSONObject(result.getResponse().getContentAsString()).has("accessToken"));
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

}
