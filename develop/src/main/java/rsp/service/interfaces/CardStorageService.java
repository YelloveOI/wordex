package rsp.service.interfaces;

import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

public interface CardStorageService {

    void addCardToStorage(Card card);

    void removeCardFromStorage(Card card);

    void addDeck(Deck deck);

    void update(Deck deck) throws Exception;

    void removeDeck(Integer id);

    List<Deck> getMyDecks();

    List<Card> getMyFreeCards();

    void createPrivateCopy(Deck deck);
}
