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
import rsp.rest.util.RestUtils;
import rsp.service.CardServiceImpl;

@RestController
@RequestMapping("/card")
public class CardController {

    private static final Logger LOG = LoggerFactory.getLogger(CardController.class);

    private final CardServiceImpl cs;

    @Autowired
    public CardController(CardServiceImpl cs) {
        this.cs = cs;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Card getCard(@PathVariable int id) {
        // TODO check if owned
        return cs.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/newUsingValues")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCardUsingValues(@RequestBody String term,
                                                      @RequestBody String definition,
                                                      @RequestBody String translation) {
        Integer id;
        try {
            id = cs.createUsingValues(term, definition, translation);
        } catch (Exception e) {
            LOG.warn("Card could not be created! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Card ID \"{}\" has been created.", id);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCard(@RequestBody Card card) {
        Integer id;
        try {
            id = cs.create(card);
        } catch (Exception e) {
            LOG.warn("Card could not be created! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Card ID \"{}\" has been created.", id);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> updateCard(@RequestBody Card card) {
        try {
            cs.update(card);
        } catch (Exception e) {
            LOG.warn("Card could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Card ID \"{}\" has been updated.", card.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", card.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/deletion/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteCard(@PathVariable int id) {
        try {
            cs.deleteById(id);
        } catch (Exception e) {
            LOG.warn("Card could not be deleted! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Card ID \"{}\" has been deleted.", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/answer/{id}/{value}")
    public ResponseEntity<Void> answerCard(@PathVariable int id, @PathVariable String value) {
        //TODO private or public card, if is card owned and so on
        if(cs.checkAnswer(id,value)) {
            final HttpHeaders headers = RestUtils.createCardAnswerRightHeaders("/{id}", cs.findById(id));
            return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
        } else {
            final HttpHeaders headers = RestUtils.createCardAnswerWrongHeaders("/{id}", cs.findById(id));
            return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
        }
    }
}
