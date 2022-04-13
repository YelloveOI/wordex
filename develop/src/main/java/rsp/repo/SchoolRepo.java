package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.enums.Role;
import rsp.model.School;
import rsp.model.User;

import java.util.Optional;

@Repository
public interface SchoolRepo extends CrudRepository<School, Integer> {

    Optional<School> findSchoolByName(String username);

    void addStudentToSchool(User user, School school);

    void removeStudentFromSchool(User user, School school);
}
