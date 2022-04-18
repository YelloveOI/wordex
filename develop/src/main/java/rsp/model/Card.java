package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Language;

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
    private boolean isPublic;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageFrom;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageTo;

    /*@OneToMany
    @Getter
    @Setter
    private List<AbstractContent> contentList;*/
    //TODO AbstractContent


    public Card(String definition, String term, boolean isPublic, Language languageFrom, Language languageTo) {
        this.definition = definition;
        this.term = term;
        this.isPublic = isPublic;
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public Card() {
    }
}
