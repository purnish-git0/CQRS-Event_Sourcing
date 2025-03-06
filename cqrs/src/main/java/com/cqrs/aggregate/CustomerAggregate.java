package com.cqrs.aggregate;

import com.cqrs.command.customer.UpdateCustomerCommand;
import com.cqrs.entity.CustomerDocument;
import org.springframework.stereotype.Component;

@Component
public class CustomerAggregate {


    public CustomerAggregate() {

    }


    public CustomerDocument handleUpdateCustomer(UpdateCustomerCommand updateCustomerCommand) {
        return null;
    }
}
