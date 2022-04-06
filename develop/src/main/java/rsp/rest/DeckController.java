package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Card;
import rsp.model.Deck;

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

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/{id}")
    public Deck getDeck(@PathVariable int id) {
    }

    @PreAuthorize("hasAuthority('')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createDeck(@RequestBody Deck d) {

    }

    /**
     *
     * @param id Deck id
     * @return
     */
    @PreAuthorize("hasAuthority('')")
    @GetMapping("/cards/{id}")
    public List<Card> getCards(@PathVariable int id) {
    }

    @PreAuthorize("hasAuthority('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeck(@PathVariable int id) {
    }
}