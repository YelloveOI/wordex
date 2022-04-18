package rsp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rsp.model.School;
import rsp.rest.util.RestUtils;
import rsp.service.SchoolServiceImpl;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolController.class);

    private final SchoolServiceImpl ss;

    @Autowired
    public SchoolController(SchoolServiceImpl ss) {
        this.ss = ss;
    }

    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/{id}")
    public School getSchool(@PathVariable int id){
        // TODO check if public or their school
        return ss.findById(id);
    }

    @PreAuthorize("hasAnyRole('')")
    @GetMapping("/{name}")
    public School getSchool(@PathVariable String name){
        return ss.findByName(name);
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

    @PreAuthorize("hasAnyRole('ROLE_SCHOOL_REPRESENTATIVE', 'ROLE_ADMINISTRATOR')")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createSchool(@RequestBody School school) {
        try {
            ss.createSchool(school);
        } catch (Exception e) {
            LOG.warn("School could not be created! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOG.debug("School \"{}\" has been created.", school.getName());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}",
                ss.findByName(school.getName()).getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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