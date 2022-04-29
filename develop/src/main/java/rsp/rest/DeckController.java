package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.service.interfaces.DeckService;

import java.util.List;

@RestController
@RequestMapping("/deck")
public class DeckController {

    private static final Logger LOG = LoggerFactory.getLogger(DeckController.class);

    private final DeckService ds;

    @Autowired
    public DeckController(DeckService ds) {
        this.ds = ds;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Deck getDeck(@PathVariable int id) {
        return ds.findById(id);
    }

    /**
     * Get public decks.
     * @return Public decks
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/public")
    public List<Deck> getPublicDecks() {
        List<Deck> decks;
        try {
            decks = ds.getPublicDecks();
        } catch (Exception e) {
            LOG.warn("Public decks could not be found! {}", e.getMessage());
            return null;
        }
        LOG.debug("Public decks were found.");
        return decks;
    }

    /*
     * Used for storing answers as a whole (isKnown, isLearned).
     * @param deck
     * @return Created/Bad request

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
    }*/

    /*
     *
     * @param id Deck id
     * @return

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/cards/{id}")
    public List<Card> getCards(@PathVariable int id) {
    }*/
}