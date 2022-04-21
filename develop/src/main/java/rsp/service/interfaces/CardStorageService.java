package rsp.service.interfaces;

import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

public interface CardStorageService {

    Card addCardToMyStorage(Card card);

    void removeCardFromMyStorage(Card card);

    Card editText();

    void addContent();

    void removeContent();

    Deck createDeck();

    Deck shareDeck();

    Deck downloadDeck();

    void addCardToDeck();

    void removeCardFromDeck();

    List<Deck> getMyDeck();

    List<Deck> getPublicDecks();

    List<Card> getMyFreeCards();
}
