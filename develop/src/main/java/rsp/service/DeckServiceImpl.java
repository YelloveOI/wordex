package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.repo.DeckRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;

import java.util.List;
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
    public List<Deck> getUserDecks() {
        return repo.findAllByOwnerId(SecurityUtils.getCurrentUser().getId());
    }

    @Override
    public List<Deck> getPublicDecks() {
        return repo.findByIsPrivateFalse();
    }


    @Override
    public void save(@NotNull Deck deck) {
        deck.setOwner(SecurityUtils.getCurrentUser());
        repo.save(deck);
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

    @Override
    public void updateAnswers(@NotNull Deck deck) throws Exception {
        if (!deck.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
            throw new Exception("You can't edit someone else's deck.");
        }
        if (!deck.isConfigurable()) { // check if deck values weren't changed if not configurable
            Deck result = findById(deck.getId());
            if (!result.getDescription().equals(deck.getDescription())
                    || result.isConfigurable() != deck.isConfigurable()
                    || result.isPrivate() != deck.isPrivate()
                    || !result.getName().equals(deck.getName())
                    || result.getLanguageFrom() != deck.getLanguageFrom()
                    || result.getLanguageTo() != deck.getLanguageTo()) {
                throw new Exception("This deck is not configurable.");
            }
        }
        repo.save(deck);
    }

    /*public Deck createPublicCopy(@NotNull Deck deck) {
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
    }*/

    @Override
    public void createPrivateCopy(@NotNull Deck deck) {
        Deck result = new Deck();

        result.setOwner(SecurityUtils.getCurrentUser());

        result.setPrivate(true);
        result.setConfigurable(deck.isConfigurable());
        result.setLanguageFrom(deck.getLanguageFrom());
        result.setLanguageTo(deck.getLanguageTo());
        result.setDescription(deck.getDescription());
        result.setName(deck.getName());
        result.setTags(deck.getTags());

        for(Card c : deck.getCards()) {
//            result.addCard(cardService.createPrivateCopy(c));
        }

        save(deck);
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
