package rsp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.service.interfaces.CardRepositoryService;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.ContentService;
import rsp.service.interfaces.DeckService;

@Service
@Transactional
public class CardRepositoryServiceImpl implements CardRepositoryService {

    private final CardService cas;
    private final ContentService cos;
    private final DeckService des;
    //TODO deckService

    public CardRepositoryServiceImpl(CardService cas, ContentService cos, DeckService des) {
        this.cas = cas;
        this.cos = cos;
        this.des = des;
    }



}
