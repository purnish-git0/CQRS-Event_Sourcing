package com.example.demo.repository.read;

import com.example.demo.entity.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryReadRepository extends MongoRepository<CategoryDocument, Integer> {


}
