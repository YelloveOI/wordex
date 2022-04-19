package rsp.rest;

import org.jetbrains.annotations.NotNull;
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
import rsp.security.model.AuthenticationToken;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.ContentService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger LOG = LoggerFactory.getLogger(CardController.class);

    private final CardService cas;
    private final ContentService cos;

    @Autowired
    public CardController(CardService cas, ContentService cos) {
        this.cas = cas;
        this.cos = cos;
    }

    /** TODO
     * Used for updating definition, term and translation of the card if the deck is configurable.
     * @param
     * @return Created/Bad request
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCard(@PathVariable int id) {
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
    @DeleteMapping("/{id}")
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

}
