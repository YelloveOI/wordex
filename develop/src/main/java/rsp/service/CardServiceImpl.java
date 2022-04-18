package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
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
    public Card save(@NotNull Card card) {
        repo.save(card);
        return card;
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
    public Card create(
            @NotNull String term,
            @NotNull String definition,
            @NotNull Language from,
            @NotNull Language to,
            @NotNull boolean isPublic
    ) {
        Card result = new Card();

        result.setTerm(term);
        result.setDefinition(definition);
        result.setPublic(isPublic);

        return result;
    }

    public Card createPublicCopy(@NotNull Card card) {
        if(!card.isPublic()) {
            Card result = new Card();

            result.setPublic(true);
            result.setDefinition(card.getDefinition());
            result.setTerm(card.getTerm());

            return  result;
        } else {
            throw IllegalActionException.create("create public card copy of public card", card);
        }
    }

    public Card createPrivateCopy(@NotNull Card card) {
        Card result = new Card();

        result.setPublic(false);
        result.setDefinition(card.getDefinition());
        result.setTerm(card.getTerm());

        return  result;
    }

    public boolean checkAnswer(int id,@NotNull String answer){
        if(findById(id).getTranslation().equals(answer)) {
            return true;
        } else {
            return false;
        }
    }
}
