package com.michaelcgood.model.dto;

import com.michaelcgood.model.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CustomerDto {
    //Long id;
    String firstName;
    String lastName;
    //private List<Tea> favouriteTeas;  // zakaznikove oblubene caje}

    public Customer convertToEntity() {
        return new Customer(this.firstName, this.lastName);
    }
}