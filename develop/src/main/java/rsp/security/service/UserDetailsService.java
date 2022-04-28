package rsp.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.security.UserDetails;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    final private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(
                       () -> new UsernameNotFoundException(String.format("User '%s' not found.", username))
                );

        return new rsp.security.UserDetails(user);
    }
}
