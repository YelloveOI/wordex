package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.SchoolDao;
import rsp.model.Deck;
import rsp.model.School;

import java.util.Objects;

@Service
public class SchoolService {

    private final SchoolDao dao;

    @Autowired
    public SchoolService(SchoolDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(School school) {
        Objects.requireNonNull(school);
        dao.persist(school);
    }

    @Transactional
    public void update(School school) {
        Objects.requireNonNull(school);
        dao.update(school);
    }

    @Transactional
    public void remove(School school) {
        Objects.requireNonNull(school);
        dao.remove(school);
    }

    @Transactional
    public School read(Integer id) {
        Objects.requireNonNull(id);
        return dao.find(id);
    }
}
