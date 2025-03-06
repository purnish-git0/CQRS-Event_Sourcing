package com.cqrs.projection;

import com.cqrs.entity.CategoryDocument;
import com.cqrs.entity.ProductDocument;
import com.cqrs.query.ProductByCategoryQuery;
import com.cqrs.repository.read.CategoryReadRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryProjection {

    private CategoryReadRepository categoryReadRepository;

    private MongoTemplate readTemplate;

    public CategoryProjection(CategoryReadRepository categoryReadRepository
    ,@Qualifier("readMongoTemplate") MongoTemplate mongoTemplate) {
        this.categoryReadRepository =categoryReadRepository;
        this.readTemplate = mongoTemplate;
    }

    public List<ProductDocument> getProductsForCategories(ProductByCategoryQuery productByCategoryQuery) {
        List<ProductDocument> retVal = new ArrayList<>();



        productByCategoryQuery.getCategoryIds().forEach(categoryId -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("categoryId").is(categoryId));

            List<CategoryDocument> categoryDocuments = readTemplate.find(query, CategoryDocument.class);
            retVal.addAll(categoryDocuments.stream().flatMap(categoryDocument -> {
                return categoryDocument.getProducts().stream();
            }).toList());


        });

        return retVal;

    }

}
