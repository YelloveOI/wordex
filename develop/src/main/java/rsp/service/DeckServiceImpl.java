package rsp.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.Language;
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

    /*@Override
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
    public Deck editText(
            @NotNull Deck deck,
            @NotNull String name,
            @NotNull String description
            ) {
        deck.setName(name);
        deck.setDescription(description);

        repo.save(deck);

        return deck;
    }*/

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

    /** LUKAS **/

    @Override
    public Deck findById(@NotNull Integer id) throws Exception {
        Optional<Deck> result = repo.findById(id);
        if(result.isPresent()) {
            return checkIfAvailable(result.get());
        } else {
            throw NotFoundException.create(Deck.class.getName(), id);
        }
    }

    /**
     * RETURNS THE FIRST DECK IN DB WITH THIS NAME (name is not unique)
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Deck findByName(@NotNull String name) throws Exception {
        Optional<Deck> result = repo.findFirstByName(name);
        if (result.isPresent()) {
            return checkIfAvailable(result.get());
        } else {
            throw NotFoundException.create(Deck.class.getName(), name);
        }
    }

    /**
     * IDE wouldn't stop complaining about duplicity.
     * @param deck
     * @return
     * @throws Exception
     */
    private Deck checkIfAvailable(Deck deck) throws Exception {
        if (!deck.isPrivate() || deck.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId())) {
            return deck;
        } else {
            throw new Exception("This deck doesn't belong to you.");
        }
    }

    @Override
    public List<Deck> getUserDecks() {
        return repo.findAllByOwnerId(SecurityUtils.getCurrentUser().getId());
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
    public void createPrivateCopy(@NotNull Integer id) throws Exception {
        Deck reference = findById(id);

        Deck result = new Deck();

        result.setOwner(SecurityUtils.getCurrentUser());

        result.setPrivate(true);
        result.setConfigurable(reference.isConfigurable());
        result.setLanguageFrom(reference.getLanguageFrom());
        result.setLanguageTo(reference.getLanguageTo());
        result.setDescription(reference.getDescription());
        result.setName(reference.getName());
        result.setTags(reference.getTags());

        for(Card c : reference.getCards()) {
            result.addCard(cardDeepCopy(c));
        }

        repo.save(result);
    }

    private Card cardDeepCopy(Card card) {
        Card result = new Card();

        result.setDefinition(card.getDefinition());
        result.setTerm(card.getTerm());
        result.setContentList(card.getContentList());

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

}
