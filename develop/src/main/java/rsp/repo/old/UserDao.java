package rsp.repo.old;


import org.springframework.stereotype.Repository;
import rsp.model.User;

import javax.persistence.NoResultException;

@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findByUserName(String name) {
        try {
            return em.createNamedQuery("User.findByName", User.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
