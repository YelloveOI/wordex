package rsp.model;

import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

@Entity
public class Card extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String definition;

    @Basic(optional = false)
    @Column(nullable = false)
    private String term;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    @JoinColumn(name="deck_id")
    private Deck deck;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    //TODO AbstractContent

}
