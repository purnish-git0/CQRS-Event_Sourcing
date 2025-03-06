package com.cqrs.repository.write;

import com.cqrs.entity.CategoryEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEventStoreRepository extends MongoRepository<CategoryEventStore, String> {


}
