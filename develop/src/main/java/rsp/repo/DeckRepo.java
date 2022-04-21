package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Deck;

import java.util.List;

@Repository
public interface DeckRepo extends CrudRepository<Deck, Integer> {

    List<Deck> findAllByTagsIn(List<String> tags);

    List<Deck> findAllByOwnerId(Integer id);

    List<Deck> findByIsPrivateFalse();

}
