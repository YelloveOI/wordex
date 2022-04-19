package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card extends AbstractEntity {

    public Card() {
        contentList = new HashSet<>();
    }

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
    private Set<Content> contentList;

    public void addContent(Content content) {
        contentList.add(content);
    }

    public void removeContent(Content content) {
        contentList.remove(content);
    }

}
