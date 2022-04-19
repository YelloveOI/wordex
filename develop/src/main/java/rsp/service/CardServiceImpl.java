package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.IllegalActionException;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.repo.CardRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.CardService;

import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRepo repo;

    public CardServiceImpl(CardRepo repo) {
        this.repo = repo;
    }


    @Override
    public void save(@NotNull Card card) {
        repo.save(card);
    }

    @Override
    public void deleteById(@NotNull Integer id) throws Exception {
        Optional<Card> toDelete = repo.findById(id);
        if(toDelete.isPresent()) {
            if (!toDelete.get().getDeck().getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
                throw new Exception("You can't delete someone else's card.");
            }
            repo.deleteById(id);
        } else {
            throw NotFoundException.create(Card.class.getName(), id);
        }
    }

    @Override
    public Card findById(@NotNull Integer id) {
        Optional<Card> result = repo.findById(id);
        if(result.isPresent()) {
            return  result.get();
        } else {
            throw NotFoundException.create(Card.class.getName(), id);
        }
    }

    @Override
    public Integer createUsingValues(
            @NotNull String term,
            @NotNull String definition,
            @NotNull String translation
    ) {
        Card card = new Card();

        card.setTerm(term);
        card.setDefinition(definition);
        card.setTranslation(translation);

        save(card);

        return card.getId();
    }

    @Override
    public void create(@NotNull Card card) {
        save(card);
    }

    @Override
    public void update(@NotNull Card card) throws Exception {
        if (!card.getDeck().getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
            throw new Exception("You can't edit someone else's card.");
        }
        if (!card.getDeck().isConfigurable()) {
            throw new Exception("The deck, this card belongs to, is not configurable.");
        }
        repo.save(card);
    }

    @Override
    public void updateAnswers(@NotNull Card card) throws Exception {
        if (!card.getDeck().getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
            throw new Exception("You can't edit someone else's card.");
        }
        if (!card.getDeck().isConfigurable()) { // check if card values weren't changed if not configurable
            Card result = findById(card.getId());
            if (!result.getDefinition().equals(card.getDefinition())
                    || !result.getTerm().equals(card.getTerm())
                    || !result.getTranslation().equals(card.getTranslation())) {
                throw new Exception("The deck, this card belongs to, is not configurable.");
            }
        }
        repo.save(card);
    }

    /*public Card createPublicCopy(@NotNull Card card) {
        if(!card.isPublic()) {
            Card result = new Card();

            result.setDefinition(card.getDefinition());
            result.setTerm(card.getTerm());

            return  result;
        } else {
            throw IllegalActionException.create("create public card copy of public card", card);
        }
    }*/

    public Card createPrivateCopy(@NotNull Card card) {
        Card result = new Card();

        result.setDefinition(card.getDefinition());
        result.setTerm(card.getTerm());
        result.setTranslation(card.getTranslation());

        return result;
    }

    @Override
    public boolean checkAnswer(int id, @NotNull String answer){
        if(findById(id).getTranslation().equals(answer)) {
            return true;
        } else {
            return false;
        }
    }
}
