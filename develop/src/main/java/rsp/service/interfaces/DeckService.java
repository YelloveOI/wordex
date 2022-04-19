package rsp.service.interfaces;

import org.jetbrains.annotations.NotNull;
import rsp.model.Deck;

public interface DeckService {

    Integer save(Deck deck);

    void update(@NotNull Deck deck) throws Exception;

    void deleteById(Integer id) throws Exception;

    Deck findById(Integer id);

}
