package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.model.User;
import rsp.rest.util.RestUtils;
import rsp.service.UserService;

import java.util.ArrayList;
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

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
    }*/

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        // TODO create similar method for users and check if they can view this user
        return us.read(id);
    }

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
    }*/

    /**
     *
     * @param id User id
     * @return
     */
    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/{id}/decks")
    public List<Deck> getUserDecks(@PathVariable int id) {
        return new ArrayList<>();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        try {
            us.register(user);
        } catch (Exception e) {
            LOG.warn("User could not be registered! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("User \"{}\" has been registered.", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user) {
    }

    @PreAuthorize("hasAnyRole('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
    }
}
