package rsp.model;

import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

@Entity
public class Deck extends AbstractEntity{

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private String nme;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToMany
    private List<Card> cards;

    @ManyToMany
    private List<Tag> tags;

}
