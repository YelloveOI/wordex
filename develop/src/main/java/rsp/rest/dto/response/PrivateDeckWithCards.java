package rsp.rest.dto.response;

import lombok.Getter;
import lombok.Setter;
import rsp.rest.dto.CardDto;

@Getter @Setter
public class PrivateDeckWithCards {
    public long id;
    public String name;
    public String languageFrom;
    public String languageTo;
    public String description;

    public DeckSearchResultTag[] tags;

    public CardDto[] cards;
    public DeckSearchResultAuthor owner;

    public Integer learnedCount;
    public Integer unknownCount;
    
    @Getter @Setter
    public static class DeckSearchResultTag {
        public String name;
    }

    @Getter @Setter
    public static class DeckSearchResultAuthor {
        public String username;
    }
}
