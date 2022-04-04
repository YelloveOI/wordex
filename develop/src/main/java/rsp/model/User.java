package rsp.model;

import rsp.enums.Role;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    //TODO other things

    @Basic(optional = false)
    @Column(nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> role;

    @OneToMany
    private List<Deck> decks;

    @OneToOne
    private Statistics statistics;




}
