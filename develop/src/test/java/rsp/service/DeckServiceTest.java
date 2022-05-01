package rsp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rsp.repo.DeckRepo;
import rsp.repo.UserRepo;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.UserService;

@ExtendWith(MockitoExtension.class)
public class DeckServiceTest {

    @Mock
    private DeckRepo repoMock;

    private DeckService sut;

    @BeforeEach
    public void setUp(){
        this.sut = new DeckServiceImpl(repoMock);
    }

    @Test
    public void addDeck(){

    }
}
