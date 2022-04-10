package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.UserDao;

import rsp.model.User;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserDao dao;

    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        dao.persist(user);
    }

    @Transactional
    public void update(User user) {
        Objects.requireNonNull(user);
        dao.update(user);
    }

    @Transactional
    public void remove(User user) {
        Objects.requireNonNull(user);
        dao.remove(user);
    }

    @Transactional
    public void register(String username, String email, String password, String matchingPassword) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(matchingPassword);
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
        if (dao.findByUsername(username) != null) {
            throw new Exception("Username is already in use.");
        }
        // Email uniqueness
        if (dao.findByEmail(email) != null) {
            throw new Exception("This email address is already in use.");
        }

        dao.persist(new User(username, email, password));
    }

    @Transactional
    public User findByUsername(String username) {
        Objects.requireNonNull(username);
        return dao.findByUsername(username);
    }

    @Transactional
    public User read(Integer id) {
        Objects.requireNonNull(id);
        return dao.find(id);
    }
}
