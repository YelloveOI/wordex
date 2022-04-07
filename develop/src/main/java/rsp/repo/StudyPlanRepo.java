package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.StudyPlan;

@Repository
public interface StudyPlanRepo extends CrudRepository<Integer, StudyPlan> {
}
