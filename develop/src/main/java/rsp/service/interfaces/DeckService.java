package rsp.service.interfaces;

import rsp.model.Deck;

public interface DeckService {

    Integer save(Deck deck);

    void update(Deck deck) throws Exception;

    void updateAnswers( Deck deck) throws Exception;

    void deleteById(Integer id) throws Exception;

    Deck findById(Integer id);

    void createPrivateCopy(Deck deck);
}
