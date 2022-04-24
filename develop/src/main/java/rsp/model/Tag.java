package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tag extends AbstractEntity {

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @ManyToMany
    private List<Deck> decks;

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void removeDeck(Deck deck) {
        decks.remove(deck);
    }

}
