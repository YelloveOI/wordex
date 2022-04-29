package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Card;
import rsp.model.Content;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepo extends CrudRepository<Card, Integer> {

    boolean existsByContentListAndTermAndDefinitionAndId(List<Content> contentList, String term, String definition, Integer id);

}
