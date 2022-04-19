package rsp.service.interfaces;

import rsp.model.Card;

public interface CardService {

    void save(Card card);

    void deleteById(Integer id) throws Exception;

    Card findById(Integer id);

    Integer createUsingValues(String term, String definition, String translation);

    Integer create(Card card);

    void update(Card card) throws Exception;

    void updateAnswers(Card card) throws Exception;
}
