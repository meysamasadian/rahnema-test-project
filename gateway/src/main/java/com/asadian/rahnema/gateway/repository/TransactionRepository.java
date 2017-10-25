package com.asadian.rahnema.gateway.repository;

import com.asadian.rahnema.gateway.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,String> {
    List<Transaction> findAllBySourceOrDest(String source,String dest);
}
