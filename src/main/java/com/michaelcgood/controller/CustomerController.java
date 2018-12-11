package com.michaelcgood.controller;

import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //TODO rework with @GetMapping, @PostMapping,....
    //TODO adding should be POST method only
    //TODO path /addCustomer is not necessary
        // swagger seems to not work with /addCustomer removed
    // return values should be the added object (always)
    @PostMapping(value = "/addCustomer")
    public Customer addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping(value = "/getCustomersByLastName")
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerService.getCustomersByLastName(lastName);
    }

    // TODO All logic from controller should be moved into Services (like CustomerServices)
    //  it is better for JUnit testing
    // TODO delete should be with DELETE method only
    @DeleteMapping(value = "/deleteCustomerById")
    public Customer deleteCustomerById(Long id) {
       if (id >= 0) { //myslim ze id je nezaporne
           return customerService.deleteCustomerById(id);
       } else {
           return null; //zase raz ak neexistuje customer ktoreho chcem vymazat je to neuspech?
       }
    }

    //TODO update is with PUT method
    @PutMapping(value = "/updateCustomer")
    public Customer updateCustomers(Customer customer) {
        if (customer != null) {
            return customerService.updateCustomers(customer);
        } else {
            return null; // updatnutie so zlym vstupom netreba riesit
        }
    }

    @PostMapping(value = "/addCustomersTeaById")
    public Tea addCustomersTea(Long customerId, Long teaId) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        return customerService.addCustomersTea(customerId, teaId);
    }


    @DeleteMapping(value = "/deleteCustomersTeaById")
    public Tea deleteCustomersTeaById(Long customerId, Long teaId) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        return customerService.deleteCustomersTeaById(customerId, teaId);
    }

    @GetMapping(value = "/getCustomersFavouriteTeasByCustomerId")
    public List<String> getCustomersFavouriteTeasByCustomerId(Long id) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        return customerService.getCustomersFavouriteTeasByCustomerId(id);
    }

    @GetMapping(
            value = "/getAllCustomers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> getAllCustomers() {
        return customerService.getAllCustomers();
    };
}
