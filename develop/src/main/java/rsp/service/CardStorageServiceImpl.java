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

    public CardStorageServiceImpl(CardStorageRepo repo) {
        this.repo = repo;
    }

    private CardStorage getCardStorage() {
        Optional<CardStorage> cardStorage = repo.findByOwnerId(SecurityUtils.getCurrentUser().getId());

        if(cardStorage.isPresent()) {
            return cardStorage.get();
        } else {
            throw NotFoundException.create(CardStorage.class.getName(), SecurityUtils.getCurrentUser());
        }
    }

    @Override
    public void addCardToStorage(@NotNull Card card) {
        CardStorage cardStorage = getCardStorage();

        cardStorage.addUnassignedCard(card);
        repo.save(cardStorage);
    }

    @Override
    public void removeCardFromStorage(@NotNull Card card) {
        CardStorage cardStorage = getCardStorage();

        cardStorage.removeUnassignedCard(card);
        repo.save(cardStorage);
    }

    @Override
    public void addDeck(@NotNull Deck deck) {
        CardStorage cardStorage = getCardStorage();

        cardStorage.addDeck(deck);
        repo.save(cardStorage);
    }

    @Override
    public void update(@NotNull Deck deck) throws Exception {
        Deck current = repo.findByDeckList_Id(deck.getId());

        if (!current.isConfigurable()) {
            throw new Exception("This deck is not configurable.");
        }

        CardStorage cardStorage = getCardStorage();

        cardStorage.removeDeck(current);
        cardStorage.addDeck(deck);

        repo.save(cardStorage);
    }

    @Override
    public void removeDeck(@NotNull Integer id) {
        CardStorage cardStorage = getCardStorage();
        Deck deck = repo.findByDeckList_Id(id);

        cardStorage.removeDeck(deck);
        repo.save(cardStorage);
    }

    @Override
    public List<Deck> getMyDecks() {
        CardStorage cardStorage = getCardStorage();

        return cardStorage.getDeckList();
    }

    @Override
    public List<Card> getMyFreeCards() {
        CardStorage cardStorage = getCardStorage();

        return cardStorage.getFreeCards();
    }

    /**
     * Creates deck w/o cards
     * @param deck
     * @return
     */
    @Override
    public void createPrivateCopy(@NotNull Deck deck) {
        CardStorage cardStorage = getCardStorage();

        Deck result = new Deck();
        result.setPrivate(true);
        result.setConfigurable(deck.isConfigurable());
        result.setLanguageFrom(deck.getLanguageFrom());
        result.setLanguageTo(deck.getLanguageTo());
        result.setDescription(deck.getDescription());
        result.setName(deck.getName());
        result.setTags(deck.getTags());

        cardStorage.addDeck(result);
        repo.save(cardStorage);
    }
}
