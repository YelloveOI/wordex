package rsp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rsp.enums.Role;
import rsp.exception.NotFoundException;
import rsp.model.User;
import rsp.repo.UserRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Deprecated
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
        if (result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(User.class.getName(), id);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        Iterable<User> iterable = repo.findAll();
        iterable.forEach(result::add);

        return result;
    }

    @Override
    public User findByUsername(@NotNull String username) {
        Optional<User> result = repo.findByUsername(username);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(User.class.getName(), username);
        }
    }

    @Transactional
    public User register(@NotNull User user) throws Exception {
        // Username requirements
        if (user.getUsername().length() < 3) {
            throw new Exception("Selected username is too short. (3-20 characters allowed)");
        }
        if (user.getUsername().length() > 20) {
            throw new Exception("Selected username is too long. (3-20 characters allowed)");
        }

        // Email requirements
        if (!Pattern.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$", user.getEmail())) {
            throw new Exception("Please enter a valid email address.");
        }

        // Password requirements
        if (user.getPassword().length() < 8) {
            throw new Exception("Selected password is too short. (8-20 characters allowed)");
        }
        if (user.getPassword().length() > 20) {
            throw new Exception("Selected password is too long. (8-20 characters allowed)");
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$",
                user.getPassword())) {
            throw new Exception("Password has to contain at least one digit [0-9], " +
                    "at least one lowercase character [a-z], " +
                    "at least one uppercase character [A-Z] and " +
                    "at least one special character like ! @ # & ( ).");
        }

        // Username uniqueness
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username is already in use.");
        }
        // Email uniqueness
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("This email address is already in use.");
        }

        return repo.save(new User(user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword())));
    }

    @Transactional
    public void update(@NotNull User user) throws Exception {
        User currentUser = SecurityUtils.getCurrentUser();
        // Username
        if (!currentUser.getUsername().equals(user.getUsername())) {
            // Username uniqueness
            if (repo.findByUsername(user.getUsername()).isPresent()) {
                throw new Exception("Username is already in use.");
            }

            // Username requirements
            if (user.getUsername().length() < 3) {
                throw new Exception("Selected username is too short. (3-20 characters allowed)");
            }
            if (user.getUsername().length() > 20) {
                throw new Exception("Selected username is too long. (3-20 characters allowed)");
            }
        }

        // Email
        if (!currentUser.getEmail().equals(user.getEmail())) {
            // Email uniqueness
            if (repo.findByEmail(user.getEmail()).isPresent()) {
                throw new Exception("This email address is already in use.");
            }

            // Email requirements
            if (!Pattern.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$", user.getEmail())) {
                throw new Exception("Please enter a valid email address.");
            }
        }

        // Password
        if (!currentUser.getPassword().equals(passwordEncoder.encode(user.getPassword()))) {
            // Password requirements
            if (user.getPassword().length() < 8) {
                throw new Exception("Selected password is too short. (8-20 characters allowed)");
            }
            if (user.getPassword().length() > 20) {
                throw new Exception("Selected password is too long. (8-20 characters allowed)");
            }
            if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$",
                    user.getPassword())) {
                throw new Exception("Password has to contain at least one digit [0-9], " +
                        "at least one lowercase character [a-z], " +
                        "at least one uppercase character [A-Z] and " +
                        "at least one special character like ! @ # & ( ).");
            }

            // Encode
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // set values
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        repo.save(currentUser);
    }

    @Override
    public void addRole(@NotNull User user, @NotNull Role role) {
        if (!repo.findById(user.getId()).isPresent()) {
            throw NotFoundException.create(User.class.getName(), user.getId());
        } else {
            if (repo.findById(user.getId()).orElse(user).hasRole(role)) {
                return;
            }
            repo.findById(user.getId()).orElse(user).addRole(role);
            save(user);
        }
    }

    @Override
    public void removeRole(@NotNull User user, @NotNull Role role) {
        if (repo.findById(user.getId()).isPresent()) {
            throw NotFoundException.create(User.class.getName(), user.getId());
        } else {
            if (!repo.findById(user.getId()).orElse(user).hasRole(role)) {
                return;
            }
            repo.findById(user.getId()).orElse(user).removeRole(role);
            save(user);
        }
    }

    @Override
    public void createAdmin(@NotNull User user) {
        user.setPassword(passwordEncoder.encode("1234"));
        repo.save(user);
    }

}
