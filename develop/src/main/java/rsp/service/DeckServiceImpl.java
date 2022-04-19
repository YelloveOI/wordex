package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.IllegalActionException;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.repo.DeckRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;

import java.util.Optional;

@Service
@Transactional
public class DeckServiceImpl implements DeckService {

    private final DeckRepo repo;
    private final CardServiceImpl cardService;

    @Autowired
    public DeckServiceImpl(DeckRepo repo, CardServiceImpl cardService) {
        this.repo = repo;
        this.cardService = cardService;
    }


    @Override
    public Integer save(@NotNull Deck deck) {
        deck.setOwner(SecurityUtils.getCurrentUser());
        repo.save(deck);

        return deck.getId();
    }

    @Override
    public void update(@NotNull Deck deck) throws Exception {
        if (!deck.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
            throw new Exception("You can't edit someone else's deck.");
        }
        if (!deck.isConfigurable()) {
            throw new Exception("This deck is not configurable.");
        }
        repo.save(deck);
    }

    public Deck createPublicCopy(@NotNull Deck deck) {
        if(deck.isPrivate()) {
            Deck result = new Deck();

            result.setPrivate(false);
            result.setConfigurable(deck.isConfigurable());
            result.setLanguageFrom(deck.getLanguageFrom());
            result.setLanguageTo(deck.getLanguageTo());
            result.setDescription(deck.getDescription());
            result.setName(deck.getName());
            result.setOwner(deck.getOwner());
            result.setTags(deck.getTags());

            for(Card c : deck.getCards()) {
                result.addCard(cardService.createPublicCopy(c));
            }

            return result;
        } else {
            throw IllegalActionException.create("create public deck copy of public deck", deck);
        }
    }

    public Deck createPrivateCopy(@NotNull Deck deck) {
        Deck result = new Deck();

        result.setPrivate(true);
        result.setConfigurable(deck.isConfigurable());
        result.setLanguageFrom(deck.getLanguageFrom());
        result.setLanguageTo(deck.getLanguageTo());
        result.setDescription(deck.getDescription());
        result.setName(deck.getName());
        result.setOwner(deck.getOwner());
        result.setTags(deck.getTags());

        for(Card c : deck.getCards()) {
            result.addCard(cardService.createPrivateCopy(c));
        }

        return result;
    }

    @Override
    public void deleteById(@NotNull Integer id) throws Exception {
        Optional<Deck> toDelete = repo.findById(id);
        if(toDelete.isPresent()) {
            if (!toDelete.get().getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
                throw new Exception("You can't delete someone else's deck.");
            }
            repo.deleteById(id);
        } else {
            throw NotFoundException.create(Deck.class.getName(), id);
        }
    }

    @Override
    public Deck findById(@NotNull Integer id) {
        Optional<Deck> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(Deck.class.getName(), id);
        }
    }
}
