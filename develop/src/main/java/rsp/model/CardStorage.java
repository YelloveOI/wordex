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
    @OneToOne
    @Getter
    @Setter
    private Deck deck;

    @OneToOne
    @Getter
    @Setter
    private User owner;

    public void addFreeCard(Card card) {
        deck.addCard(card);
    }

    public void removeFreeCard(Card card) {
        deck.removeCard(card);
    }

    public void addDeck(Deck deck) {
        deckList.add(deck);
    }

    public void removeDeck(Deck deck) {
        deckList.remove(deck);
    }

}
