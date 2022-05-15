package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.exception.NotFoundException;

import javax.persistence.*;
import java.util.*;

@Entity
public class Statistics extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<StatisticDeck> decks;

    /*@ManyToMany
    Map<Date, Achievement> achievementMap;*/

    public Statistics() {
    }

    public StatisticDeck getDeck(Integer id) {
        for (StatisticDeck d : decks) {
            if (d.getDeckId().equals(id)) {
                return d;
            }
        }
        throw new NotFoundException("This statisticDeck does not exists.");
    }

    public void addDeck(StatisticDeck statisticDeck) {
        if (decks == null) {
            decks = new ArrayList<>();
        }
        decks.add(statisticDeck);
    }

    public void removeDeck(Integer deckId) {
        if (decks == null) {
            return;
        }
        Optional<StatisticDeck> forDelete = decks.stream().filter((d) -> d.getDeckId() != deckId).findFirst();
        decks.remove(forDelete.get());
    }

    public void updateDeck(StatisticDeck deck) {
        removeDeck(deck.getDeckId());
        addDeck(deck);
    }

    public void reset(Integer deckId) {
        Optional<StatisticDeck> optionalDeck = decks.stream().filter(x -> x.getDeckId() != deckId).findFirst();
        if (optionalDeck.isPresent()) {
            optionalDeck.get().resetAll();
        }
    }

    public void storeDeck(StatisticDeck deck) {
        Optional<StatisticDeck> optionalDeck = decks.stream().filter(x -> x.getDeckId().equals(deck.getDeckId())).findAny();
        if (optionalDeck.isPresent()) {
            decks.remove(optionalDeck.get());
            decks.add(deck);
        }
    }
}
