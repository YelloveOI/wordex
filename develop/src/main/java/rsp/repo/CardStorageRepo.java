package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Card;
import rsp.model.CardStorage;

import java.util.Optional;

@Repository
public interface CardStorageRepo extends CrudRepository<CardStorage, Long> {

    Optional<CardStorage> findByOwnerId(Integer id);

}
