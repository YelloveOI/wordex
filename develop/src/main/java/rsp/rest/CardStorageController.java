package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.rest.util.RestUtils;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.CardStorageService;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.TagService;

import java.util.List;

/**
 * This controller serves as API for user's interactions
 * with his cards/decks
 */
@RestController
@RequestMapping("/repository")
public class CardStorageController {

    private static final Logger LOG = LoggerFactory.getLogger(CardStorageController.class);

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

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/deletion/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteDeck(@PathVariable int id) {
        try {
            cardStorageService.removeDeck(id);
        } catch (Exception e) {
            LOG.warn("Deck could not be deleted! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck ID \"{}\" has been deleted.", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Get current user's decks.
     * @return Current user's decks
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/myDecks")
    public List<Deck> getMyDecks() {
        List<Deck> decks;
        try {
            decks = cardStorageService.getMyDecks();
        } catch (Exception e) {
            LOG.warn("Decks could not be found! {}", e.getMessage());
            return null;
        }
        LOG.debug("Decks were found.");
        return decks;
    }

    /**
     *
     * @param deck Deck to store
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/newDeck")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addDeck(@RequestBody Deck deck) {
        try {
            cardStorageService.addDeck(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be created! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck ID \"{}\" has been created.", deck.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Used for updating name, description and language of the deck if the deck is configurable.
     * @param deck
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateDeck(@RequestBody Deck deck) {
        try {
            cardStorageService.update(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been updated.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Selects a deck (either public or private) and makes it private so that user can start
     * answering it without the deck being modified by other users.
     * @param deck
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/selection")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> chooseDeck(@RequestBody Deck deck) {
        try {
            cardStorageService.createPrivateCopy(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be selected! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been selected.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
