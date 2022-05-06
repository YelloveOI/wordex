package rsp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsp.enums.ContentType;
import rsp.model.Content;
import rsp.repo.ContentRepo;
import rsp.service.interfaces.ContentService;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final ContentRepo repo;

    public ContentServiceImpl(ContentRepo repo) {
        this.repo = repo;
    }

    @Override
    public Content create(ContentType type, String source) {
        Content result = new Content();

        result.setSource(source);
        result.setType(type);

        repo.save(result);

        return result;
    }
}
