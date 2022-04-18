package rsp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import rsp.enums.Role;
import rsp.util.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.GET_ALL_USERS", query = "SELECT u FROM User u")
})
public class User extends AbstractEntity {

    public static final String GET_ALL_USERS = "User.getAllUsers";

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private List<Role> roles;

    @OneToMany
    @Getter
    @Setter
    private List<Deck> decks;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Statistics statistics;

    public void addDeck(Deck deck) {
        if (decks == null) {
            decks = new ArrayList<>();
        }
        decks.add(deck);
    }

    public void removeDeck(Deck deck) {
        if (decks == null) {
            return;
        }
        decks.remove(deck);
    }

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

    public boolean hasRole(Role role) {
        for (Role r : roles) {
            if (r.toString().equals(role.toString())) {
                return true;
            }
        }
        return false;
    }

    public void addRole(Role role) {
        Objects.requireNonNull(role);
        if (roles == null) {
            this.roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        Objects.requireNonNull(role);
        if (roles == null) {
            return;
        }
        roles.removeIf(r -> Objects.equals(r.toString(), role.toString()));
    }

    public void encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(password);
    }
}
