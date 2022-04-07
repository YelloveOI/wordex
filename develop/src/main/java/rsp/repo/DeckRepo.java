package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Deck;

import java.util.List;

@Repository
public interface DeckRepo extends CrudRepository<Integer, Deck> {

    List<Deck> findAllByTagNames(List<String> tagNames);

}
