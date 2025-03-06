package com.example.demo.repository.write;

import com.example.demo.entity.ProductEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEventStoreRepository extends MongoRepository<ProductEventStore, Integer> {




}
