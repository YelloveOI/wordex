package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Card;
import rsp.model.Statistics;

@Repository
public interface StatisticsRepo extends CrudRepository<Statistics, Integer> {
}
