package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Achievement;

@Repository
public interface AchievementRepo extends CrudRepository<Integer, Achievement> {

}
