package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.enums.Role;
import rsp.model.Deck;
import rsp.model.School;
import rsp.model.User;
import rsp.rest.util.RestUtils;
import rsp.security.model.AuthenticationToken;
import rsp.service.UserServiceImpl;
import rsp.service.interfaces.SchoolService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl us;
    private final SchoolService ss;

    @Autowired
    public UserController(UserServiceImpl us, SchoolService ss) {
        this.us = us;
        this.ss = ss;
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }

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
    @ResponseStatus(HttpStatus.CREATED)
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
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('')")
    @PatchMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        try {
            us.update(user);
        } catch (Exception e) {
            LOG.warn("User could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("User \"{}\" has been updated.", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(user.getUsername()).getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SCHOOL_REPRESENTATIVE')")
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createStudent(@RequestBody User user, @RequestBody School school) {
        try {
            us.addRole(user, Role.STUDENT);
            ss.addStudent(school, user);
        } catch (Exception e) {
            LOG.warn("User could not had role student! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Role student has been added to user \"{}\".", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(user.getUsername()).getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SCHOOL_REPRESENTATIVE')")
    @DeleteMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> removeStudent(@RequestBody User user, @RequestBody School school) {
        try {
            us.removeRole(user, Role.STUDENT);
            ss.removeStudent(school, user);
        } catch (Exception e) {
            LOG.warn("Role student cannot be removed from user! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Role student has been removed from user \"{}\".", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(user.getUsername()).getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
    }
}
