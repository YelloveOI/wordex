package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
import rsp.exception.IllegalActionException;
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

    @Autowired
    public DeckServiceImpl(DeckRepo repo) {
        this.repo = repo;
    }

    //TODO get N deck
    @Override
    public List<Deck> getPublicDecks() {
        return repo.findAllByIsPrivateFalse();
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
        deck.setOwner(SecurityUtils.getCurrentUser());

        return repo.save(deck);
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

    /**
     * Creates deck w/o cards
     * @param deck
     * @return
     */
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
            result.setOwner(SecurityUtils.getCurrentUser());

            return result;
        } else {
            throw IllegalActionException.create("create public deck copy of public deck", deck);
        }
    }

    /**
     * Creates deck w/o cards
     * @param deck
     * @return
     */
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
        result.setOwner(SecurityUtils.getCurrentUser());

        repo.save(result);

        return result;
    }

    @Override
    public Deck editText(
            @NotNull Deck deck,
            @NotNull String name,
            @NotNull String description
            ) {
        deck.setName(name);
        deck.setDescription(description);

        repo.save(deck);

        return deck;
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

    @Override
    public boolean exists(Deck deck) {
        return repo.existsByNameAndAndCardsAndIdAndDescriptionAndLanguageFromAndLanguageToAndPrivate(
                deck.getName(),
                deck.getCards(),
                deck.getId(),
                deck.getDescription(),
                deck.getLanguageFrom(),
                deck.getLanguageTo(),
                deck.isPrivate()
        );
    }

    @Override
    public void delete(@NotNull Deck deck){
        if(exists(deck)) {
            repo.delete(deck);
        }
    }

}
