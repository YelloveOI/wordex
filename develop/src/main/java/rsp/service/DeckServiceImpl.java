package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
import rsp.exception.IllegalActionException;
import rsp.model.Card;
import rsp.model.Deck;
import rsp.repo.DeckRepo;
import rsp.security.SecurityUtils;
import rsp.service.interfaces.DeckService;

import java.util.List;

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
    public List<Deck> getCurrentUserDecks() {
        return repo.findAllByOwnerId(SecurityUtils.getCurrentUser().getId());
    }

    @Override
    public List<Deck> getPublicDecks() {
        return repo.findByIsPrivateFalse();
    }

    //TODO Tags logic

    /**
     * Creates private deck w/o cards
     */
    @Override
    public Deck create(
            @NotNull String description,
            @NotNull String name,
            @NotNull Language languageTo,
            @NotNull Language languageFrom
    ) {
        Deck deck = new Deck();

        deck.setDescription(description);
        deck.setName(name);
        deck.setLanguageTo(languageTo);
        deck.setLanguageFrom(languageFrom);
        deck.setPrivate(true);

        repo.save(deck);

        return deck;
    }

    @Override
    public void addCard(@NotNull Deck deck, @NotNull Card card) {
        deck.addCard(card);

        repo.save(deck);
    }

    @Override
    public void removeCard(@NotNull Deck deck, @NotNull Card card) {
        deck.removeCard(card);

        repo.save(deck);
    }

    @Override
    public Deck createPublicCopy(@NotNull Deck deck) {
        if(deck.isPrivate()) {
            Deck result = new Deck();

            result.setPrivate(false);
            result.setConfigurable(deck.isConfigurable());
            result.setLanguageFrom(deck.getLanguageFrom());
            result.setLanguageTo(deck.getLanguageTo());
            result.setDescription(deck.getDescription());
            result.setName(deck.getName());
            result.setTags(deck.getTags());

            for(Card c : deck.getCards()) {
                result.addCard(cardService.createCopy(c));
            }

            return result;
        } else {
            throw IllegalActionException.create("create public deck copy of public deck", deck);
        }
    }

    @Override
    public Deck createPrivateCopy(@NotNull Deck deck) {
        Deck result = new Deck();

        result.setPrivate(true);
        result.setConfigurable(deck.isConfigurable());
        result.setLanguageFrom(deck.getLanguageFrom());
        result.setLanguageTo(deck.getLanguageTo());
        result.setDescription(deck.getDescription());
        result.setName(deck.getName());
        result.setTags(deck.getTags());

        for(Card c : deck.getCards()) {
            result.addCard(cardService.createCopy(c));
        }

        repo.save(result);

        return result;
    }

//    @Override
//    public void deleteById(@NotNull Integer id) throws Exception {
//        Optional<Deck> toDelete = repo.findById(id);
//        if(toDelete.isPresent()) {
//            if (!toDelete.get().getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
//                throw new Exception("You can't delete someone else's deck.");
//            }
//            repo.deleteById(id);
//        } else {
//            throw NotFoundException.create(Deck.class.getName(), id);
//        }
//    }
//
//    @Override
//    public Deck findById(@NotNull Integer id) {
//        Optional<Deck> result = repo.findById(id);
//        if(result.isPresent()) {
//            return result.get();
//        } else {
//            throw NotFoundException.create(Deck.class.getName(), id);
//        }
//    }
}
