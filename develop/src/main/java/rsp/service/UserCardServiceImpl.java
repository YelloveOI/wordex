package rsp.service;

import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.model.Tag;
import rsp.service.interfaces.*;

import java.util.List;

public class UserCardServiceImpl implements UserCardsService {

    private final CardService cardService;

    private final CardStorageService cardStorageService;

    private final ContentService contentService;

    private final DeckService deckService;

    public UserCardServiceImpl(CardService cardService, CardStorageService cardStorageService, ContentService contentService, DeckService deckService) {
        this.cardService = cardService;
        this.cardStorageService = cardStorageService;
        this.contentService = contentService;
        this.deckService = deckService;
    }

    @Override
    public Card createCard(String term, String definition) {
        Card result = cardService.create(term, definition);

        cardStorageService.addCardToStorage(result);

        return result;
    }

    @Override
    public void deleteCard() {
        //TODO
    }

    @Override
    public Card editCardText(Card card, String term, String definition) {
        return cardService.editText(card, term, definition);
    }

    @Override
    public Deck createDeck(String description, String name, Language languageTo, Language languageFrom) {
        Deck result = deckService.create(description, name, languageTo, languageFrom);

        cardStorageService.addDeck(result);

        return result;
    }

    @Override
    public void deleteDeck() {
        //TODO
    }

    @Override
    public Deck editDeckText(Deck deck, String name, String description) {
        return deckService.editText(deck, name, description);
    }

    @Override
    public void addCardToDeck(Deck deck, Card card) {
        deckService.addCard(deck, card);
    }

    @Override
    public void removeCardFromDeck(Deck deck, Card card) {
        deckService.removeCard(deck, card);
    }

    @Override
    public List<Deck> getMyDecks() {
        return cardStorageService.getMyDecks();
    }

    @Override
    public List<Card> getMyFreeCards() {
        return cardStorageService.getMyFreeCards();
    }

    @Override
    public List<Deck> getPublicDecksByTag(Tag tag, int quantity) {
        //TODO
        return null;
    }
}
