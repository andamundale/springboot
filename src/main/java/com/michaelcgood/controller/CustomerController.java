package com.michaelcgood.controller;

//import com.michaelcgood.mapper.CustomerMapper;
//import com.michaelcgood.mapper.TeaMapper;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import com.michaelcgood.model.dto.CustomerDto;
import com.michaelcgood.model.dto.TeaDto;
import com.michaelcgood.service.CustomerService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="customer/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto.convertToEntity()).convertToDto();
    }

    @GetMapping(value = "/{id}")
    public CustomerDto getCustomersById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id).convertToDto();
    }

    // TODO All logic from controller should be moved into Services (like CustomerServices)
    //  it is better for JUnit testing
    // TODO delete should be with DELETE method only
    @DeleteMapping(value = "{id}")
    public CustomerDto deleteCustomerById(@PathVariable("id") Long id) {
       if (id >= 0) { //myslim ze id je nezaporne
           return customerService.deleteCustomerById(id).convertToDto();
       } else {
           return null; //zase raz ak neexistuje customer ktoreho chcem vymazat je to neuspech?
       }
    }

    //TODO update is with PUT method
    @PutMapping
    public CustomerDto updateCustomer(@RequestBody Customer customer) {
        if (customer != null) {
            return customerService.updateCustomer(customer).convertToDto();
        } else {
            return null; // updatnutie so zlym vstupom netreba riesit
        }
    }

    @PostMapping(value = "/addCustomersTeaById/{customerId}/{teaId}")
    public TeaDto addCustomersTea(@PathVariable Long customerId, @PathVariable Long teaId) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        return customerService.addCustomersTea(customerId, teaId).convertToDto();
    }


    @DeleteMapping(value = "/deleteCustomersTeaById/{customerId}/{teaId}")
    public TeaDto deleteCustomersTeaById(@PathVariable Long customerId, @PathVariable Long teaId) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        return customerService.deleteCustomersTeaById(customerId, teaId).convertToDto();
    }

    @GetMapping(value = "/getFavouriteTeas/{id}")
    public List<TeaDto> getCustomersFavouriteTeasByCustomerId(@PathVariable("id") Long id) {
        // tu by zase mohla byt podmienka ci vstupy su validne ID
        List<TeaDto> result = new ArrayList<TeaDto>();
        for (Tea tea: customerService.getCustomersFavouriteTeasByCustomerId(id)) {
            result.add(tea.convertToDto());
        }
        return result;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> result = new ArrayList<CustomerDto>();
        for (Customer customer: customerService.getAllCustomers()) {
            result.add(customer.convertToDto());
        }
        return result;
    };
}
