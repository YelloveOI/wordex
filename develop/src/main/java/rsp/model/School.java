package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "School.findSchoolByName", query = "SELECT s FROM School s WHERE s.name = :name"),
})
public class School extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String address;

    @ManyToMany
    @Getter
    @Setter
    private List<User> students;

    @ManyToMany
    @Getter
    @Setter
    private List<User> teachers;

    @ManyToMany
    @Getter
    @Setter
    private List<User> moderators;

    public boolean hasStudent(User user) {
        for (User s : students) {
            if (s.getId() == user.getId()) {
                return true;
            }
        }
        return false;
    }
}
