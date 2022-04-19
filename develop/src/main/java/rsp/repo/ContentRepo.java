package rsp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rsp.model.Content;

@Repository
public interface ContentRepo extends CrudRepository<Content, Integer> {
}
