package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.rest.util.RestUtils;
import rsp.service.DeckServiceImpl;

@RestController
@RequestMapping("/deck")
public class DeckController {

    private static final Logger LOG = LoggerFactory.getLogger(DeckController.class);

    private final DeckServiceImpl ds;

    @Autowired
    public DeckController(DeckServiceImpl ds) {
        this.ds = ds;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Deck getDeck(@PathVariable int id) {
        // TODO check if owned
        return ds.findById(id);
    }

    /**
     *
     * @param deck Deck to store
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createDeck(@RequestBody Deck deck) {
        try {
            ds.save(deck);
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
            ds.update(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been updated.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Used for storing answers as a whole (isKnown, isLearned).
     * @param deck
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/answersStoring")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> storeDeckAnswers(@RequestBody Deck deck) {
        try {
            ds.updateAnswers(deck);
        } catch (Exception e) {
            LOG.warn("Deck answers could not be stored! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck answers have been stored.");
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
            ds.createPrivateCopy(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be selected! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been selected.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /*
     *
     * @param id Deck id
     * @return

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/cards/{id}")
    public List<Card> getCards(@PathVariable int id) {
    }*/

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/deletion/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteDeck(@PathVariable int id) {
        try {
            ds.deleteById(id);
        } catch (Exception e) {
            LOG.warn("Deck could not be deleted! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck ID \"{}\" has been deleted.", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}