package rsp.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDeckPreview {
    public String name;
    public String description;
    public String languageFrom;
    public String languageTo;
}
