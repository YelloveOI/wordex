package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.CardDao;
import rsp.model.Card;

import java.util.Objects;

@Service
public class CardService {

    private final CardDao dao;

    @Autowired
    public CardService(CardDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(Card card) {
        Objects.requireNonNull(card);
        dao.persist(card);
    }

    @Transactional
    public void update(Card card) {
        Objects.requireNonNull(card);
        dao.update(card);
    }

    @Transactional
    public void remove(Card card) {
        Objects.requireNonNull(card);
        dao.remove(card);
    }
}
