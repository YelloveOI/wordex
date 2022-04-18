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
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createCard(@RequestBody Card card) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCard(@RequestBody Card card) {
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
