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
        if (!customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);

            return customer;
        } else {
            return null; //customer s takym id uz existuje
        }
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            return null;
        }
    }

    public Customer deleteCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        }

        return customer.get();
    }

    public Customer updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);

            return customer;
        } else {
            return null;  //nemozno updatnut entitu ktora neexistuje
        }
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

    public List<Tea> getCustomersFavouriteTeasByCustomerId(Long id) {
        List<Tea> result = new ArrayList<>();
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            for (Tea tea: customer.get().getFavouriteTeas()) {
                result.add(tea);
            }
        } else {
            return null; //customer neexistuje tak ani zoznam jeho oblubenych cajov
        }

        return result;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();

        for (Customer customer : customerRepository.findAll()) {
            result.add(customer);
        }

        return result;
    };
}
