package rsp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Task extends Goal {

    @Basic(optional = false)
    @Column(nullable = false)
    private Date toDo;


}
