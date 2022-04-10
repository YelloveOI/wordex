package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.Deck;
import rsp.model.User;
import rsp.rest.util.RestUtils;
import rsp.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl us;

    @Autowired
    public UserController(UserServiceImpl us) {
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
        return us.findById(id);
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

    /**
     *
     * @param username Selected username has to be 3-20 characters long and cannot be already in use.
     * @param email Selected Email has to have a valid form and cannot be already in use.
     * @param password Selected password has to be 8-20 characters long.
     *                 Has to contain at least one digit [0-9], at least one lowercase character [a-z],
     *                 at least one uppercase character [A-Z] and at least one special character like ! @ # & ( ).
     * @param matchingPassword Must match password.
     * @return No content/Bad request
     */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createUser(@RequestBody String username, @RequestBody String email,
                                           @RequestBody String password, @RequestBody String matchingPassword) {
        try {
            us.register(username, email, password, matchingPassword);
        } catch (Exception e) {
            LOG.warn("User could not be registered! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("User \"{}\" has been registered.", username);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(username).getId());   // TODO edit to log in
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
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
