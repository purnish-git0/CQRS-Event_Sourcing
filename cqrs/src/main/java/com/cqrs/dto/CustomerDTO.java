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
public class CustomerDTO {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private Set<Integer> orderIds;
}
