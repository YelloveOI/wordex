package rsp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudyPlan extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @ManyToMany
    private List<Task> tasks;

}
