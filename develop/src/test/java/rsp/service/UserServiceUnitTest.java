package rsp.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static  org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import rsp.enums.Role;
import rsp.environment.Generator;
import rsp.exception.NotFoundException;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.security.DefaultAuthenticationProvider;
import rsp.service.interfaces.UserService;

import javax.swing.text.html.Option;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepo repoMock;

    @Mock
    private DefaultAuthenticationProvider authMock;

    private UserService sut;

    @BeforeEach
    public void setUp(){
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
            sut.register(orgUser);
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
    }

    @Test
    public void addRole_StudentRole_roleAdded(){
        //arrange
        User user = Generator.generateRandomUser();
        user.setId(12);
        when(repoMock.findById(user.getId())).thenReturn(Optional.of(user));
        Role res= Role.STUDENT;

        //act
        try{
            sut.addRole(user,res);
        }
        catch (Exception ex){
            fail("Error not expected " + ex.getMessage());
        }
        //assert

        final ArgumentCaptor<User> captor =ArgumentCaptor.forClass(User.class);
        verify(repoMock).save(captor.capture());
        assertTrue(captor.getValue().hasRole(res));

    }

    @Test
    public void addRole_StudentRole_UserNotFound(){
        //arrange
        User user = Generator.generateRandomUser();
        user.setId(12);
        when(repoMock.findById(user.getId())).thenReturn(Optional.empty());
        //act
        //assert
        assertThrows(NotFoundException.class, ()->sut.addRole(user,Role.STUDENT));
        verify(repoMock).findById(user.getId());
    }
}
