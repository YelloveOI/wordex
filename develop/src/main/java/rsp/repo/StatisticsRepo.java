package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Card;

@Repository
public interface StatisticsRepo extends CrudRepository<Card, Integer> {
}