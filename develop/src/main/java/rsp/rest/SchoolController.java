package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.School;

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

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/{id}")
    public School getSchool(@PathVariable int id){
    }

    @PreAuthorize("hasAuthority('')")
    @GetMapping("/all")
    public List<School> getAllSchools() {
    }

    @PreAuthorize("hasAuthority('')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> createSchool(@RequestBody School s) {
    }

    @PreAuthorize("hasAuthority('')")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSchool(@RequestBody School s) {
    }

    @PreAuthorize("hasAuthority('')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable int id) {
    }
}