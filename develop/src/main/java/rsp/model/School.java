package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String city;

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
            if (s.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    public void addStudent(User student) {
        Objects.requireNonNull(student);
        if (students == null) {
            this.students = new ArrayList<>();
        }
        students.add(student);
    }

    public void removeStudent(User student) {
        Objects.requireNonNull(student);
        if (students == null) {
            return;
        }
        students.removeIf(s -> Objects.equals(s.getId(), student.getId()));
    }

    public void addTeacher(User teacher) {
        Objects.requireNonNull(teacher);
        if (teachers == null) {
            this.teachers = new ArrayList<>();
        }
        teachers.add(teacher);
    }
}
