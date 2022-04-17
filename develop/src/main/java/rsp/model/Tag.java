package rsp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tag extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<Deck> decks;

}
