package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.School;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public interface SchoolRepo extends CrudRepository<School, Integer> {

    Optional<School> findSchoolByName(String username);

}
