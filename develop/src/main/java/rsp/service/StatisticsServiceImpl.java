package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.model.*;
import rsp.repo.DeckRepo;
import rsp.repo.StatisticsDeckRepo;
import rsp.repo.StatisticsRepo;
import rsp.repo.UserRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.StatisticsService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepo repo;
    private final StatisticsDeckRepo statisticsDeckRepo;
    private final UserRepo userRepo;
    private final DeckRepo deckRepo;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepo repo, StatisticsDeckRepo statisticsDeckRepo, UserRepo userRepo, DeckRepo deckRepo) {
        this.repo = repo;
        this.statisticsDeckRepo = statisticsDeckRepo;
        this.userRepo = userRepo;
        this.deckRepo = deckRepo;
    }

    public StatisticDeck getStatisticDeckByDeckId(@NotNull Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        return statistics.getDeck(deckId);
    }

    public Integer getNumberOfLearnedByDeckId(@NotNull Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        return statistics.getDeck(deckId).getNumberOfLearned();
    }

    public Integer getNumberOfUnknownByDeckId(@NotNull Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        return statistics.getDeck(deckId).getNumberOfUnknown();
    }

    public void createDeck(Deck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Statistics statistics = currentUser.getStatistics();
        StatisticDeck statisticDeck = createStatisticDeck(deck);
        if (statistics == null) {
            statistics = new Statistics();
            repo.save(statistics);
            currentUser.setStatistics(statistics);
            userRepo.save(currentUser);
        }
        Integer statisticsId = currentUser.getStatistics().getId();
        statistics = repo.findById(statisticsId).get();
        statistics.addDeck(statisticDeck);
        repo.save(statistics);
    }

    public void deleteDeck(Integer deckId) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        statistics.removeDeck(deckId);
        repo.save(statistics);
        statisticsDeckRepo.deleteByDeckId(deckId);
    }

    public void updateDeck(Deck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        statisticsDeckRepo.deleteByDeckId(deck.getId());
        StatisticDeck statisticDeck = createStatisticDeck(deck);
        if (statistics == null) {
            statistics = new Statistics();
            repo.save(statistics);
            currentUser.setStatistics(statistics);
            userRepo.save(currentUser);
        } else {
            statisticsDeckRepo.save(statisticDeck);
            statistics.updateDeck(statisticDeck);
            statistics.reset(deck.getId());
            repo.save(statistics);
        }
    }

    public void storeAnswer(StatisticDeck deck) {
        User currentUser = SecurityUtils.getCurrentUser();
        Integer statisticsId = currentUser.getStatistics().getId();
        Statistics statistics = repo.findById(statisticsId).get();
        statistics.storeDeck(deck);
        repo.save(statistics);
    }

    @Override
    public StatisticDeck countAnswer(Integer deckId, Integer[] knownCards) {
        StatisticDeck deck = statisticsDeckRepo.findByDeckId(deckId);
        Arrays.stream(knownCards).forEach(i -> deck.incrementCardStatistic(i));
        return statisticsDeckRepo.save(deck);
    }

    private StatisticDeck createStatisticDeck(Deck deck) {
        Map<Integer, Integer> map;
        if (deck.getCards() != null) {
            List<Integer> cardIdList = deck.getCards().stream().map(Card::getId).collect(Collectors.toList());
            map = new HashMap<>(cardIdList.size());
            for (Integer i : cardIdList) {
                map.put(i, 0);
            }
        } else {
            map = new HashMap<>(0);
        }
        StatisticDeck statisticDeck = new StatisticDeck(map, deck.getId());
        return statisticsDeckRepo.save(statisticDeck);
    }
}
