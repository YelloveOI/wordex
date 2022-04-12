package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Role;
import rsp.util.Constants;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String username;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String email;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private List<Role> roles;

    @OneToMany
    @Getter
    @Setter
    private List<Deck> decks;

    @OneToOne
    @Getter
    @Setter
    private Statistics statistics;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<Role>() {
            {
                add(Constants.DEFAULT_ROLE);
            }
        };
    }

    public void erasePassword() {
        this.password = null;
    }
}
