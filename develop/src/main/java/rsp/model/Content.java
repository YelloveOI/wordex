package rsp.model;

import lombok.Getter;
import lombok.Setter;
import rsp.enums.ContentType;

import javax.persistence.*;

/**
 * Content represents different types of assistant content:
 * 1) Image
 * 2) Sound
 * 3) Video
 * Content must be immutable
 */
@Entity
public class Content extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Getter
    @Setter
    private String source;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ContentType type;

}
