package rsp.model;

import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Statistics extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Setter
    private Integer learnedCards;

    @ManyToMany
    Map<Date, Achievement> achievementMap;
}
