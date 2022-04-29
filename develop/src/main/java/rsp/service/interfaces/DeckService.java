package rsp.service.interfaces;

import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

public interface DeckService {

    Deck create(String description, String name, Language languageTo, Language languageFrom);

    void addCard(Deck deck, Card card);

    void removeCard(Deck deck, Card card);

    Deck editText(Deck deck, String name, String definition);

    List<Deck> getPublicDecks();

    Deck findById(Integer id);
}
