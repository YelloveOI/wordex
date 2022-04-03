package rsp.model;

import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

@Entity
public class Card extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String definition;

    @Basic(optional = false)
    @Column(nullable = false)
    private String term;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToMany
    private List<Deck> decks;

    //TODO AbstractContent

}
