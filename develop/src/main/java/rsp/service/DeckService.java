package rsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.dao.DeckDao;
import rsp.model.Deck;

import java.util.Objects;

@Service
public class DeckService {

    private final DeckDao dao;

    @Autowired
    public DeckService(DeckDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(Deck deck) {
        Objects.requireNonNull(deck);
        dao.persist(deck);
    }

    @Transactional
    public void update(Deck deck) {
        Objects.requireNonNull(deck);
        dao.update(deck);
    }

    @Transactional
    public void remove(Deck deck) {
        Objects.requireNonNull(deck);
        dao.remove(deck);
    }
}
