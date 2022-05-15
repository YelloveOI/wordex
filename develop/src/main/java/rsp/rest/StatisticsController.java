package rsp.rest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.StatisticDeck;
import rsp.rest.dto.KnownWordsDto;
import rsp.rest.util.RestUtils;
import rsp.service.interfaces.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{deckId}/learned")
    public Integer getLearnedAmount(@PathVariable Integer deckId) {
        return service.getNumberOfLearnedByDeckId(deckId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{deckId}/unknown")
    public Integer getUnknownAmount(@PathVariable Integer deckId) {
        return service.getNumberOfUnknownByDeckId(deckId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{deckId}")
    public String getStatisticDeck(@PathVariable Integer deckId) {
        return service.getStatisticDeckByDeckId(deckId).toJSON();
    }

    /**
     * Used for storing answers as a whole (isKnown, isLearned).
     * @param jsonDeck
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/answersStoring")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> storeDeckAnswers(@RequestBody String jsonDeck) {
        StatisticDeck deck = new StatisticDeck(jsonDeck);
        try {
            service.storeAnswer(deck);
        } catch (Exception e) {
            LOG.warn("Deck answers could not be stored! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck answers have been stored.");
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", deck.getId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/knownWordsStoring")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> storeKnownWords(@RequestBody KnownWordsDto knownWords) {
        try {
            StatisticDeck answer = service.countAnswer(knownWords.getDeckId(), knownWords.getKnownCards());
            service.storeAnswer(answer);
        } catch (Exception e) {
            LOG.warn("Deck answers could not be stored! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Deck answers have been stored.");
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", knownWords.getDeckId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
