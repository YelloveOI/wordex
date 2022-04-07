package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Task;

@Repository
public interface TaskRepo extends CrudRepository<Integer, Task> {
}
