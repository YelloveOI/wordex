package rsp.service.interfaces;

import rsp.model.Card;
import rsp.model.CardStorage;
import rsp.model.Deck;
import rsp.model.User;
import rsp.repo.CardRepo;

import java.util.List;
import java.util.Optional;

public interface CardStorageService {

    void addCard(CardStorage cardStorage, Card card);

    void removeCard(CardStorage cardStorage, Card card);

    void addDeck(CardStorage cardStorage, Deck deck);

    void removeDeck(CardStorage cardStorage, Deck deck);

    CardStorage getCardStorage(User user);

    boolean exists(CardStorage cardStorage, Card card);

}
