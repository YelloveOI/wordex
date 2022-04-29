package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Card extends AbstractEntity {

    public Card() {
        contentList = new ArrayList<>();
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
    private List<Content> contentList;

    public void addContent(Content content) {
        contentList.add(content);
    }

    public void removeContent(Content content) {
        contentList.remove(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!Objects.equals(definition, card.definition)) return false;
        if (!Objects.equals(term, card.term)) return false;
        return Objects.equals(contentList, card.contentList);
    }

    @Override
    public int hashCode() {
        int result = definition != null ? definition.hashCode() : 0;
        result = 31 * result + (term != null ? term.hashCode() : 0);
        result = 31 * result + (contentList != null ? contentList.hashCode() : 0);
        return result;
    }
}
