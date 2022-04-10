package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.repo.CardRepo;
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
    public void deleteById(@NotNull Integer id) {
        repo.deleteById(id);
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
            @NotNull Language to
    ) {
        Card result = new Card();
        result.setTerm(term);
        result.setDefinition(definition);
        result.setLanguageFrom(from);
        result.setLanguageTo(to);

        return result;
    }
}
