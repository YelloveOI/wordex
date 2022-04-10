package rsp.service.interfaces;

import rsp.enums.Language;
import rsp.model.Card;

public interface CardService {

    Card save(Card card);

    void deleteById(Integer id);

    Card findById(Integer id);

    Card create(String term, String definition, Language from, Language to);

}
