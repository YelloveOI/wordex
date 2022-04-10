package rsp.service.interfaces;

import rsp.model.Card;

public interface CardService {

    Card save(Card card);

    void deleteById(Integer id);

    Card findById(Integer id);

}
