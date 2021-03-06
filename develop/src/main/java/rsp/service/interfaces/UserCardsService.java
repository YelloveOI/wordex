package rsp.service.interfaces;

import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.model.Tag;

import java.util.List;

public interface UserCardsService {

    Card createCard(String term, String definition);

    void deleteCard(Card card);

    Card editCardText(Card card, String term, String definition);

    //TODO Content processing
    //TODO Tag processing

    Deck createDeck(String description, String name, Language languageTo, Language languageFrom);

    void deleteDeck(Deck deck);

    Deck editDeckText(Deck deck, String name, String description);

    void addCardToDeck(Deck deck, Card card);

    void removeCardFromDeck(Deck deck, Card card);

    List<Deck> getMyDecks();

    Deck getMyFreeCards();

    List<Deck> getPublicDecksByTag(Tag tag, int quantity);

}
