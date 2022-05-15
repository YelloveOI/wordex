package rsp.service.init;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rsp.enums.Language;
import rsp.enums.Role;
import rsp.model.Deck;
import rsp.model.StatisticDeck;
import rsp.model.Statistics;
import rsp.model.User;
import rsp.repo.DeckRepo;
import rsp.repo.UserRepo;
import rsp.rest.DeckController;
import rsp.rest.dto.CardDto;
import rsp.rest.dto.CreateDeck;
import rsp.rest.dto.TagDto;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.StatisticsService;
import rsp.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    DeckService deckService;

    @Autowired
    DeckRepo repo;





    @Override
    public void run(String...args) throws Exception {

        try {
            userService.findByUsername("admin");

        } catch (Exception e) {
            final User admin = new User();

            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.addRole(Role.ADMIN);
            admin.setEmail("email@com.cz");



            userService.createAdmin(admin);
        }
        /* Mock data in db*/
        User u = addNormalUserToDB("Pavel", "Michal");
        addRandomWordsDeckToDB(u);
        addHospitalWordsToDB(u);
    }

    private User addNormalUserToDB(String name, String pswd){
        final User u = new User();
        u.setUsername(name);
        u.setPassword(pswd);
        u.setEmail(name + "@fel.cvut.cz");
        u.addRole(Role.USER);
        return userRepo.save(u);
    }

    private void addRandomWordsDeckToDB(User owner) throws Exception{
        CreateDeck deckDTO = new CreateDeck();
        deckDTO.setName("Random words");
        deckDTO.setDescription("Collection of random notable words from English");
        deckDTO.setLanguageFrom(Language.ENGLISH);
        deckDTO.setLanguageTo(Language.CZECH);
        deckDTO.setIsPrivate(false);
        deckDTO.setIsConfigurable(false);

        TagDto tag = new TagDto();
        tag.setName("Random");
        deckDTO.setTags(List.of(tag));

        CardDto card = new CardDto();
        CardDto card1 = new CardDto();
        CardDto card2 = new CardDto();
        CardDto card3 = new CardDto();
        CardDto card4 = new CardDto();

        List<CardDto> cards = new ArrayList<CardDto>();

        card.setDefinition("auto");
        card.setTerm("car");
        cards.add(card);

        card1.setDefinition("hlava");
        card1.setTerm("head");
        cards.add(card1);

        card2.setDefinition("ruka");
        card2.setTerm("arm");
        cards.add(card2);

        card3.setDefinition("stůl");
        card3.setTerm("table");
        cards.add(card3);

        card4.setDefinition("pravítko");
        card4.setTerm("ruler");
        cards.add(card4);

        deckDTO.setCards(cards);
        Deck deck = deckService.mapDto(deckDTO);
        deck.setOwner(owner);
        repo.save(deck);
    }

    private void addHospitalWordsToDB(User owner) throws Exception{
        CreateDeck deckDTO = new CreateDeck();
        deckDTO.setName("Nemocnice");
        deckDTO.setDescription("Slova z prostředí nemocníce");
        deckDTO.setLanguageFrom(Language.CZECH);
        deckDTO.setLanguageTo(Language.ENGLISH);
        deckDTO.setIsPrivate(false);
        deckDTO.setIsConfigurable(false);

        TagDto tag = new TagDto();
        tag.setName("Nemocnice");
        deckDTO.setTags(List.of(tag));

        CardDto card = new CardDto();
        CardDto card1 = new CardDto();
        CardDto card2 = new CardDto();
        CardDto card3 = new CardDto();
        CardDto card4 = new CardDto();

        List<CardDto> cards = new ArrayList<CardDto>();

        card1.setDefinition("Kost stehenní");
        card1.setTerm("femur");
        cards.add(card1);

        card2.setDefinition("infekce");
        card2.setTerm("infection");
        cards.add(card2);

        card3.setDefinition("mozek");
        card3.setTerm("brain");
        cards.add(card3);

        card4.setDefinition("krev");
        card4.setTerm("blood");
        cards.add(card4);

        card.setDefinition("Strach z dlouhých slov");
        card.setTerm("hyppopotomonstroseskvipedaliophobia");
        cards.add(card);

        deckDTO.setCards(cards);
        Deck deck = deckService.mapDto(deckDTO);
        deck.setOwner(owner);
        repo.save(deck);
    }
}