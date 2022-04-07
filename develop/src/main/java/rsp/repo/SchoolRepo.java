package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.School;

@Repository
public interface SchoolRepo extends CrudRepository<Integer, School> {
}
