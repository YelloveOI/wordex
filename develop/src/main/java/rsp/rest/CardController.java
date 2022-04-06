package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Card;

@RestController
@RequestMapping("/card")
public class CardController {

    private static final Logger LOG = LoggerFactory.getLogger(CardController.class);

    private final CardService cs;

    @Autowired
    public CardController(CardService cs) {
        this.cs = cs;
    }

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/{id}")
    public Card getCard(@PathVariable int id) {
    }

    @PreAuthorize("hasAuthority('')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createCard(@RequestBody Card c) {
    }

    @PreAuthorize("hasAuthority('')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCard(@RequestBody Card c) {
    }

    @PreAuthorize("hasAuthority('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable int id) {
    }
}
