package com.michaelcgood.service;

import com.michaelcgood.repository.CustomerRepository;
import com.michaelcgood.repository.TeaRepository;
import com.michaelcgood.model.Customer;
import com.michaelcgood.model.Tea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private TeaRepository teaRepository;

    CustomerService(CustomerRepository repository, TeaRepository teaRepository) {
        this.customerRepository = repository;
        this.teaRepository = teaRepository;
    }

    public Customer addCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    public Customer deleteCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        }

        return customer.get();
    }

    public Customer updateCustomers(Customer customer) {
//      co robit ak povodny elemnt neexistuje? zatial hlupo pridam update ako novy
        //if (customerRepository.existsById(customer.getId())) {
        customerRepository.save(customer);
        //}

        return customer;
    }

    public Tea addCustomersTea(Long customerId, Long teaId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Tea> tea = teaRepository.findById(teaId);

        if (
            customer.isPresent()
            && tea.isPresent()
            && (customer.get().getFavouriteTeas().indexOf(tea.get()) < 0)
        ) {
            customer.get().getFavouriteTeas().add(tea.get());
            tea.get().getCustomers().add(customer.get());
            customerRepository.save(customer.get());
            teaRepository.save(tea.get());

            return tea.get();
        }

        return null; // co sa patri vratit ak sa akcia nepdoarila?
    }


    public Tea deleteCustomersTeaById(Long customerId, Long teaId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Tea> tea = teaRepository.findById(teaId);

        if (
                customer.isPresent()
                        && tea.isPresent()
                        && customer.get().getFavouriteTeas().contains(tea.get())
        ) {
            customer.get().getFavouriteTeas().remove(tea.get());
            tea.get().getCustomers().remove(customer.get());
            customerRepository.save(customer.get());
            teaRepository.save(tea.get());

            return tea.get();
        }

        return null;  // co sa patri vratit ak sa akcia nepdoarila?
        // ak vec na vymazanie neexistuje alebo objekt ktoremu ma patrit neexistuje je akcia vymazania neuspesna?
    }

    public List<String> getCustomersFavouriteTeasByCustomerId(Long id) {
        List<String> result = new ArrayList<>();
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            for (Tea tea: customer.get().getFavouriteTeas()) {
                result.add(tea.toString());
            }
        } else {
            return null; //customer neexistuje tak ani zoznam jeho oblubenych cajov
        }

        return result;
    }

    public List<String> getAllCustomers() {
        List<String> result = new ArrayList<>();

        for (Customer customer : customerRepository.findAll()) {
            result.add(customer.toString());
        }

        return result;
    };
}
