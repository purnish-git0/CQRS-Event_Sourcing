package com.cqrs.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductCategoryChangedEvent extends Event {

    private Integer productId;

    private Set<Integer> categoriesId;
}
