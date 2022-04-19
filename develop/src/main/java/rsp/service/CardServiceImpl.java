package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.Content;
import rsp.repo.CardRepo;
import rsp.service.interfaces.CardService;
import rsp.service.interfaces.ContentService;

import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRepo repo;
    private final ContentService cs;

    public CardServiceImpl(CardRepo repo, ContentService cs) {
        this.repo = repo;
        this.cs = cs;
    }

    /**
     * Deletes a card by id, deleting couldn't affect
     * foreign decks
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteById(@NotNull Integer id) throws NotFoundException {
        Optional<Card> toDelete = repo.findById(id);
        if(toDelete.isPresent()) {
            repo.deleteById(id);
        } else {
            throw NotFoundException.create(Card.class.getName(), id);
        }
    }

    @Override
    public Card findById(@NotNull Integer id) throws NotFoundException {
        Optional<Card> result = repo.findById(id);
        if(result.isPresent()) {
            return  result.get();
        } else {
            throw NotFoundException.create(Card.class.getName(), id);
        }
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
        card.setTerm(term);
        card.setDefinition(definition);

        repo.save(card);

        return card;
    }

    @Override
    public Card addContent(
            @NotNull Card card,
            @NotNull Content content
    ) {
        card.addContent(content);

        repo.save(card);

        return card;
    }

    @Override
    public Card removeContent(
            @NotNull Card card,
            @NotNull Content content
    ) throws NotFoundException {
        if(card.getContentList().contains(content)) {
            card.removeContent(content);
        } else {
            throw NotFoundException.create(Card.class.getName(), content);
        }

        repo.save(card);

        return card;
    }

    /**
     * Deep copy of a card, contents of a card are immutable,
     * thus there is swallow copy there
     * @param card to copy
     * @return copy of the card
     */
    @Override
    public Card deepCopy(@NotNull Card card) {
        Card result = new Card();

        result.setDefinition(card.getDefinition());
        result.setTerm(card.getTerm());
        result.setContentList(card.getContentList());

        repo.save(result);

        return result;
    }
}
