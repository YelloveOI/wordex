package rsp.model;

import lombok.Getter;
import lombok.Setter;

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

    @OneToMany
    @Getter
    @Setter
    private List<Content> contentList;

}
