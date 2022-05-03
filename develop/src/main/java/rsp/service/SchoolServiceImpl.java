package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.School;
import rsp.model.User;
import rsp.repo.SchoolRepo;
import rsp.security.SecurityUtils;
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
        school.addTeacher(SecurityUtils.getCurrentUser());
        repo.save(school);
    }

    @Override
    public void addStudent(@NotNull School school, @NotNull User user) {
        if (!repo.findById(school.getId()).isPresent()) {
            throw NotFoundException.create(School.class.getName(), school.getId());
        } else {
            if (repo.findById(school.getId()).orElse(school).hasStudent(user)) {
                return;
            }
            repo.findById(school.getId()).orElse(school).addStudent(user);
            save(school);
        }
    }

    @Override
    public void removeStudent(School school, User user) {
        if (repo.findById(school.getId()).isPresent() && user != null) {
            throw NotFoundException.create(School.class.getName(), school.getId());
        } else {
            if (!repo.findById(school.getId()).orElse(school).hasStudent(user)) {
                return;
            }
            repo.findById(school.getId()).orElse(school).removeStudent(user);
            save(school);
        }
    }
}
