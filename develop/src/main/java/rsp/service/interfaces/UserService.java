package rsp.service.interfaces;

import rsp.model.User;

public interface UserService {

    User save(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void register(String username, String email, String password, String matchingPassword) throws Exception;

    User findByUsername(String username);

}
