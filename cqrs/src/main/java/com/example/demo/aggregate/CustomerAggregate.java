package com.example.demo.aggregate;

import com.example.demo.command.customer.CreateCustomerCommand;
import com.example.demo.command.customer.UpdateCustomerCommand;
import com.example.demo.entity.CustomerDocument;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerAggregate {


    public CustomerAggregate() {

    }


    public CustomerDocument handleUpdateCustomer(UpdateCustomerCommand updateCustomerCommand) {
        return null;
    }
}
