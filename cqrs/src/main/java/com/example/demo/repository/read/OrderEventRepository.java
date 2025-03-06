package com.example.demo.repository.read;

import com.example.demo.entity.OrderEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEventStore, Integer> {
}
