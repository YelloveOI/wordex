package rsp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Package extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Deck> decks;

}
