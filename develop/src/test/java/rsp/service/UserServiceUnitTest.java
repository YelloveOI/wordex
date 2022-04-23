package rsp.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static  org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import rsp.environment.TestConfig;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.security.DefaultAuthenticationProvider;
import rsp.service.interfaces.UserService;

import java.util.Optional;


@SpringBootTest(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepo repoMock;

    @Mock
    private DefaultAuthenticationProvider authMock;

    private UserService sut;

    @BeforeEach
    private void setUp(){
        this.sut = new UserServiceImpl(repoMock, authMock);
    }

    //example unit test
    @Test
    public void getUserByName_getsUser_Test() {
        final User user = Generator.generateRandomUser();
        when(repoMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User result = sut.findByUsername(user.getUsername());

        assertEquals(user, result);
    }
    //TODO unit tests

    @Test
    public void saveUser_validUser_userSaved(){
        //arrange
        User orgUser = Generator.generateRandomUser();
        String orgPassword = orgUser.getPassword();
        String encryptedString = "?12HaFGdfgdfg";
        when(authMock.encode(anyString())).thenReturn(encryptedString);
        when(repoMock.save(any(User.class))).thenAnswer(new Answer<User>() {
            int idCounter = 1;

            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                User u = (User)invocationOnMock.getArgument(0);
                u.setId(idCounter++);
                return u;
            }

        });



        //act
        try{
            sut.register(orgUser.getUsername(),orgUser.getEmail(), orgUser.getPassword(), orgUser.getPassword());
        }
        catch (Exception ex){
            fail("Unexpected error " + ex.getMessage());
        }


        //assert
        verify(authMock).encode(orgPassword);
        assertNull(verify(repoMock).findByUsername(orgUser.getUsername()));
        assertNull(verify(repoMock).findByEmail(orgUser.getEmail()));

        orgUser.setPassword(encryptedString);
        User resultUser = verify(repoMock,times(1)).save(any(User.class));
        //assertNotNull(resultUser.getId());
        //assertEquals(encryptedString, resultUser.getPassword());
    }
}
