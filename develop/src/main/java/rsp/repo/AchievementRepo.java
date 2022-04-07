package rsp.dao;

import org.springframework.data.repository.CrudRepository;
import rsp.model.Achievement;

public interface AchievementRepo extends CrudRepository<Integer, Achievement> {
    
}
