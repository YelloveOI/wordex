package rsp.service.interfaces;

import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.rest.dto.CreateDeck;

import java.util.List;

public interface DeckService {

    Deck create(String description, String name, Language languageTo, Language languageFrom);

    //Deck createPublicCopy(Deck deck);

    //Deck createPrivateCopy(Deck deck);

    //void addCard(Deck deck, Card card);

    //void removeCard(Deck deck, Card card);

    //Deck editText(Deck deck, String name, String definition);

    Deck mapDto(CreateDeck createDeck);

    void save(Deck deck) throws Exception;

    void update(Deck deck) throws Exception;

    void deleteById(Integer id) throws Exception;

    Deck findById(Integer id) throws Exception;

    Deck findByName(String name) throws Exception;

    void createPrivateCopy(Integer id) throws Exception;

    List<Deck> getUserDecks();

    void delete(Deck deck);

    boolean exists(Deck deck);

    List<Deck> getPublicDecks();

    List<Deck> findDecksByTags(List<String> tags);
}
