package rsp.model;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashMap;
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

    public StatisticDeck(Map<Integer, Integer> cards, Integer deckId) {
        this.cards = cards;
        this.deckId = deckId;
    }

    public StatisticDeck() {
    }

    public StatisticDeck(String jsonDeck) {
        JSONObject jsonObject = new JSONObject(jsonDeck);
        this.deckId = jsonObject.getInt("deckId");
        JSONArray jsonCards = jsonObject.getJSONArray("cards");
        if (cards == null ) {
            cards = new HashMap<>();
        }
        for (int i = 0; i < jsonCards.length(); i++) {
            JSONObject card = jsonCards.getJSONObject(i);
            cards.put(card.getInt("cardId"), card.getInt("cardStatistic"));
        }
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

    public void incrementCardStatistic(Integer id) {
        Integer card = cards.get(id);
        cards.remove(id);
        cards.put(id, ++card);
    }

    public Integer getNumberOfUnknown() {
        return cards.size() - getNumberOfLearned();
    }

    public void resetAll() {
        for (Integer key : cards.keySet()) {
            cards.replace(key, cards.get(key), 0);
        }
    }

    public String toJSON() {
        StringBuilder builder = new StringBuilder("");
        builder.append("{\"id\":")
                .append(this.getId())
                .append(",")
                .append("\"deckId\":")
                .append(this.getDeckId())
                .append(",")
                .append("\"cards\":[");
        cards.forEach((k, v) -> builder.append("{\"cardId\":")
                .append(k)
                .append(",\"cardStatistic\":")
                .append(v)
                .append("},"));
        builder.deleteCharAt(builder.length() - 1)
                .append("]")
                .append("}");
        return builder.toString();
    }
}
