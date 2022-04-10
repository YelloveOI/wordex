package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.User;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserRepo, Integer> {

    Optional<User> findByName(String name);

    void deleteById(Integer id);

}
