package rsp.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rsp.model.Deck;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
public class TagDto {

    private String name;

}
