package rsp.service.security;

import rsp.repo.UserRepo;
import rsp.model.User;
import rsp.security.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        return new rsp.security.model.UserDetails(user.get());
    }
}