package rsp.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsp.model.Deck;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.CardStorageService;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.TagService;

/**
 * This controller serves as API for user's interactions
 * with his cards/decks
 */
@RestController
@RequestMapping("/repository")
public class CardStorageController {

    private final CardService cardService;

    private final CardStorageService cardStorageService;

    private final DeckService deckService;

    private final TagService tagService;


    public CardStorageController(
            CardService cardService,
            CardStorageService cardStorageService,
            DeckService deckService,
            TagService tagService
    ) {
        this.cardService = cardService;
        this.cardStorageService = cardStorageService;
        this.deckService = deckService;
        this.tagService = tagService;
    }

    public void addDeck(Deck deck) {

    }

    public void deleteDeck(Deck deck) {

    }


}
