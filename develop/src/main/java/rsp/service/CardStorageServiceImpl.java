package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.CardStorage;
import rsp.model.Deck;
import rsp.repo.CardStorageRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.CardStorageService;
import rsp.service.interfaces.DeckService;

import java.util.List;
import java.util.Optional;

/**
 * User-oriented service, processes only current
 * user's own card storage
 */
@Service
@Transactional
public class CardStorageServiceImpl implements CardStorageService {

    private final CardStorageRepo repo;
    private final DeckService deckService;

    public CardStorageServiceImpl(CardStorageRepo repo, DeckService deckService) {
        this.repo = repo;
        this.deckService = deckService;
    }

    @Override
    public void addCardToStorage(@NotNull Card card) {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            cardStorage.get().addUnassignedCard(card);

            repo.save(cardStorage.get());
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public void removeCardFromStorage(@NotNull Card card) {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            cardStorage.get().removeUnassignedCard(card);

            repo.save(cardStorage.get());
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public void addDeck(@NotNull Deck deck) {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            cardStorage.get().addDeck(deck);
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public void removeDeck(@NotNull Deck deck) {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            cardStorage.get().removeDeck(deck);
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    //For further processing
    @Override
    public Deck shareDeck(@NotNull Deck deck) {
        return deckService.createPublicCopy(deck);
    }

    //For further processing
    @Override
    public Deck downloadDeck(@NotNull Deck deck) {
        Deck result = deckService.createPrivateCopy(deck);

        addDeck(result);

        return result;
    }

    @Override
    public List<Deck> getMyDecks() {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            return cardStorage.get().getDeckList();
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public List<Deck> getPublicDecks() {
        return deckService.getPublicDecks();
    }

    @Override
    public List<Card> getMyFreeCards() {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            return cardStorage.get().getFreeCards();
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public Deck updateDeck(Deck deck) {
        removeDeck(deck);
        addDeck(deck);

        return deck;
    }

    @Override
    public Card updateCard(Card card) {
        removeCardFromStorage(card);
        addCardToStorage(card);

        return card;
    }
}
