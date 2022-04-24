package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class CardStorage extends AbstractEntity {

    @OneToMany
    @Getter
    @Setter
    private List<Deck> deckList;

    /**
     * This list server as a "deck" for the
     * cards which are not assigned to any other deck
     */
    @OneToMany
    @Getter
    @Setter
    private List<Card> freeCards;

    @OneToOne
    @Getter
    @Setter
    private User owner;

    public void addUnassignedCard(Card card) {
        freeCards.add(card);
    }

    public void removeUnassignedCard(Card card) {
        freeCards.remove(card);
    }

    public void addDeck(Deck deck) {
        deckList.add(deck);
    }

    public void removeDeck(Deck deck) {
        deckList.remove(deck);
    }

}
