package com.michaelcgood.controller;

import com.michaelcgood.dao.CustomerRepository;
import com.michaelcgood.dao.TeaRepository;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;
    private TeaRepository teaRepository;

    CustomerController(CustomerRepository repository, TeaRepository teaRepository) {
        this.customerRepository = repository;
        this.teaRepository = teaRepository;
    }

    //TODO rework with @GetMapping, @PostMapping,....
    //TODO adding should be POST method only
    //TODO path /addCustomer is not necessary
    // return values should be the added object (always)
    @RequestMapping(value = "/addCustomer", method = { RequestMethod.GET, RequestMethod.POST })
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @RequestMapping(value = "/getCustomersByLastName", method = { RequestMethod.GET, RequestMethod.POST })
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

//    TODO All logic from controller should be moved into Services (like CustomerServices)
    // it is better for JUnit testing
    //TODO delete should be with DELETE method only
    @RequestMapping(value = "/deleteCustomerById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean deleteCustomerById(Long id) {
        boolean result = false;
        if (customerRepository.existsById(id)) {
            result = true;
            customerRepository.deleteById(id);
        }

        return result;
    }

    //TODO update is with PUT method
    @RequestMapping(value = "/updateCustomerById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean updateCustomersById(Customer customer) {
        boolean result = false;
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
            result = true;
        }

        return result;
    }

    @RequestMapping(value = "/addCustomersTeaById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean addCustomersTea(Long customerId, Long teaId) {
        boolean result = false;
        Optional<Customer> customer;
        Optional<Tea> tea;

        customer = customerRepository.findById(customerId);
        tea = teaRepository.findById(teaId);
        if (
                customer.isPresent()
                && tea.isPresent()
                && (customer.get().getFavouriteTeas().indexOf(tea.get()) < 0)
        ) {
            customer.get().getFavouriteTeas().add(tea.get());
            tea.get().getCustomers().add(customer.get());
            customerRepository.save(customer.get());
            teaRepository.save(tea.get());
            result = true;
        }

        return result;
    }


    @RequestMapping(value = "/deleteCustomersTeaById", method = { RequestMethod.GET, RequestMethod.POST })
    public boolean addCustomer(Long customerId, Long teaId) {
        boolean result = false;
        Optional<Customer> customer;
        Optional<Tea> tea;

        customer = customerRepository.findById(customerId);
        tea = teaRepository.findById(teaId);
        if (
                customer.isPresent()
                && tea.isPresent()
                && customer.get().getFavouriteTeas().contains(tea.get())
        ) {
            customer.get().getFavouriteTeas().remove(tea.get());
            tea.get().getCustomers().remove(customer.get());
            customerRepository.save(customer.get());
            teaRepository.save(tea.get());
            result = true;
        }

        return result;
    }

    @RequestMapping(
            value = "/getCustomersFavouriteTeasByCustomerId",
            method = { RequestMethod.GET, RequestMethod.POST }
            )
    public List<String> getCustomersFavouriteTeasByCustomerId(Long id) {
        List<String> result = new ArrayList<>();
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            for (Tea tea: customer.get().getFavouriteTeas()) {
                result.add(tea.toString());
            }
        }

        return result;
    }
}
