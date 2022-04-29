package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.CardStorage;
import rsp.model.Deck;

import java.util.Optional;

@Repository
public interface CardStorageRepo extends CrudRepository<CardStorage, Long> {

    Optional<CardStorage> findByOwnerId(Integer id);

    Deck findByDeckList_Id(Integer id);

}
