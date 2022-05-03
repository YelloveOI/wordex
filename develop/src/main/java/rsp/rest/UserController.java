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
import rsp.rest.dto.CreateStudent;
import rsp.rest.util.RestUtils;
import rsp.security.SecurityUtils;
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

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMINISTRATOR')")
    @GetMapping(value = "/me")
    public User getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return us.findById(id);
    }



    /*
    Testing

    Results - cant change one value, must change all -> logic in service is faulty or need more methods
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/edit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            us.update(user);
        } catch (Exception e) {
            LOG.warn("User could not be updated! {}", e.getMessage());
            return new ResponseEntity<>("WRONG", HttpStatus.BAD_REQUEST);
        }
        LOG.info("User \"{}\" has been updated.", user.getUsername());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    //ROLE_SCHOOL_REPRESENTATIVE
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/student",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> createStudent(@RequestBody CreateStudent createStudent) {
        User user;
        try {
            user = us.findByUsername(createStudent.getUsername());
            us.addRole(user, Role.STUDENT);
            ss.addStudent(ss.findByName(createStudent.getNameSchool()), user);
        } catch (Exception e) {
            LOG.warn("User could not receive the student role! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("Role student has been added to user \"{}\".", user.getUsername());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                us.findByUsername(user.getUsername()).getId());
        return ResponseEntity.ok().headers(headers).body("OK");
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


    @PostMapping("/check")
    public ResponseEntity<?> sanityCheck() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser().getEmail());
    }
}
