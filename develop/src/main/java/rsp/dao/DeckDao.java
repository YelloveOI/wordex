package rsp.dao;

import org.springframework.stereotype.Repository;
import rsp.model.Deck;

import javax.persistence.NoResultException;

@Repository
public class DeckDao extends BaseDao<Deck> {

    public DeckDao() {
        super(Deck.class);
    }

    public User findByUserUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
