package rsp.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rsp.repo.UserRepo;
import rsp.security.DefaultAuthenticationProvider;
import rsp.service.interfaces.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceIntegrationTest {

    @Mock
    private UserRepo repoMock;

    private DefaultAuthenticationProvider authMock;

    private UserService sut;

    @BeforeEach
    private void setUp(){
        this.sut = new UserServiceImpl(repoMock, authMock);
    }
}
