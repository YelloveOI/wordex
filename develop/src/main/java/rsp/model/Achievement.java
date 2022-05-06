package rsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "achievement")
public class Achievement extends AbstractEntity {

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(nullable = false)
    private String imgSource;

    @Getter
    @Setter
    @ManyToOne
    private User owner;

    //@OneToMany
    //List<Goal> goals;

}
