package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.StatisticDeck;

import java.util.Optional;

@Repository
public interface StatisticsDeckRepo extends CrudRepository<StatisticDeck, Integer> {

    void deleteByDeckId(Integer deckId);
}
