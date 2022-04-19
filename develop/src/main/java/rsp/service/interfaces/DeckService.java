package rsp.service.interfaces;

import rsp.model.Deck;

import java.util.List;

public interface DeckService {

    Deck save(Deck deck);

    void deleteById(Integer id);

    Deck findById(Integer id);

}
