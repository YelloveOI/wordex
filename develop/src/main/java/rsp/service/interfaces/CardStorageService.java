package rsp.service.interfaces;

import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

public interface CardStorageService {

    void addCardToStorage(Card card);

    void removeCardFromStorage(Card card);

    void addDeck(Deck deck);

    void removeDeck(Deck deck);

    List<Deck> getMyDecks();

    List<Card> getMyFreeCards();
}
