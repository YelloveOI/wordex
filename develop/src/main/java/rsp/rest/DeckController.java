package rsp.rest;



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.model.Tag;
import rsp.rest.dto.*;
import rsp.rest.dto.response.DeckSearchResult;
import rsp.rest.util.RestUtils;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;
import rsp.service.interfaces.StatisticsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deck")
public class DeckController {

    private static final Logger LOG = LoggerFactory.getLogger(DeckController.class);

    private final DeckService ds;
    private final StatisticsService ss;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DeckController(DeckService ds, StatisticsService ss) {
        this.ds = ds;
        this.ss = ss;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Deck getDeck(@PathVariable int id) {
        Deck deck;
        try {
            deck = ds.findById(id);
        } catch (Exception e) {
            LOG.warn("Deck could not be returned! {}", e.getMessage());
            return null;
        }
        LOG.debug("Deck was found.");
        return deck;
    }

    /**
     * RETURNS THE FIRST DECK IN DB WITH THIS NAME (name is not unique)
     *
     * @param name
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{name}")
    public Deck getDeckByName(@PathVariable String name) {
        Deck deck;
        try {
            deck = ds.findByName(name);
        } catch (Exception e) {
            LOG.warn("Deck could not be returned! {}", e.getMessage());
            return null;
        }
        LOG.debug("Deck was found.");
        return deck;
    }

    /**
     * Get current user's decks.
     *
     * @return Current user's decks
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/my")
    public List<UserDeckPreview> getUserDecks() {
        try {
            return ds.getUserDecks()
                    .stream()
                    .map((d) -> modelMapper.map(d, UserDeckPreview.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.warn("Decks could not be found! {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get public decks.
     *
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

    /**
     * Get public decks.
     *
     * @return Public decks
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/private")
    public DeckSearchResult[] getUserPrivateDecks() {
        List<Deck> decks;

        try {
            decks = ds.getUserPrivateDecks(SecurityUtils.getCurrentUser().getId());
        } catch (Exception e) {
            LOG.warn("Public decks could not be found! {}", e.getMessage());
            return null;
        }

        LOG.debug("Public decks were found.");
        return modelMapper.map(decks, DeckSearchResult[].class);
    }

    /**
     * @param createDeck to store (doesn't have to have owner)
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDeck(@RequestBody CreateDeck createDeck) {
        Deck deck;
        try {
            deck = ds.mapDto(createDeck);
            ds.save(deck);
            ss.createDeck(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be created! {}", e.getMessage());
            return ResponseEntity.badRequest().body("WRONG");
        }
        LOG.debug("Deck ID \"{}\" has been created.", deck.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return ResponseEntity.ok().headers(headers).body("OK");
    }

    /**
     * Used for updating name, description and language of the deck if the deck is yours and configurable.
     *
     * @param deck
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateDeck(@RequestBody Deck deck) {
        try {
            ds.update(deck);
            ss.updateDeck(deck);
        } catch (Exception e) {
            LOG.warn("Deck could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck named \"{}\" has been updated.", deck.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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

    /**
     * Selects a deck (either public or private) and makes it private so that user can start
     * answering it without the deck being modified by other users.
     *
     * @param id
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/selection")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> chooseDeck(@RequestBody Integer id) {
        try {
            ds.createPrivateCopy(id);
            ss.createDeck(getDeck(id));
        } catch (Exception e) {
            LOG.warn("Deck could not be selected! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck id \"{}\" has been selected.", id);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/deletion/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteDeck(@PathVariable int id) {
        try {
            ds.deleteById(id);
            ss.deleteDeck(id);
        } catch (Exception e) {
            LOG.warn("Deck could not be deleted! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck ID \"{}\" has been deleted.", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Get public decks having any of given tags
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/tags")
    public DeckSearchResult[] getDecksByTags(@RequestBody TagSearchDto body) {
        List<Deck> decks;
        try {
            decks = ds.findDecksByTags(List.of(body.tags));
        } catch (Exception e) {
            LOG.warn("Decks could not be found! {}", e.getMessage());
            return null;
        }
        LOG.debug("Decks were found.");
        return modelMapper.map(decks, DeckSearchResult[].class);
    }
}