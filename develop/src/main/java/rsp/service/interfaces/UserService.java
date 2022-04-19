package rsp.service.interfaces;

import org.jetbrains.annotations.NotNull;
import rsp.enums.Role;
import rsp.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void register(String username, String email, String password, String matchingPassword) throws Exception;

    User findByUsername(String username);

    void update(String username, String email, String password, String matchingPassword) throws Exception;

    void addRole(User user, Role role);

    void removeRole(User user, Role role);

    void createAdmin(User user);

    List<User> findAll();
}
