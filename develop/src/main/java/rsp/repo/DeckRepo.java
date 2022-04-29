package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.enums.Language;
import rsp.model.Card;
import rsp.model.Deck;

import java.util.List;

@Repository
public interface DeckRepo extends CrudRepository<Deck, Integer> {

    List<Deck> findAllByIsPrivateFalse();

    boolean existsByNameAndAndCardsAndIdAndDescriptionAndLanguageFromAndLanguageToAndPrivate(
            String name,
            List<Card> cards,
            Integer id,
            String description,
            Language languageFrom,
            Language languageTo,
            boolean isPrivate
            );

}
