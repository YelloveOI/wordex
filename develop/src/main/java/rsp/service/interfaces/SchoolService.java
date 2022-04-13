package rsp.service.interfaces;

import rsp.model.School;
import rsp.model.User;

public interface SchoolService {

    School save(School school);

    void deleteById(Integer id);

    School findById(Integer id);

    public School findByName(String name);

    void createSchool(School school) throws Exception;

    void addStudent(School school, User user);

    void removeStudent(School school, User user);
}
