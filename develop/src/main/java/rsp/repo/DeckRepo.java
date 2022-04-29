package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Deck;

import java.util.List;

@Repository
public interface DeckRepo extends CrudRepository<Deck, Integer> {

    List<Deck> findAllByIsPrivateFalse();

    //List<Deck> findAllByOwnerId(Integer id);

}
