package rsp.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteById(Integer id);

    @Override
    Iterable<User> findAll();
}
