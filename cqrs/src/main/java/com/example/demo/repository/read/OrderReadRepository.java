package com.example.demo.repository.read;

import com.example.demo.entity.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderReadRepository extends MongoRepository<OrderDocument, Integer> {

    @Query(value = "{{'customerDocument' : {'id': ?1}, 'answers.answerId' : ?1}}")
    public List<OrderDocument> getOrderDocsByCustomerId(Integer customerId);


}
