package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.model.Deck;
import rsp.model.StatisticDeck;
import rsp.model.Statistics;
import rsp.model.User;
import rsp.repo.StatisticsRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.StatisticsService;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepo repo;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepo repo) {
        this.repo = repo;
    }

    public Integer getNumberOfLearnedByDeckId(@NotNull Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        return statistics.getDeck(deckId).getNumberOfLearned();
    }

    public Integer getNumberOfUnknownByDeckId(@NotNull Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        return statistics.getDeck(deckId).getNumberOfUnknown();
    }

    public void createDeck(Deck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        if(statistics == null){
            return;
        }
        statistics.addDeck(deck);
    }

    public void deleteDeck(Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        statistics.removeDeck(deckId);
    }

    public void updateDeck(Deck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        statistics.updateDeck(deck);
        statistics.reset(deck.getId());
    }

    public void storeAnswer(StatisticDeck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        statistics.storeDeck(deck);
    }
}
