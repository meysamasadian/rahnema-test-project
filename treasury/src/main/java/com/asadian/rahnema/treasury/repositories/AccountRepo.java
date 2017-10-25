package com.asadian.rahnema.treasury.repositories;

import com.asadian.rahnema.treasury.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepo extends MongoRepository<Account, String> {
    Account findByPan(String pan);
}
