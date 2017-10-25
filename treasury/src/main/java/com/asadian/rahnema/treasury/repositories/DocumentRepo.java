package com.asadian.rahnema.treasury.repositories;

import com.asadian.rahnema.treasury.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepo extends MongoRepository<Document,String> {
    Document findByRefId(String refId);
}
