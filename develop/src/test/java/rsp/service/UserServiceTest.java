package rsp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static  org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rsp.environment.Generator;
import rsp.environment.TestConfig;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.security.DefaultAuthenticationProvider;
import rsp.service.interfaces.UserService;

import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo repoMock;

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

        Assertions.assertEquals(user, result);
    }
}
