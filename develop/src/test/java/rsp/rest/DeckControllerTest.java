package rsp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rsp.environment.Generator;
import rsp.model.User;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.UserService;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeckService deckService;

    private User user;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    String uri = "http://localhost:7797";

    @BeforeEach
    public void setUp(){
        try{
            User generatedU = Generator.generateRandomUser();
            generatedU.setUsername("userName69");
            user = userService.register(generatedU);
        }
        catch(Exception e){
            fail("Failed to set up tests " + e.getMessage());
        }
    }


    @Test
    @WithUserDetails(value = "userName69", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void systemTest_createDeck_addToDeck_DeleteFromDeck(){

    }
}
