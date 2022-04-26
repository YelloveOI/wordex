package rsp.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Role;
import rsp.environment.Generator;
import rsp.model.User;
import rsp.security.DefaultAuthenticationProvider;
import rsp.service.interfaces.UserService;

import java.beans.Encoder;

@SpringBootTest()
@ActiveProfiles("test")
@Transactional
public class UserServiceIntegrationTest {


    @Autowired
    private UserService sut;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DefaultAuthenticationProvider auth;

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void register_validUser_encodesPassword() {
        final User user = Generator.generateRandomUser();
        final String rawPassword = user.getPassword();
        try{
            sut.register(user);
        }
        catch(Exception ex){
            fail("Exception encountered " + ex.getMessage());
        }
        User res = sut.findByUsername(user.getUsername());
        assertTrue(encoder.matches(rawPassword, res.getPassword()));
    }

    @Test
    public void saveUser_savesUserToDB(){
        //arrange
        User user = Generator.generateRandomUser();

        //act
        sut.save(user);
        User result = sut.findByUsername(user.getUsername());

        //assert
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void registerUser_validUserDetails_savesUserToDBGeneratesIdGetsRole(){
        //arrange
        User user = Generator.generateRandomUser();


        //act
        try{
            sut.register(user);
        }
        catch(Exception ex){
            fail("Exception not expected: " + ex.getMessage());
        }
        User result = sut.findByUsername(user.getUsername());

        //assert

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertNotNull(user.getId());
        assertNotNull(user.hasRole(Role.USER));
    }



    @Test
    public void registerUser_invalidUserName_throwsException(){
        //arrange
        User user = Generator.generateRandomUser();
        String validName = user.getUsername();
        String invalidName = "";

        //act
        //assert
        user.setUsername(invalidName);
        assertThrows(Exception.class, ()->sut.register(user));
        user.setUsername(validName);
        assertDoesNotThrow(()->sut.register(user));
    }
    @Test
    public void registerUser_invalidUserEmail_throwsException(){
        //arrange
        User user = Generator.generateRandomUser();
        String validEmail = user.getEmail();
        String invalidEmail = "";

        //act
        //assert

        user.setEmail(invalidEmail);
        assertThrows(Exception.class, ()->sut.register(user));
        user.setEmail(validEmail);
        assertDoesNotThrow(()->sut.register(user));

    }
    @Test
    public void registerUser_invalidUserPassword_throwsException(){
        //arrange
        User user = Generator.generateRandomUser();
        String validPassword = user.getPassword();
        String invalidPassword = "";

        //act
        //assert

        user.setPassword(invalidPassword);
        assertThrows(Exception.class, ()->sut.register(user));
        user.setPassword(validPassword);
        assertDoesNotThrow(()->sut.register(user));
    }

    @Test
    public void registerUser_userExists_throwsException(){
        //arrange
        User user = Generator.generateRandomUser();

        //act
        assertDoesNotThrow(()->sut.register(user));
        //assert
        assertThrows(Exception.class, ()->sut.register(user));
    }
}
