package rsp.dao;

import org.springframework.stereotype.Repository;
import rsp.model.School;

@Repository
public class SchoolDao extends BaseDao<School> {

    public SchoolDao() {
        super(School.class);
    }

}
