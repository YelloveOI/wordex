package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Card;

import java.util.Optional;

@Repository
public interface CardRepo extends CrudRepository<Card, Integer> {

}
