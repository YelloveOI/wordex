package rsp.service.interfaces;

import rsp.model.Deck;
import rsp.model.StatisticDeck;

public interface StatisticsService {

    Integer getNumberOfLearnedByDeckId(Integer deckId);

    Integer getNumberOfUnknownByDeckId(Integer deckId);

    void createDeck(Deck deck);

    void deleteDeck(Integer deckId);

    void updateDeck(Deck deck);

    void storeAnswer(StatisticDeck deck);

    StatisticDeck getStatisticDeckByDeckId(Integer deckId);
}
