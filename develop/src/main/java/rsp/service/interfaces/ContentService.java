package rsp.service.interfaces;

import rsp.enums.ContentType;
import rsp.model.Content;

public interface ContentService {

    Content create(ContentType type, String source);

}
