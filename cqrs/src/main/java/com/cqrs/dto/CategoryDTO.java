package com.cqrs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDTO {

    private Integer id;

    private String name;

    private Integer categoryId;

    private Set<Integer> productIds;


}
