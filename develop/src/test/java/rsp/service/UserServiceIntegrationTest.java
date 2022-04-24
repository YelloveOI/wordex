package rsp.service;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rsp.environment.Generator;
import rsp.model.User;
import rsp.service.interfaces.UserService;

@SpringBootTest()
@ActiveProfiles("test")
@Transactional
public class UserServiceIntegrationTest {


    @Autowired
    private UserService sut;

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void testTest(){
        //arrange
        User user = Generator.generateRandomUser();
        sut.save(user);

        //act
        User result = sut.findByUsername(user.getUsername());

        //assert
        assertEquals(user.getUsername(), result.getUsername());
    }
}
