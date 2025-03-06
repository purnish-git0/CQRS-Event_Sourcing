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
public class CategoryUpdateDTO {

    private Integer id;

    private String name;

    private Set<Integer> productIdsToAdd;

    private Set<Integer> productIdsToRemove;


}