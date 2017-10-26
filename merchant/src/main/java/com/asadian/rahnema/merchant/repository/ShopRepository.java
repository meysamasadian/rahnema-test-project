package com.asadian.rahnema.merchant.repository;

import com.asadian.rahnema.merchant.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Nemesis on 25/10/2017.
 */
public interface ShopRepository extends MongoRepository<Shop,String> {
    Shop findByPan(String pan);
}
