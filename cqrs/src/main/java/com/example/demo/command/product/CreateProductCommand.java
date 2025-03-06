package com.example.demo.command.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateProductCommand {

    private Integer productId;

    private String name;

    private Set<Integer> categoryIds;
}
