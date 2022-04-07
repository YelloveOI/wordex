package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Role;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
public class User extends AbstractEntity {

    //TODO other things

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private List<Role> role;

    @OneToMany
    @Getter
    @Setter
    private List<Deck> decks;

    @OneToOne
    @Getter
    @Setter
    private Statistics statistics;
}
