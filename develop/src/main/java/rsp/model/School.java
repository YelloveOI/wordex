package rsp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class School extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String address;

    @ManyToMany
    private List<User> students;

    @ManyToMany
    private List<User> teachers;

    @ManyToMany
    private List<User> moderators;


}
