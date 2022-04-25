package rsp.service.interfaces;

import rsp.model.Deck;
import rsp.model.Tag;

import java.util.List;

public interface TagService {

    List<Deck> findPublicDecksWithTag(String tagName, int decksQuantity);

//    List<Deck> findDecksWithTags(List<String> tagNames, int deckQuantity);

    Tag findByName(String tagName);

    Tag create(String name);

    void addDeck(Tag tag, Deck deck);

    void removeDeck(Tag tag, Deck deck);

    Boolean tagWithNameExists(String name);
}
