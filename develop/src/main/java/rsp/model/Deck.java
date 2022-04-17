package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

@Entity
public class Deck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    @Setter
    private boolean isConfigurable;

    @Basic(optional = false)
    @Column(nullable = false)
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
    private Language languageTo;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageFrom;

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

    public boolean isConfigurable() {
        return isConfigurable;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

}
