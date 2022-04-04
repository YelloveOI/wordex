package rsp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Statistics extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer learnedCards;

    @ManyToMany
    Map<Date, Achievement> achievementMap;
}
