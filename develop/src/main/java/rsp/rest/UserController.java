package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.model.User;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
    }

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id)
}

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
    }

    /**
     *
     * @param id User id
     * @return
     */
    @PreAuthorize("hasAuthority('')")
    @GetMapping("/{id}/decks")
    public List<Deck> getUserDecks(@PathVariable int id) {
    }

    /**
     *
     * @param id Organisation id
     * @return
     */
    @PreAuthorize("hasAuthority('')")
    @GetMapping("/users")
    public List<User> getUsers(@PathVariable int id) {
    }

    @PreAuthorize("hasAuthority('')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createUser(@RequestBody User u) {
    }

    @PreAuthorize("hasAuthority('')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User u) {
    }

    @PreAuthorize("hasAuthority('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
    }
}
