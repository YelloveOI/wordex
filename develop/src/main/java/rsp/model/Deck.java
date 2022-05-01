package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Language;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String description;

    //TODO deprecated??
    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isConfigurable;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isPrivate;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageFrom;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageTo;

    @OneToMany
    @Getter
    @Setter
    private List<Card> cards;

    @ManyToMany
    @Getter
    @Setter
    private List<Tag> tags;

    @ManyToOne
    @Getter
    @Setter
    private User owner;

    public Deck(String description, boolean isConfigurable, boolean isPrivate, String name, Language languageFrom, Language languageTo) {
        this.description = description;
        this.isConfigurable = isConfigurable;
        this.isPrivate = isPrivate;
        this.name = name;
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public Deck() {
    }

    public void addCard(Card card) {
        if (cards == null) {
            cards = new ArrayList<>();
        }
        cards.add(card);
    }

    public void removeCard(Card card) {
        if (cards == null) {
            return;
        }
        cards.remove(card);
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        if (tags == null) {
            return;
        }
        tags.remove(tag);
    }



}
