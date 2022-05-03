package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.exception.NotFoundException;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Statistics extends AbstractEntity {

    @OneToMany
    private List<StatisticDeck> decks;

    /*@ManyToMany
    Map<Date, Achievement> achievementMap;*/

    public Statistics() {
        this.decks = new ArrayList<>();
    }

    public StatisticDeck getDeck(Integer id) {
        StatisticDeck result;
        for (StatisticDeck d : decks) {
            if (d.getDeckId().equals(id)) {
                return d;
            }
        }
        throw new NotFoundException("This statisticDeck does not exists.");
    }

    public void addDeck(Deck deck) {
        StatisticDeck statisticDeck;
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
        statisticDeck = new StatisticDeck(map);
        statisticDeck.setDeckId(deck.getId());
        decks.add(statisticDeck);
    }

    public void removeDeck(Integer deckId) {
        Optional<StatisticDeck> deck = decks.stream().filter(x -> x.getDeckId() == deckId).findFirst();
        if (deck.isPresent()) {
            decks.remove(deck.get().getId());
        }
    }

    public void updateDeck(Deck deck) {
        removeDeck(deck.getId());
        addDeck(deck);
    }

    public void reset(Integer deckId) {
        Optional<StatisticDeck> optionalDeck = decks.stream().filter(x -> x.getDeckId() == deckId).findFirst();
        if (optionalDeck.isPresent()) {
            optionalDeck.get().resetAll();
        }
    }

    public void storeDeck(StatisticDeck deck) {
        Optional<StatisticDeck> optionalDeck = decks.stream().filter(x -> x.getDeckId() == deck.getDeckId()).findFirst();
        if (optionalDeck.isPresent()) {
            decks.remove(optionalDeck.get().getId());
            decks.add(deck);
        }
    }
}
