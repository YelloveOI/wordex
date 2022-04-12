package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.School;
import rsp.repo.SchoolRepo;
import rsp.service.interfaces.SchoolService;

import java.util.Optional;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepo repo;

    @Autowired
    public SchoolServiceImpl(SchoolRepo repo) {
        this.repo = repo;
    }


    @Override
    public School save(@NotNull School school) {
        repo.save(school);
        return school;
    }

    @Override
    public void deleteById(@NotNull Integer id) {
        repo.deleteById(id);
    }

    @Override
    public School findById(@NotNull Integer id) {
        Optional<School> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(School.class.getName(), id);
        }
    }

    @Override
    public School findByName(@NotNull String name) {
        Optional<School> result = repo.findSchoolByName(name);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(School.class.getName(), name);
        }
    }

    @Override
    public void createSchool(@NotNull School school) throws Exception {
        if (repo.findSchoolByName(school.getName()).isPresent()) {
            throw new Exception("School that goes by this name is already in use.");
        }
        repo.save(school);
    }
}
