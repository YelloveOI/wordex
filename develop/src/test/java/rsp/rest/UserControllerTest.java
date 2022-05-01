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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.test.context.support.WithUserDetails;
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
import rsp.exception.NotFoundException;
import rsp.model.User;
import rsp.security.SecurityConstants;
import rsp.security.SecurityUtils;
import rsp.security.UserDetails;
import rsp.service.interfaces.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    private String accessToken;



    @BeforeEach
    public void setUp(){
        try{
            User generatedU = Generator.generateRandomUser();
            generatedU.setUsername("userName69");
            user = sut.register(generatedU);
            accessToken = SecurityConstants.TOKEN_PREFIX + SecurityUtils.jwtGenerateAccessToken(user);
        }
        catch(Exception e){
            fail("Failed to set up tests " + e.getMessage());
        }
    }

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserService sut;

    String uri = "http://localhost:7797";

    @Test
    @WithUserDetails(value = "userName69", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void getCurrentUser_getsCurrentUser(){
        try{
            assertDoesNotThrow(()->sut.findByUsername(user.getUsername()));
            mockMvc.perform(get(uri+"/user/me")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    @WithUserDetails(value = "userName69", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updateUser_successfulyUpdatesUser(){
        try{
            assertDoesNotThrow(()->sut.findByUsername(user.getUsername()));
            String originalName = user.getUsername();
            String newName = "PartyPooper";
            User updatedUser = new User();
            updatedUser.setUsername(newName);

            mockMvc.perform(post(uri+"/user/edit")
                            .content(mapper.writeValueAsString(updatedUser))
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().json(mapper.writeValueAsString(user)));

            assertDoesNotThrow(()->sut.findByUsername(newName));
            assertThrows(NotFoundException.class, ()->sut.findByUsername(originalName));
            assertEquals(user.getPassword(), sut.findByUsername(newName).getPassword());
            assertEquals(user.getEmail(), sut.findByUsername(newName).getEmail());
            assertEquals(user.getId(), sut.findByUsername(newName).getId());
        }
        catch (Exception ex){
            fail("Unexpected Exception " + ex.getMessage());
        }
    }

    @Test
    public void getUser_DeniedNoAuthority(){
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

}
