package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.rest.util.RestUtils;
import rsp.service.DeckService;

import java.util.ArrayList;
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

    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/{id}")
    public Deck getDeck(@PathVariable int id) {
        // TODO check if owned
        return ds.read(id);
    }

    /**
     *
     * @param deck Deck to store
     * @return No content/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STUDENT', 'ROLE_ADMINISTRATOR', 'ROLE_SCHOOL_REPRESENTATIVE', " +
            "'ROLE_PREMIUM_USER')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createDeck(@RequestBody Deck deck) {
        try {
            ds.persist(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not been created! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been created.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    /*
     *
     * @param id Deck id
     * @return

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/cards/{id}")
    public List<Card> getCards(@PathVariable int id) {
    }*/

    @PreAuthorize("hasAnyRole('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeck(@PathVariable int id) {
    }
}