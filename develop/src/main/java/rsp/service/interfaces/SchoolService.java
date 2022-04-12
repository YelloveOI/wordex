package rsp.service.interfaces;

import rsp.model.School;

public interface SchoolService {

    School save(School school);

    void deleteById(Integer id);

    School findById(Integer id);

    public School findByName(String name);

    void createSchool(School school) throws Exception;
}
