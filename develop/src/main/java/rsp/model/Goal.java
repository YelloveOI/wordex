package rsp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Goal extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;
}
