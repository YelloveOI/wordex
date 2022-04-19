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
import rsp.model.School;
import rsp.model.User;
import rsp.rest.util.RestUtils;
import rsp.security.SecurityUtils;
import rsp.security.model.AuthenticationToken;
import rsp.service.interfaces.SchoolService;
import rsp.service.interfaces.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService us;
    private final SchoolService ss;

    @Autowired
    public UserController(UserService us, SchoolService ss) {
        this.us = us;
        this.ss = ss;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrentUser(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return us.findById(id);
    }

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
    }*/

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
    @PreAuthorize("isAnonymous()")
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

    @PreAuthorize("permitAll()")
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateUser(@RequestBody String username, @RequestBody String email,
                                           @RequestBody String password, @RequestBody String matchingPassword) {
        try {
            us.update(username, email, password, matchingPassword);
        } catch (Exception e) {
            LOG.warn("User could not be updated! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("User \"{}\" has been updated.", username);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(username).getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SCHOOL_REPRESENTATIVE')")
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createStudent(@RequestBody User user, @RequestBody School school) {
        try {
            us.addRole(user, Role.STUDENT);
            ss.addStudent(school, user);
        } catch (Exception e) {
            LOG.warn("User could not receive the student role! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Role student has been added to user \"{}\".", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(user.getUsername()).getId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_SCHOOL_REPRESENTATIVE')")
    @DeleteMapping("/student")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
    }
}
