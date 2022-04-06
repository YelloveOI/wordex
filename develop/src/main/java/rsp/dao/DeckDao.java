package rsp.dao;

import org.springframework.stereotype.Repository;
import rsp.model.Deck;

@Repository
public class DeckDao extends BaseDao<Deck> {

    public DeckDao() {
        super(Deck.class);
    }

}
