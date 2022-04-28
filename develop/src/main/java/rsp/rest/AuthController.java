package rsp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("login")
    public ResponseEntity<?> login() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping("registration")
    public ResponseEntity<?> register() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
