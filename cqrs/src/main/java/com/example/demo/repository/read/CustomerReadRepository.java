package com.example.demo.repository.read;

import com.example.demo.entity.CustomerDocument;
import com.example.demo.entity.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerReadRepository extends MongoRepository<CustomerDocument, Integer> {


    @Query(value = "{'id': ?1}")
    public List<OrderDocument> getOrderDocsByCustomerId(Integer customerId);


}
