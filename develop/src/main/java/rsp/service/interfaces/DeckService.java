package rsp.service.interfaces;

import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

public interface DeckService {

    Deck create(String description, String name, Language languageTo, Language languageFrom);

    Deck createPublicCopy(Deck deck);

    Deck createPrivateCopy(Deck deck);

    void addCard(Deck deck, Card card);

    void removeCard(Deck deck, Card card);

//    void deleteById(Integer id) throws Exception;
//
//    Deck findById(Integer id);

    List<Deck> getCurrentUserDecks();

    List<Deck> getPublicDecks();
}
