package rsp.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rsp.enums.Role;
import rsp.environment.Generator;
import rsp.model.User;
import rsp.service.interfaces.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    public void setUp(){
        user = sut.save(Generator.generateRandomUser());
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserService sut;

    String uri = "http://localhost:7797";

    @Test
    @WithMockUser(roles="ADMIN")
    public void getUser_getsPersistedUser(){
        try{
            assertNotNull(user.getId());
            assertDoesNotThrow(()->sut.findByUsername(user.getUsername()));
            mockMvc.perform(get(uri+"/user/" + user.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));

        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    @WithMockUser
    public void getCurrentUser_getsCurrentUser(){
        try{
            assertNotNull(user.getId());
            mockMvc.perform(get(uri+"/user/me")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isUnauthorized());

        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }


    @Test
    public void sanityCheck_connectionEstablished(){
        try{
            mockMvc.perform(get(uri+"/user/hi"))
                    .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("Exception encountered " + ex.getMessage());
        }
    }

    @Test
    @WithMockUser
    public void createUser_validUser_registerUser(){
        final User userToReg = Generator.generateRandomUser();
        try{
            mockMvc.perform(get(uri+"/user/hi"))
                    .andExpect(status().isOk());
            assertNotNull(user.getId());
            mockMvc.perform(post(uri+"/user/registration")
                    .content(mapper.writeValueAsString(userToReg))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isCreated());

        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }
}
