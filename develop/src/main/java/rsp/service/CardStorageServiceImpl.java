package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.CardStorage;
import rsp.model.Deck;
import rsp.model.User;
import rsp.repo.CardStorageRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.CardStorageService;
import rsp.service.interfaces.DeckService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardStorageServiceImpl implements CardStorageService {

    private final CardStorageRepo repo;

    public CardStorageServiceImpl(CardStorageRepo repo) {
        this.repo = repo;
    }

    @Override
    public void addCard(@NotNull CardStorage cardStorage, @NotNull Card card) {
        cardStorage.addUnassignedCard(card);

        repo.save(cardStorage);
    }

    @Override
    public void removeCard(@NotNull CardStorage cardStorage, @NotNull Card card) {
        cardStorage.removeUnassignedCard(card);

        repo.save(cardStorage);
    }

    @Override
    public void addDeck(@NotNull CardStorage cardStorage, @NotNull Deck deck) {
        cardStorage.addDeck(deck);

        repo.save(cardStorage);
    }

    @Override
    public void removeDeck(@NotNull CardStorage cardStorage, @NotNull Deck deck) {
        cardStorage.removeDeck(deck);

        repo.save(cardStorage);
    }

    @Override
    public CardStorage getCardStorage(User user) {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(user.getId());

        if(cardStorage.isPresent()) {
            return cardStorage.get();
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), user.getId());
        }
    }

    @Override
    public boolean exists(@NotNull CardStorage cardStorage, Card card) {
        return cardStorage.getFreeCards().contains(card);
    }
}
