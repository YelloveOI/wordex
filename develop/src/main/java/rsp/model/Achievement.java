package rsp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Achievement extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

<<<<<<< HEAD
=======
    @OneToMany
    List<Goal> goals;
>>>>>>> 22df5ef5a9308e440be275c7948b5c616fe56cca
}
