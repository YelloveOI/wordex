package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.UserDao;
import rsp.model.Card;
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
    public void register(User user) throws Exception {
        Objects.requireNonNull(user);
        // Username requirements
        if (user.getUsername().length() < 3) {
            throw new Exception("Selected username is too short. (3-20 characters allowed)");
        }
        if (user.getUsername().length() > 20) {
            throw new Exception("Selected username is too long. (3-20 characters allowed)");
        }

        // Password requirements
        if (user.getPassword().length() < 8) {
            throw new Exception("Selected password is too short. (8-20 characters allowed)");
        }
        if (user.getPassword().length() > 20) {
            throw new Exception("Selected password is too long. (8-20 characters allowed)");
        }
        if (Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
                user.getPassword())) {
            throw new Exception("Password has to contain at least one digit [0-9], " +
                    " at least one lowercase character [a-z], " +
                    " at least one uppercase character [A-Z], " +
                    " at least one special character like ! @ # & ( ).");
        }

        // Username uniqueness
        if (dao.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username is already in use.");
        }
        // Email uniqueness
        if (dao.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email is already in use.");
        }

        dao.persist(user);
    }

    @Transactional
    public User read(Integer id) {
        Objects.requireNonNull(id);
        return dao.find(id);
    }
}
