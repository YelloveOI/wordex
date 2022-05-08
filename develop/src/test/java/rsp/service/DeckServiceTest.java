package rsp.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rsp.environment.Generator;
import rsp.exception.NotFoundException;
import rsp.model.Deck;
import rsp.model.User;
import rsp.repo.DeckRepo;
import rsp.repo.UserRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.UserService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeckServiceTest {

    @Mock
    private DeckRepo repoMock;

    @Mock
    private UserRepo userRepo;

    private DeckServiceImpl sut;

    private Deck emptyDeck;

    private Deck fullDeck;

    private User owner;

    @BeforeEach
    public void setUp(){
        this.owner = Generator.generateRandomUser();
        lenient().when(userRepo.findById(anyInt()))
                .thenReturn(Optional.of(owner));
        lenient().when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(owner));

        this.fullDeck = Generator.generateRandomFullDeck();
        fullDeck.setOwner(owner);
        fullDeck.setId(12);
        fullDeck.setName("name");

        this.emptyDeck = Generator.generateRandomEmptyDeck();
        emptyDeck.setOwner(owner);
        emptyDeck.setId(11);
        emptyDeck.setName("name2");

        //need repair
        //this.sut = new DeckServiceImpl(repoMock);
    }

    @Test
    public void findById_isPresent_returnsDeck() throws  Exception{
        //arrange
        when(repoMock.findById(fullDeck.getId()))
                .thenReturn(Optional.of(fullDeck));
        //act
        Deck result = sut.findById(fullDeck.getId());
        //assert
        assertEquals(fullDeck, result);
    }

    @Test
    public void findById_emptyDB_throwsException(){
        //arrange
        when(repoMock.findById(fullDeck.getId()))
                .thenReturn(Optional.empty());
        //act
        //assert
        assertThrows(NotFoundException.class, ()->sut.findById(fullDeck.getId()));
    }

    @Test
    public void findByName_isPresent_returnsDeck() throws  Exception{
        //arrange
        when(repoMock.findFirstByName(fullDeck.getName()))
                .thenReturn(Optional.of(fullDeck));
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            //act
            Deck result = sut.findByName(fullDeck.getName());
            //assert
            assertEquals(fullDeck, result);
        }

    }

    @Test
    public void findByName_emptyDB_throwsException(){
        //arrange
        when(repoMock.findFirstByName(fullDeck.getName()))
                .thenReturn(Optional.empty());
        //act
        //assert
        assertThrows(NotFoundException.class, ()->sut.findByName(fullDeck.getName()));
    }


    @Test
    public void createPrivateCopy_invalidArgument_throwsException(){
        //arrange
        Integer arg = -1;

        //act
        //assert
        assertThrows(Exception.class, () -> sut.createPrivateCopy(arg));
    }

    @Test
    public void createPrivateCopy_validArgument_savesDeepCopyToRepoWithDifferingIdsAndOwners() throws Exception {
        //arrange
        Integer arg = fullDeck.getId();
        when(repoMock.findById(arg))
                .thenReturn(Optional.of(fullDeck));

        //act
        Deck result;
        sut.createPrivateCopy(arg);
        //TODO

        //assert
    }

    @Test
    public void deleteById_validArgument_deletesDeckFromRepo(){
        //arrange
        //act
        //assert
    }

    @Test
    public void deleteById_invalidArgument_throwsException(){
        //arrange
        Integer arg  = -1;

        //act
        //assert
        assertThrows(Exception.class, () -> sut.deleteById(arg));
    }

    @Test
    public void deleteById_deckIsNotOwnedByCaller_throwsException(){
        //arrange
        fullDeck.setOwner(Generator.generateRandomUser());
        when(repoMock.findById(fullDeck.getId()))
                .thenReturn(Optional.of(fullDeck));
        //act
        //assert
        assertThrows(Exception.class, ()->sut.deleteById(fullDeck.getId()));
    }


    @Test
    public void update_validArgument_updatesDeck() throws Exception{
        //arrange
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            when(repoMock.save(fullDeck))
                    .thenReturn(emptyDeck);
            fullDeck.setConfigurable(true);
            //act
            sut.update(fullDeck);

            //assert
            final ArgumentCaptor<Deck> captor =ArgumentCaptor.forClass(Deck.class);
            verify(repoMock).save(captor.capture());
            assertEquals(emptyDeck, captor.getValue());
        }


    }

    @Test
    public void update_invalidArgument_throwsException(){
        //arrange
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            emptyDeck.setConfigurable(false);

            fullDeck.setOwner(Generator.generateRandomUser());

            //act
            //assert
            assertThrows(Exception.class, ()->sut.update(emptyDeck));
            assertThrows(Exception.class,()->sut.update(fullDeck));

            verifyNoInteractions(repoMock);
        }


    }

    @Test
    public void save_validArgument_deckIsPersisted() throws Exception{
        //arrange
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            when(repoMock.save(fullDeck))
                    .thenReturn(fullDeck);

            //act
            sut.save(fullDeck);

            //assert
            final ArgumentCaptor<Deck> captor =ArgumentCaptor.forClass(Deck.class);
            verify(repoMock).save(captor.capture());
            assertEquals(owner, captor.getValue().getOwner());
        }
    }

    @Test
    public void save_validArgumentNullOwner_deckIsPersisted() throws Exception{
        //arrange
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            fullDeck.setOwner(null);
            when(repoMock.save(fullDeck))
                    .thenReturn(fullDeck);

            //act
            sut.save(fullDeck);

            //assert
            final ArgumentCaptor<Deck> captor =ArgumentCaptor.forClass(Deck.class);
            verify(repoMock).save(captor.capture());
            assertEquals(owner, captor.getValue().getOwner());
        }
    }

    @Test
    public void save_invalidArgument_throwsException(){
        //arrange
        try(MockedStatic<SecurityUtils> utils = Mockito.mockStatic(SecurityUtils.class)){
            utils.when(()->SecurityUtils.getCurrentUser())
                    .thenReturn(owner);
            fullDeck.setOwner(Generator.generateRandomUser());

            //act, assert
            assertThrows(Exception.class, ()->sut.save(fullDeck));
            verifyNoInteractions(repoMock);
        }
    }
}
