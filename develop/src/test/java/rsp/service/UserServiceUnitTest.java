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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import rsp.enums.Role;
import rsp.environment.Generator;
import rsp.exception.NotFoundException;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.service.interfaces.UserService;

import javax.swing.text.html.Option;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepo repoMock;

    @Mock
    private PasswordEncoder encoder;


    private UserService sut;

    @BeforeEach
    public void setUp(){
        this.sut = new UserServiceImpl(repoMock,encoder);
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
