package rsp.service.interfaces;

import rsp.enums.Role;
import rsp.model.User;

public interface UserService {

    User save(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void register(String username, String email, String password, String matchingPassword) throws Exception;

    User findByUsername(String username);

    void update(User user) throws Exception;

    void addRole(User user, Role role);

    void removeRole(User user, Role role);

    void createAdmin(User user);
}
