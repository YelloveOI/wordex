package rsp.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rsp.model.Content;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
public class CardDto {

    private String definition;

    private String term;

    //no image or sound...
    //private List<Content> contentList;

}
