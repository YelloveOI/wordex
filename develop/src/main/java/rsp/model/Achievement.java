package rsp.model;

import javax.persistence.*;

@Entity
@Table(name = "achievement")
public class Achievement extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    //@OneToMany
    //List<Goal> goals;

}
