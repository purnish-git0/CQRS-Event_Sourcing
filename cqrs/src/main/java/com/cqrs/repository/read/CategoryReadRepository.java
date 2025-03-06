package com.cqrs.repository.read;

import com.cqrs.entity.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryReadRepository extends MongoRepository<CategoryDocument, Integer> {


}
