package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.Content;
import rsp.repo.CardRepo;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.ContentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRepo repo;
    private final ContentService contentService;

    public CardServiceImpl(CardRepo repo, ContentService contentService) {
        this.repo = repo;
        this.contentService = contentService;
    }

    /**
     * Deletes a card, deleting couldn't affect
     * foreign decks
     * @param card
     * @throws Exception
     */
    @Override
    public void delete(@NotNull Card card) throws NotFoundException {
        if(exists(card)) {
            repo.delete(card);
        }
    }

//    @Override
//    public Card findById(@NotNull Integer id) throws NotFoundException {
//        Optional<Card> result = repo.findById(id);
//        if(result.isPresent()) {
//            return  result.get();
//        } else {
//            throw NotFoundException.create(Card.class.getName(), id);
//        }
//    }

    /**
     * Deep copy of a card, contents of a card are immutable,
     * thus there is swallow copy there
     * @param card to copy
     * @return copy of the card
     */
    @Override
    public Card createCopy(@NotNull Card card) {
        Card result = new Card();

        result.setTerm(card.getTerm());
        result.setDefinition(card.getDefinition());
        result.setContentList(card.getContentList());

        repo.save(result);

        return result;
    }

    @Override
    public Card create(
            @NotNull String term,
            @NotNull String definition
    ) {
        Card result = new Card();

        result.setTerm(term);
        result.setDefinition(definition);

        repo.save(result);

        return result;
    }

    @Override
    public Card editText(
            @NotNull Card card,
            @NotNull String term,
            @NotNull String definition
    ) {
        if(exists(card)) {
            card.setTerm(term);
            card.setDefinition(definition);

            repo.save(card);

            return card;
        } else {
            throw NotFoundException.create(Card.class.getName(), card.getId());
        }
    }

    @Override
    public Card addContent(
            @NotNull Card card,
            @NotNull Content content
    ) {
        if(exists(card)) {
            card.addContent(content);

            repo.save(card);

            return card;
        } else {
            throw NotFoundException.create(Card.class.getName(), card.getId());
        }
    }

    @Override
    public Card removeContent(
            @NotNull Card card,
            @NotNull Content content
    ) throws NotFoundException {
        if(exists(card)) {
            if(card.getContentList().contains(content)) {
                card.removeContent(content);

                repo.save(card);

                return card;
            } else {
                throw NotFoundException.create(Content.class.getName(), content.getId());
            }
        } else {
            throw NotFoundException.create(Card.class.getName(), card.getId());
        }
    }

    /**
     * @param card
     * @return true if exists (in repo) 100% same card, false otherwise
     */
    @Override
    public boolean exists(Card card){
        return repo.existsById(card.getId());
    }

    /*
    @Override
    public boolean exists(Card card) {
        return repo.existsByContentListAndTermAndDefinitionAndId(
                card.getContentList(),
                card.getTerm(),
                card.getDefinition(),
                card.getId()
        );
    }

     */
}
