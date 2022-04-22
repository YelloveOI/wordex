package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
public class StatisticDeck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private Integer deckId;

    /*@Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private Integer userId;*/

    @ElementCollection
    @Setter
    private Map<Integer, Integer> cards;

    public StatisticDeck(Map<Integer, Integer> cards) {
        this.cards = cards;
    }

    public StatisticDeck() {

    }

    public Integer getNumberOfLearned() {
        Integer learned = 0;
        final Integer NumberOfRepeating = 3;
        for (Integer key : cards.keySet()) {
            if (cards.get(key).equals(NumberOfRepeating)) {
                ++learned;
            }
        }
        return learned;
    }

    public Integer getNumberOfUnknown() {
        return cards.size() - getNumberOfLearned();
    }
}
