package com.cqrs.command.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateCustomerCommand {

    private Integer id;

    private String name;

    private String email;

    private String phone;
}
