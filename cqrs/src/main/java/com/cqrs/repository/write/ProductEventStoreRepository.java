package com.cqrs.repository.write;

import com.cqrs.entity.ProductEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventStoreRepository extends MongoRepository<ProductEventStore, Integer> {




}
