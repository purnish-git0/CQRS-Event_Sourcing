package com.example.demo.controller;

import com.example.demo.aggregate.CategoryAggregate;
import com.example.demo.command.category.CreateCategoryCommand;
import com.example.demo.command.category.UpdateCategoryCommand;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.CategoryUpdateDTO;
import com.example.demo.entity.ProductDocument;
import com.example.demo.event.Event;
import com.example.demo.projection.CategoryProjection;
import com.example.demo.query.ProductByCategoryQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {


    private CategoryAggregate categoryAggregate;

    private CategoryProjection categoryProjection;

    public CategoryController(CategoryAggregate categoryAggregate
    , CategoryProjection categoryProjection) {
        this.categoryAggregate = categoryAggregate;
        this.categoryProjection =categoryProjection;
    }

    @PostMapping("/create")
    public ResponseEntity<List<Event>> createCategory(@RequestBody Set<CategoryDTO> categoryDTOs) {

        List<Event> retVal = new ArrayList<>();
        categoryDTOs.forEach(categoryDTO -> {
            CreateCategoryCommand command = CreateCategoryCommand.builder()
                    .categoryId(categoryDTO.getCategoryId())
                    .name(categoryDTO.getName())
                    .productIds(categoryDTO.getProductIds())
                    .build();
            retVal.addAll(categoryAggregate.handleCreateCategory(command));
        });


        return ResponseEntity.ok(retVal);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        UpdateCategoryCommand command = UpdateCategoryCommand.builder()
                .name(categoryUpdateDTO.getName())
                .productIdsToAdd(categoryUpdateDTO.getProductIdsToAdd())
                .productIdsToRemove(categoryUpdateDTO.getProductIdsToRemove())
                .build();

        return ResponseEntity.ok(categoryAggregate.handleUpdateCategory(command));
    }

    @PostMapping("/get-products-for-categories")
    public ResponseEntity<List<ProductDocument>> getProductsForCategories(@RequestBody Set<Integer> categoriesIds) {

        ProductByCategoryQuery productByCategoryQuery = ProductByCategoryQuery
                .builder()
                .categoryIds(categoriesIds)
                .build();

        List<ProductDocument> retVal = categoryProjection.getProductsForCategories(productByCategoryQuery);

        return ResponseEntity.ok(retVal);
    }
}
