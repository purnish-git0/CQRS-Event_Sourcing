package com.cqrs.controller;

import com.cqrs.aggregate.CustomerAggregate;
import com.cqrs.command.customer.CreateCustomerCommand;
import com.cqrs.command.customer.UpdateCustomerCommand;
import com.cqrs.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerAggregate customerAggregate;

    public CustomerController(CustomerAggregate customerAggregate) {
        this.customerAggregate = customerAggregate;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CreateCustomerCommand command = CreateCustomerCommand.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build();

        return ResponseEntity.ok(new Object());
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        UpdateCustomerCommand command = UpdateCustomerCommand.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build();

        return ResponseEntity.ok(customerAggregate.handleUpdateCustomer(command));
    }
}
