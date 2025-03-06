package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

    private Integer id;

    private String name;

    private Set<Integer> categoryIds;
}
