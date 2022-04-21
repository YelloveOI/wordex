package rsp.service.interfaces;

import org.jetbrains.annotations.NotNull;
import rsp.model.Card;
import rsp.model.Content;

public interface CardService {

    Card createCopy(Card card);

    void deleteById(Integer id) throws Exception;

    Card findById(Integer id);

    Card create(String term, String definition);

    Card editText(Card card, String term, String definition);

    Card addContent(Card card, Content content);

    Card removeContent(Card card, Content content);

    Card deepCopy(Card card);

    boolean exists(Card card);

}
