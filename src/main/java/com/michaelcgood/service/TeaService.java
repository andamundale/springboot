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
public class TeaService {
    private TeaRepository teaRepository;
    private CustomerRepository customerRepository;

    TeaService(CustomerRepository customerRepository, TeaRepository repository) {
        this.customerRepository = customerRepository;
        this.teaRepository = repository;
    }

    public Tea addTea(Tea tea) {
//        if (!teaRepository.existsById(tea.getId())) {
        if (teaRepository.findByNameAndCountryOfOrigin(tea.getName(), tea.getCountryOfOrigin()).isEmpty()) {
            teaRepository.save(tea);

            return tea;
        } else {
            return null; //tea s takym id uz existuje
        }
    }

    public Tea getTeaById(Long id) {
        Optional<Tea> tea = teaRepository.findById(id);
        if (tea.isPresent()) {
            return tea.get();
        } else {
            return null;
        }
    }

    public Tea deleteTeaById(Long id) {
        List<Customer> customers;
        Optional<Tea> tea = teaRepository.findById(id);
        if (tea.isPresent()) {
            customers = tea.get().getCustomers();
            System.out.println(customers); //ked je toto a rovnake v TeaController zakomentovane TeaMapper hadze chybu
            teaRepository.deleteById(id);
        }

        return tea.get();
    }

    public Tea updateTea(Tea tea) {
        if (teaRepository.existsById(tea.getId())) {
            teaRepository.save(tea);

            return tea;
        } else {
            return null; //nemozno updatnut entitu ktora neexistuje
        }
    }

    public List<Customer> getTeasCustomersByTeaId(Long id) {
        List<Customer> result = new ArrayList<Customer>();
        Optional<Tea> tea = teaRepository.findById(id);

        if (tea.isPresent()) {
            //for (Customer customer : tea.get().getCustomers()) {
            //    result.add(customer);
            //}
            return tea.get().getCustomers();
        }

        return result;
    }

    public List<Tea> getAllTeas() {
        List<Tea> result = new ArrayList<Tea>();

        for (Tea tea : teaRepository.findAll()) {
            result.add(tea);
        }

        return result;
    };

}
