package rsp.rest.dto;

import lombok.Data;

@Data
public class KnownWordsDto {
    private Integer deckId;
    private Integer[] knownCards;
}
