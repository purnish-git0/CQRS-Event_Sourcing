package com.cqrs.command.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateCategoryCommand {

    private String name;

    private Integer categoryId;

    private Set<Integer> productIds;

}
