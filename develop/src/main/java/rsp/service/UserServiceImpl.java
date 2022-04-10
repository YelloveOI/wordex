package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rsp.exception.NotFoundException;
import rsp.model.School;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.service.interfaces.UserService;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo repo;

    @Autowired
    public UserServiceImpl(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public User save(@NotNull User user) {
        repo.save(user);
        return user;
    }

    @Override
    public void deleteById(@NotNull Integer id) {
        repo.deleteById(id);
    }

    @Override
    public User findById(@NotNull Integer id) {
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(User.class.getName(), id);
        }
    }

    @Override
    public User findByUsername(@NotNull String username) {
        Optional<User> result = repo.findByEmail(username);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(User.class.getName(), username);
        }
    }

    @Transactional
    public void register(
            @NotNull String username,
            @NotNull String email,
            @NotNull String password,
            @NotNull String matchingPassword
    ) throws Exception {
        // Username requirements
        if (username.length() < 3) {
            throw new Exception("Selected username is too short. (3-20 characters allowed)");
        }
        if (username.length() > 20) {
            throw new Exception("Selected username is too long. (3-20 characters allowed)");
        }

        // Username requirements
        if (!Pattern.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$", email)) {
            throw new Exception("Please enter a valid email address.");
        }

        // Password requirements
        if (!password.equals(matchingPassword)) {   // is done differently?
            throw new Exception("Passwords do not match.");
        }
        if (password.length() < 8) {
            throw new Exception("Selected password is too short. (8-20 characters allowed)");
        }
        if (password.length() > 20) {
            throw new Exception("Selected password is too long. (8-20 characters allowed)");
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$",
                password)) {
            throw new Exception("Password has to contain at least one digit [0-9], " +
                    "at least one lowercase character [a-z], " +
                    "at least one uppercase character [A-Z] and " +
                    "at least one special character like ! @ # & ( ).");
        }

        // Username uniqueness
        if (repo.findByUsername(username).isPresent()) {
            throw new Exception("Username is already in use.");
        }
        // Email uniqueness
        if (repo.findByEmail(email).isPresent()) {
            throw new Exception("This email address is already in use.");
        }

        repo.save(new User(username, email, password));
    }

}
