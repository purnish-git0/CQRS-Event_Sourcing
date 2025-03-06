package com.example.demo.repository.write;

import com.example.demo.entity.CategoryEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEventStoreRepository extends MongoRepository<CategoryEventStore, String> {


}
