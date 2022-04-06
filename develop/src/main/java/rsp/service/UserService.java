package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.UserDao;
import rsp.model.Card;
import rsp.model.User;

import java.util.Objects;

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
        if (dao.findByUsername(user.getUsername()) != null) {
            throw new Exception("User with such username already exists");
        }
        if (dao.findByEmail(user.getEmail()) != null) {
            throw new Exception("User with such email already exists");
        }
        dao.persist(user);
    }

    @Transactional
    public User read(Integer id) {
        Objects.requireNonNull(id);
        return dao.find(id);
    }
}
