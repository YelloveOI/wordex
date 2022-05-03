package rsp.service;

import org.jetbrains.annotations.NotNull;
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
    public List<Deck> findPublicDecksWithTag(@NotNull String tagName, int decksQuantity) {
        Optional<Tag> tag = repo.findByName(tagName);

        if(tag.isPresent()) {
            return tag.get().getDecks().stream()
                    .filter(v -> !v.isPrivate())
                    .limit(decksQuantity)
                    .collect(Collectors.toList());
        } else {
            throw NotFoundException.create(Tag.class.getName(), tagName);
        }
    }

    //TODO
//    @Override
//    public List<Deck> findDecksWithTags(List<String> tagNames, int decksQuantity) {
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
    public Tag findByName(@NotNull String tagName) {
        Optional<Tag> result = repo.findByName(tagName);

        if(result.isPresent()) {
            return result.get();
        } else {
            throw NotFoundException.create(Tag.class.getName(), tagName);
        }
    }

    @Override
    public Tag create(String name) {
        Tag result = new Tag();
        result.setName(name);

        repo.save(result);

        return result;
    }

    @Override
    public void addDeck(@NotNull Tag tag, @NotNull Deck deck) {
        tag.addDeck(deck);

        repo.save(tag);
    }

    @Override
    public void removeDeck(@NotNull Tag tag, @NotNull Deck deck) {
        tag.removeDeck(deck);

        repo.save(tag);
    }

    @Override
    public Boolean tagWithNameExists(String name) {
        return repo.existsByName(name);
    }
}
