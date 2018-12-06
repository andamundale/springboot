package com.michaelcgood.controller;

import com.michaelcgood.dao.CustomerRepository;
import com.michaelcgood.model.Customer;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    CustomerController(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    @RequestMapping(value = "/addCustomer", method = { RequestMethod.GET, RequestMethod.POST })
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @RequestMapping(value = "/getCustomersByLastName", method = { RequestMethod.GET, RequestMethod.POST })
    public List<Customer> getCustomersByLastName(String lastName) {
        List<Customer> result = new ArrayList<>();
        result = customerRepository.findByLastName(lastName);

        return result;
    }

    @RequestMapping(value = "/deleteCustomerById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean deleteCustomerById(Long id) {
        boolean result = false;
        if (customerRepository.existsById(id)) {
            result = true;
            customerRepository.deleteById(id);
        }

        return result;
    }

    @RequestMapping(value = "/updateCustomerById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean updateCustomersById(Customer customer) {
        boolean result = false;
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
            result = true;
        }

        return result;
    }
}
