package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.School;
import rsp.model.User;
import rsp.service.SchoolService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolController.class);

    private final SchoolService ss;

    @Autowired
    public SchoolController(SchoolService ss) {
        this.ss = ss;
    }

    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/{id}")
    public School getSchool(@PathVariable int id){
        // TODO check if public or their school
        return ss.read(id);
    }

    /*@PreAuthorize("hasAnyRole('')")
    @GetMapping("/all")
    public List<School> getAllSchools() {
    }*/

    /*
     *
     * @param id School id
     * @return

    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/school/{id}")
    public List<User> getAllSchoolUsers(@PathVariable int id) {
    }*/

    @PreAuthorize("hasAnyRole('')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createSchool(@RequestBody School school) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyRole('')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSchool(@RequestBody School school) {
    }

    @PreAuthorize("hasAnyRole('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable int id) {
    }
}