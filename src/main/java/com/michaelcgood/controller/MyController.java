package com.michaelcgood.controller;

import com.michaelcgood.dao.CustomerRepository;
import com.michaelcgood.dao.TeaRepository;
import com.michaelcgood.model.Customer;
//import org.springframework.stereotype.Controller;
import com.michaelcgood.model.Tea;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MyController {

    private CustomerRepository customerRepository;
    private TeaRepository teaRepository;

    MyController(CustomerRepository customerRepository, TeaRepository teaRepository) {
        this.customerRepository = customerRepository;
        this.teaRepository = teaRepository;
    }

    @RequestMapping(
            value = "/getAllCustomers",
            method = { RequestMethod.GET, RequestMethod.POST },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> getAllCustomers() {
        List<String> result = new ArrayList<>();

        System.out.println("--- /getAllCustomers ---");
        for (Customer customer : customerRepository.findAll()) {
            result.add(customer.toString());
            System.out.println(customer.toString());
        }

        return result;
    };

    @RequestMapping(
            value = "/getAllTeas",
            method = { RequestMethod.GET, RequestMethod.POST },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> getAllTeas() {
        List<String> result = new ArrayList<>();

        System.out.println("--- /getAllTeas ---");
        for (Tea tea : teaRepository.findAll()) {
            result.add(tea.toString());
            System.out.println(tea.toString());
        }

        return result;
    };

}
