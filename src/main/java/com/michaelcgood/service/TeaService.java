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
        teaRepository.save(tea);

        return tea;
    }

    public List<Tea> getTeaByName(String name) {
        return teaRepository.findByName(name);
    }

    public Tea deleteTeaById(Long id) {
        Optional<Tea> tea = teaRepository.findById(id);
        if (tea.isPresent()) {
            teaRepository.deleteById(id);
        }

        return tea.get();
    }

    public Tea updateTea(Tea tea) {
//      co robit ak povodny elemnt neexistuje? zatial hlupo pridam update ako novy
        //if (teaRepository.existsById(tea.getId())) {
        teaRepository.save(tea);
        //}

        return tea;
    }

    public List<String> getTeasCustomersByTeaId(Long id) {
        List<String> result = new ArrayList<>();
        Optional<Tea> tea = teaRepository.findById(id);

        if (tea.isPresent()) {
            for (Customer customer : tea.get().getCustomers()) {
                result.add(customer.toString());
            }
        }

        return result;
    }

    public List<String> getAllTeas() {
        List<String> result = new ArrayList<>();

        for (Tea tea : teaRepository.findAll()) {
            result.add(tea.toString());
        }

        return result;
    };

}
