package rsp.model;

import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

@Entity
public class Deck extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isConfigurable;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isPrivate;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany
    private List<Card> cards;

    @ManyToMany
    private List<Tag> tags;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isConfigurable() {
        return isConfigurable;
    }

    public void setConfigurable(boolean configurable) {
        isConfigurable = configurable;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
