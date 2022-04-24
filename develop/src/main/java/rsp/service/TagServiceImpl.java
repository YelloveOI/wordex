package rsp.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.exception.NotFoundException;
import rsp.model.Deck;
import rsp.model.Tag;
import rsp.repo.TagRepo;
import rsp.service.interfaces.TagService;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepo repo;

    public TagServiceImpl(TagRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Deck> findDecksWithTag(String tagName, int decksQuantity) {
        Optional<Tag> tag = repo.findByName(tagName);
        if(tag.isPresent()) {
            return tag.get().getDecks().stream()
                    .limit(decksQuantity)
                    .collect(Collectors.toList());
        } else {
            throw NotFoundException.create(Tag.class.getName(), tagName);
        }
    }

    //TODO
//    @Override
//    public List<Deck> findDecksWithTags(List<String> tagNames, int deckQuantity) {
//        List<Tag> tagList = new ArrayList<>();
//        List<Deck> deckList = new ArrayList<>();
//        Optional<Tag> tag;
//
//        for(String s : tagNames) {
//            tag = repo.findByName(s);
//            tag.ifPresent(tagList::add);
//        }
//
//        if(tagList.isEmpty()) {
//            return deckList;
//        } else {
//
//        }
//    }

    @Override
    public Boolean tagWithNameExists(String tagName) {
        return repo.existsByName(tagName);
    }

    @Override
    public Tag create(String name) {
        Tag result = new Tag();
        result.setName(name);

        repo.save(result);

        return result;
    }

    @Override
    public void addDeck(Tag tag, Deck deck) {
        tag.addDeck(deck);

        repo.save(tag);
    }

    @Override
    public void removeDeck(Tag tag, Deck deck) {
        tag.removeDeck(deck);

        repo.save(tag);
    }
}
