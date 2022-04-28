package rsp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import rsp.model.User;
import rsp.rest.dto.LoginRequest;
import rsp.rest.dto.LoginResponse;
import rsp.rest.dto.RegistrationResponse;
import rsp.security.SecurityUtils;
import rsp.security.UserDetails;
import rsp.service.interfaces.UserService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        val user = (UserDetails) authenticate.getPrincipal();
        val accessToken = SecurityUtils.jwtGenerateAccessToken(user.getUser());

        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        val accessToken = SecurityUtils.jwtGenerateAccessToken(user);

        return ResponseEntity.ok(new RegistrationResponse(accessToken));
    }
}
