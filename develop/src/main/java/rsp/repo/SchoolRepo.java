package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.School;

import java.util.Optional;

@Repository
public interface SchoolRepo extends CrudRepository<School, Integer> {

    Optional<School> findSchoolByName(String username);
}
