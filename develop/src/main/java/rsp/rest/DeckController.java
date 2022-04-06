package rsp.rest;

import org.graalvm.compiler.nodes.java.ExceptionObjectNode;
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

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<Void> createDeck(@RequestBody Deck deck) {
        try {
            ds.persist(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not been created!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been created.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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