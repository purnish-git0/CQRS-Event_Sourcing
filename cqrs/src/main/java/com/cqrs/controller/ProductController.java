package com.cqrs.controller;

import com.cqrs.aggregate.ProductAggregate;
import com.cqrs.command.product.CreateProductCommand;
import com.cqrs.command.product.UpdateProductCommand;
import com.cqrs.dto.ProductDTO;
import com.cqrs.entity.ProductDocument;
import com.cqrs.event.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductAggregate productAggregate;

    public ProductController(ProductAggregate productAggregate) {
        this.productAggregate = productAggregate;
    }

    @PostMapping("/create")
    public ResponseEntity<List<Event>> createProduct(@RequestBody Set<ProductDTO> productDTOs) {
        List<Event> retVal = new ArrayList<>();
        productDTOs.forEach(productDTO -> {
            CreateProductCommand command = CreateProductCommand.builder()
                    .name(productDTO.getName())
                    .productId(productDTO.getId())
                    .categoryIds(productDTO.getCategoryIds())
                    .build();

            retVal.addAll(productAggregate.handleCreateProduct(command));
        });


        return ResponseEntity.ok(retVal);

    }

    @PostMapping("/update")
    public ResponseEntity<List<Event>> updateProduct(@RequestBody ProductDTO productDTO) {
        UpdateProductCommand command = UpdateProductCommand.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .categoryIds(productDTO.getCategoryIds())
                .build();

        return ResponseEntity.ok(productAggregate.handleUpdateProduct(command));
    }
}
