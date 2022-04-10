package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Tag;

import java.util.Optional;

@Repository
public interface TagRepo extends CrudRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);

}
