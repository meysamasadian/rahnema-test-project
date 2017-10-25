package com.asadian.rahnema.gateway.repository;

import com.asadian.rahnema.gateway.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {
}
