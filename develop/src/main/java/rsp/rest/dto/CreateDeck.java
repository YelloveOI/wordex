package rsp.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Tag;
import rsp.model.User;

import javax.persistence.*;
import java.util.List;

@Data
public class CreateDeck {


    private String description;

    private String isConfigurable;

    private String isPrivate;

    private String name;

    private Language languageFrom;

    private Language languageTo;

    private List<CardDto> cards;

    private List<TagDto> tags;
}
