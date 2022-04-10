package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Language;

import javax.persistence.*;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageTo;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Language languageFrom;

    /*@OneToMany
    @Getter
    @Setter
    private List<AbstractContent> contentList;*/
    //TODO AbstractContent
}
