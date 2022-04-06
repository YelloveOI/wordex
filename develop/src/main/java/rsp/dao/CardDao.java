package rsp.dao;

import org.springframework.stereotype.Repository;
import rsp.model.Card;

@Repository
public class CardDao extends BaseDao<Card> {

    public CardDao() {
        super(Card.class);
    }

}
