package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Card extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String definition;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String term;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String translation;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isKnown;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isLearned;

    @ManyToOne
    @Column(nullable = false)
    @Getter
    @Setter
    private Deck deck;



    /*@OneToMany
    @Getter
    @Setter
    private List<AbstractContent> contentList;*/
    //TODO AbstractContent


    public Card(String definition, String term, String translation) {
        this.definition = definition;
        this.term = term;
        this.translation = translation;
    }

    public Card() {
    }
}
